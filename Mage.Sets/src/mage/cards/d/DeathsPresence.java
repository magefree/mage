package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class DeathsPresence extends CardImpl {

    public DeathsPresence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{5}{G}");

        // Whenever a creature you control dies, put X +1/+1 counters on target creature you control, where X is the power of the creature that died.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance(), DeathsPresenceDiedPermanentPowerCount.instance)
                        .setText("put X +1/+1 counters on target creature you control, where X is the power of the creature that died"),
                false, StaticFilters.FILTER_CONTROLLED_CREATURE));
    }

    private DeathsPresence(final DeathsPresence card) {
        super(card);
    }

    @Override
    public DeathsPresence copy() {
        return new DeathsPresence(this);
    }
}

enum DeathsPresenceDiedPermanentPowerCount implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Permanent targetPermanent = (Permanent) effect.getValue("creatureDied");
        if (targetPermanent != null) {
            return targetPermanent.getPower().getValue();
        }

        return 0;
    }

    @Override
    public DeathsPresenceDiedPermanentPowerCount copy() {
        return DeathsPresenceDiedPermanentPowerCount.instance;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "its power";
    }
}
