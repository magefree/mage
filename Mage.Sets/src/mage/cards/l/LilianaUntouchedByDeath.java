package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.ruleModifying.PlayFromGraveyardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterNonlandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LilianaUntouchedByDeath extends CardImpl {

    private static final DynamicValue xValue = new SignInversionDynamicValue(
            new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.ZOMBIE, "Zombies you control"), null)
    );

    private static final FilterNonlandCard filter = new FilterNonlandCard("Zombie spells");
    static {
        filter.add(SubType.ZOMBIE.getPredicate());
    }

    public LilianaUntouchedByDeath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.LILIANA);
        this.setStartingLoyalty(4);

        // +1: Put the top three cards of your library into your graveyard. If at least one of them is a Zombie card, each opponent loses 2 life and you gain 2 life.
        this.addAbility(new LoyaltyAbility(new LilianaUntouchedByDeathEffect(), 1));

        // -2: Target creature gets -X/-X until end of turn, where X is the number of Zombies you control.
        Ability ability = new LoyaltyAbility(new BoostTargetEffect(xValue, xValue, Duration.EndOfTurn), -2);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // -3: You may cast Zombie spells from your graveyard this turn.
        this.addAbility(new LoyaltyAbility(new PlayFromGraveyardControllerEffect(filter, Duration.EndOfTurn)
                .setText("You may cast Zombie spells from your graveyard this turn"), -3));
    }

    private LilianaUntouchedByDeath(final LilianaUntouchedByDeath card) {
        super(card);
    }

    @Override
    public LilianaUntouchedByDeath copy() {
        return new LilianaUntouchedByDeath(this);
    }
}

class LilianaUntouchedByDeathEffect extends OneShotEffect {

    LilianaUntouchedByDeathEffect() {
        super(Outcome.Benefit);
        this.staticText = "mill three cards. If at least one Zombie card is milled this way, each opponent loses 2 life and you gain 2 life";
    }

    private LilianaUntouchedByDeathEffect(final LilianaUntouchedByDeathEffect effect) {
        super(effect);
    }

    @Override
    public LilianaUntouchedByDeathEffect copy() {
        return new LilianaUntouchedByDeathEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        if (player
                .millCards(3, source, game)
                .getCards(game)
                .stream()
                .anyMatch(card -> card.hasSubtype(SubType.ZOMBIE, game))) {
            new LoseLifeOpponentsEffect(2).apply(game, source);
            player.gainLife(2, game, source);
        }
        return true;
    }
}
