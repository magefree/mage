

package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.counter.DistributeCountersEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanentAmount;

/**
 * @author LevelX
 */
public final class JuganTheRisingStar extends CardImpl {

    public JuganTheRisingStar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Jugan, the Rising Star dies, you may distribute five +1/+1 counters among any number of target creatures.
        Ability ability = new DiesSourceTriggeredAbility(new DistributeCountersEffect(CounterType.P1P1, 5, false, "any number of target creatures"), true);
        ability.addTarget(new TargetCreaturePermanentAmount(5));
        this.addAbility(ability);
    }

    private JuganTheRisingStar(final JuganTheRisingStar card) {
        super(card);
    }

    @Override
    public JuganTheRisingStar copy() {
        return new JuganTheRisingStar(this);
    }

}
