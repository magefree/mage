package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * A-Luminarch Aspirant - Alchemy rebalanced version
 *
 * Digital change: Trigger at beginning of end step instead of beginning of combat.
 * This makes the card less aggressive and more defensive-oriented.
 *
 * @author Vernon
 */
public final class ALuminarchAspirant extends CardImpl {

    public ALuminarchAspirant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // At the beginning of the end step, put a +1/+1 counter on target creature you control.
        // This is the Alchemy rebalance - slower tempo compared to the paper version
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
                TargetController.YOU,
                false
        );
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private ALuminarchAspirant(final ALuminarchAspirant card) {
        super(card);
    }

    @Override
    public ALuminarchAspirant copy() {
        return new ALuminarchAspirant(this);
    }
}

