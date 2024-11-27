package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldWithCounterTargetEffect;
import mage.abilities.effects.common.SacrificeAllEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author Cguy7777
 */
public final class MeathookMassacreII extends CardImpl {

    public MeathookMassacreII(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{X}{X}{B}{B}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);

        // When Meathook Massacre II enters, each player sacrifices X creatures of their choice.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SacrificeAllEffect(GetXValue.instance, StaticFilters.FILTER_PERMANENT_CREATURES)));

        // Whenever a creature you control dies, you may pay 3 life.
        // If you do, return that card under your control with a finality counter on it.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new DoIfCostPaid(
                        new ReturnFromGraveyardToBattlefieldWithCounterTargetEffect(
                                CounterType.FINALITY.createInstance())
                                .setText("return that card under your control with a finality counter on it"),
                        new PayLifeCost(3)),
                false,
                StaticFilters.FILTER_CONTROLLED_A_CREATURE,
                true));

        // Whenever a creature an opponent controls dies, they may pay 3 life.
        // If they don't, return that card under your control with a finality counter on it.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new MeathookMassacreIIEffect(),
                false,
                StaticFilters.FILTER_OPPONENTS_PERMANENT_A_CREATURE,
                true));
    }

    private MeathookMassacreII(final MeathookMassacreII card) {
        super(card);
    }

    @Override
    public MeathookMassacreII copy() {
        return new MeathookMassacreII(this);
    }
}

class MeathookMassacreIIEffect extends OneShotEffect {

    MeathookMassacreIIEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "they may pay 3 life. " +
                "If they don't, return that card under your control with a finality counter on it";
    }

    private MeathookMassacreIIEffect(final MeathookMassacreIIEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = getTargetPointer().getControllerOfFirstTargetOrLKI(game, source);
        if (player == null) {
            return false;
        }

        // Whenever a creature an opponent controls dies, they may pay 3 life.
        // If they don't, return that card under your control with a finality counter on it.
        Cost cost = new PayLifeCost(3);
        if (player.chooseUse(Outcome.Detriment, "Pay 3 life to prevent this effect?", source, game)
                && cost.pay(source, game, source, player.getId(), true)) {
            return true;
        }

        // If a token died, card will be null and nothing will be returned to the battlefield
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (card == null) {
            return true;
        }
        new ReturnFromGraveyardToBattlefieldWithCounterTargetEffect(CounterType.FINALITY.createInstance())
                .setTargetPointer(new FixedTarget(card, game))
                .apply(game, source);
        return true;
    }

    @Override
    public MeathookMassacreIIEffect copy() {
        return new MeathookMassacreIIEffect(this);
    }
}
