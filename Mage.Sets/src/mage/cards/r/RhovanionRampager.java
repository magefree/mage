package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.keyword.AmassEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author miesma
 */
public final class RhovanionRampager extends CardImpl {

    public RhovanionRampager(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.WOLF);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever this creature attacks, you may sacrifice another creature.
        // If you do, put a number of +1/+1 counters on this creature equal to the sacrificed creature’s power.
        this.addAbility(new AttacksTriggeredAbility(new RhovanionRampagerEffect(), false));

        // When this creature dies,
        // amass Goblins X, where X is this creature's power.
        this.addAbility(new DiesSourceTriggeredAbility(new AmassEffect(SourcePermanentPowerValue.NOT_NEGATIVE, SubType.GOBLIN)
                .setText("amass Goblins X, where X is {this}'s power.")));
    }

    private RhovanionRampager(final RhovanionRampager card) {
        super(card);
    }

    @Override
    public RhovanionRampager copy() {
        return new RhovanionRampager(this);
    }
}

class RhovanionRampagerEffect extends OneShotEffect {

    RhovanionRampagerEffect() {
        super(Outcome.Benefit);
        staticText = "you may sacrifice another creature." +
                " If you do, put a number of +1/+1 counters on {this} equal to the sacrificed creature's power.";
    }

    private RhovanionRampagerEffect(final RhovanionRampagerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            SacrificeTargetCost cost = new SacrificeTargetCost(1, StaticFilters.FILTER_ANOTHER_CREATURE_YOU_CONTROL);
            if (cost.canPay(source, source, controller.getId(), game)
                    && cost.pay(source, game, source, controller.getId(), true)) {
                final int power = cost.getPermanents().get(0).getPower().getValue();
                Effect addCounterEffect = new AddCountersSourceEffect(CounterType.P1P1.createInstance(), StaticValue.get(power));
                return addCounterEffect.apply(game, source);
            }
        }
        return false;
    }

    @Override
    public RhovanionRampagerEffect copy() {
        return new RhovanionRampagerEffect(this);
    }
}

