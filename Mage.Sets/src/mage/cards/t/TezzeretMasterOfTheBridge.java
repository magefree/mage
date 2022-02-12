package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledSpellsEffect;
import mage.abilities.keyword.AffinityForArtifactsAbility;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterArtifactCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TezzeretMasterOfTheBridge extends CardImpl {

    private static final FilterCard filter = new FilterCard("creature and planeswalker spells");

    static {
        filter.add(Predicates.or(
                CardType.PLANESWALKER.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
    }

    private static final FilterCard filter2 = new FilterArtifactCard("artifact card from your graveyard");

    public TezzeretMasterOfTheBridge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{U}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.TEZZERET);
        this.setStartingLoyalty(5);

        // Creature and planeswalker spells you cast have affinity for artifacts.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledSpellsEffect(
                new AffinityForArtifactsAbility(), filter
        )));

        // +2: Tezzeret, Master of the Bridge deals X damage to each opponent, where X is the number of artifacts you control. You gain X life.
        this.addAbility(new LoyaltyAbility(new TezzeretMasterOfTheBridgeEffect(), 2));

        // -3: Return target artifact card from your graveyard to your hand.
        Ability ability = new LoyaltyAbility(new ReturnToHandTargetEffect(), -3);
        ability.addTarget(new TargetCardInYourGraveyard(filter2));
        this.addAbility(ability);

        // -8: Exile the top ten cards of your library. Put all artifact cards from among them onto the battlefield.
        this.addAbility(new LoyaltyAbility(new TezzeretMasterOfTheBridgeEffect2(), -8));
    }

    private TezzeretMasterOfTheBridge(final TezzeretMasterOfTheBridge card) {
        super(card);
    }

    @Override
    public TezzeretMasterOfTheBridge copy() {
        return new TezzeretMasterOfTheBridge(this);
    }
}

class TezzeretMasterOfTheBridgeEffect extends OneShotEffect {

    TezzeretMasterOfTheBridgeEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals X damage to each opponent, " +
                "where X is the number of artifacts you control. You gain X life";
    }

    private TezzeretMasterOfTheBridgeEffect(final TezzeretMasterOfTheBridgeEffect effect) {
        super(effect);
    }

    @Override
    public TezzeretMasterOfTheBridgeEffect copy() {
        return new TezzeretMasterOfTheBridgeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int artifactCount = new PermanentsOnBattlefieldCount(
                StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT
        ).calculate(game, source, this);
        new DamagePlayersEffect(artifactCount, TargetController.OPPONENT).apply(game, source);
        player.gainLife(artifactCount, game, source);
        return true;
    }
}

class TezzeretMasterOfTheBridgeEffect2 extends OneShotEffect {

    TezzeretMasterOfTheBridgeEffect2() {
        super(Outcome.Benefit);
        staticText = "Exile the top ten cards of your library. " +
                "Put all artifact cards from among them onto the battlefield.";
    }

    private TezzeretMasterOfTheBridgeEffect2(final TezzeretMasterOfTheBridgeEffect2 effect) {
        super(effect);
    }

    @Override
    public TezzeretMasterOfTheBridgeEffect2 copy() {
        return new TezzeretMasterOfTheBridgeEffect2(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 10));
        player.moveCards(cards, Zone.EXILED, source, game);
        Cards cards2 = new CardsImpl();
        for (Card card : cards.getCards(game)) {
            if (card.isArtifact(game)) {
                cards2.add(card);
            }
        }
        return player.moveCards(cards2, Zone.BATTLEFIELD, source, game);
    }
}