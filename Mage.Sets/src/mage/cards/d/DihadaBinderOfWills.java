package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.CanBeYourCommanderAbility;
import mage.abilities.effects.common.RevealLibraryPickControllerEffect;
import mage.abilities.effects.common.UntapAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlAllEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.Cards;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author Draya, awjackson
 */
public final class DihadaBinderOfWills extends CardImpl {

    private static final FilterCreaturePermanent legendarycreaturefilter = new FilterCreaturePermanent("legendary creature");

    static {
        legendarycreaturefilter.add(SuperType.LEGENDARY.getPredicate());
    }

public DihadaBinderOfWills(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{1}{R}{W}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DIHADA);

        this.setStartingLoyalty(5);

        // +2: Up to one target legendary creature gains vigilance, lifelink, and indestructible until your next turn.
        Ability ability = new LoyaltyAbility(new GainAbilityTargetEffect(
                VigilanceAbility.getInstance(), Duration.UntilYourNextTurn
        ).setText("Up to one target legendary creature gains vigilance"), 2);
        ability.addEffect(new GainAbilityTargetEffect(
                LifelinkAbility.getInstance(), Duration.UntilYourNextTurn
        ).setText(", lifelink"));
        ability.addEffect(new GainAbilityTargetEffect(
                IndestructibleAbility.getInstance(), Duration.UntilYourNextTurn
        ).setText(", and indestructible until your next turn."));
        ability.addTarget(new TargetCreaturePermanent(0, 1, legendarycreaturefilter, false));
        this.addAbility(ability);

        // -3: Reveal the top four cards of your library.
        // Put any number of legendary cards from among them into your hand and the rest into your graveyard.
        // Create a Treasure token for each card put into your graveyard this way.
        this.addAbility(new LoyaltyAbility(new DihadaFilterEffect(), -3));

        // -11: Gain control of all nonland permanents until end of turn. Untap them. They gain haste until end of turn.
        ability = new LoyaltyAbility(new GainControlAllEffect(Duration.EndOfTurn, StaticFilters.FILTER_PERMANENTS_NON_LAND), -11);
        ability.addEffect(new UntapAllEffect(StaticFilters.FILTER_PERMANENTS_NON_LAND).setText("untap them"));
        ability.addEffect(new GainAbilityAllEffect(
                HasteAbility.getInstance(),
                Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENTS_NON_LAND,
                "they gain haste until end of turn"
        ));
        this.addAbility(ability);
        
        // Dihada, Binder of Wills can be your commander.
        this.addAbility(CanBeYourCommanderAbility.getInstance());
    }

    private DihadaBinderOfWills(final DihadaBinderOfWills card) {
        super(card);
    }

    @Override
    public DihadaBinderOfWills copy() {
        return new DihadaBinderOfWills(this);
    }
}

class DihadaFilterEffect extends RevealLibraryPickControllerEffect {

    private static final FilterCard legendaryfilter = new FilterCard("legendary cards");

    static {
        legendaryfilter.add(SuperType.LEGENDARY.getPredicate());
    }

    public DihadaFilterEffect() {
        super(4, Integer.MAX_VALUE, legendaryfilter, PutCards.HAND, PutCards.GRAVEYARD, false);
        staticText = "Reveal the top four cards of your library. " +
                "Put any number of legendary cards from among them into your hand and the rest into your graveyard. " +
                "Create a Treasure token for each card put into your graveyard this way";
    }

    private DihadaFilterEffect(final DihadaFilterEffect effect) {
        super(effect);
    }

    @Override
    public DihadaFilterEffect copy() {
        return new DihadaFilterEffect(this);
    }

    @Override
    protected boolean actionWithPickedCards(Game game, Ability source, Player player, Cards pickedCards, Cards otherCards) {
        super.actionWithPickedCards(game, source, player, pickedCards, otherCards);
        otherCards.retainZone(Zone.GRAVEYARD, game);
        if (!otherCards.isEmpty()) {
            new TreasureToken().putOntoBattlefield(otherCards.size(), game, source, player.getId());
        }
        return true;
    }
}
