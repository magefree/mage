package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesMonstrousSourceTriggeredAbility;
import mage.abilities.dynamicvalue.common.GetMonstrosityXValue;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.MonstrosityAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.TargetsCountAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VitalityHunter extends CardImpl {

    public VitalityHunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.NIGHTMARE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // {X}{W}{W}: Monstrosity X.
        this.addAbility(new MonstrosityAbility("{X}{W}{W}", Integer.MAX_VALUE));

        // When Vitality Hunter becomes monstrous, put a lifelink counter on each of up to X target creatures.
        Ability ability = new BecomesMonstrousSourceTriggeredAbility(
                new AddCountersTargetEffect(CounterType.LIFELINK.createInstance())
                        .setText("put a lifelink counter on each of up to X target creatures")
        );
        ability.setTargetAdjuster(new TargetsCountAdjuster(GetMonstrosityXValue.instance));
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);
    }

    private VitalityHunter(final VitalityHunter card) {
        super(card);
    }

    @Override
    public VitalityHunter copy() {
        return new VitalityHunter(this);
    }
}
