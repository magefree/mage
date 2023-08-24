package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class ChargingHooligan extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(StaticFilters.FILTER_ATTACKING_CREATURE);

    public ChargingHooligan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PEASANT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Charging Hooligan attacks, it gets +1/+0 until end of turn for each attacking creature. If a Rat is attacking, Charging Hooligan gains trample until end of turn.
        Ability ability = new AttacksTriggeredAbility(
                new BoostSourceEffect(xValue, StaticValue.get(0), Duration.EndOfTurn, true, "it")
        );
        ability.addEffect(new ConditionalOneShotEffect(
                new AddContinuousEffectToGame(
                        new GainAbilitySourceEffect(TrampleAbility.getInstance(), Duration.EndOfTurn)
                ),
                ChargingHooliganCondition.instance
        ));

        this.addAbility(ability);
    }

    private ChargingHooligan(final ChargingHooligan card) {
        super(card);
    }

    @Override
    public ChargingHooligan copy() {
        return new ChargingHooligan(this);
    }
}

enum ChargingHooliganCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID attackingCreatureId : game.getCombat().getAttackers()) {
            Permanent attackingCreature = game.getPermanent(attackingCreatureId);
            if (attackingCreature != null) {
                if (attackingCreature.getSubtype(game).contains(SubType.RAT)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "a Rat is attacking";
    }
}
