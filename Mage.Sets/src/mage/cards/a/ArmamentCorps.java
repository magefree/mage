package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.counter.DistributeCountersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanentAmount;

/**
 *
 * @author LevelX2
 */
public final class ArmamentCorps extends CardImpl {

    public ArmamentCorps(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}{B}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Armament Corps enters the battlefield, distribute two +1/+1 counters among one or two target creatures you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DistributeCountersEffect(CounterType.P1P1, 2, false, "one or two target creatures you control"), false);
        ability.addTarget(new TargetCreaturePermanentAmount(2, StaticFilters.FILTER_CONTROLLED_CREATURES));
        this.addAbility(ability);
    }

    private ArmamentCorps(final ArmamentCorps card) {
        super(card);
    }

    @Override
    public ArmamentCorps copy() {
        return new ArmamentCorps(this);
    }
}
