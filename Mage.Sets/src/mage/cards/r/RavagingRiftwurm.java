
package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.KickerAbility;
import mage.abilities.keyword.VanishingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class RavagingRiftwurm extends CardImpl {

    public RavagingRiftwurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");
        this.subtype.add(SubType.WURM);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Kicker {4}
        this.addAbility(new KickerAbility("{4}"));

        // Vanishing 2
        this.addAbility(new VanishingAbility(2));

        // If Ravaging Riftwurm was kicked, it enters the battlefield with three additional time counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.TIME.createInstance(3)),
                KickedCondition.ONCE, "If {this} was kicked, it enters the battlefield with three additional time counters on it.", ""));
    }

    private RavagingRiftwurm(final RavagingRiftwurm card) {
        super(card);
    }

    @Override
    public RavagingRiftwurm copy() {
        return new RavagingRiftwurm(this);
    }
}
