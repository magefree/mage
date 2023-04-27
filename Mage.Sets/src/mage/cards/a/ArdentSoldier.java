
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.KickerAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

/**
 *
 * @author LoneFox

 */
public final class ArdentSoldier extends CardImpl {

    public ArdentSoldier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Kicker {2}
        this.addAbility(new KickerAbility("{2}"));
        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        // If Ardent Soldier was kicked, it enters the battlefield with a +1/+1 counter on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(1)),
            KickedCondition.ONCE, "If {this} was kicked, it enters the battlefield with a +1/+1 counter on it.", ""));
    }

    private ArdentSoldier(final ArdentSoldier card) {
        super(card);
    }

    @Override
    public ArdentSoldier copy() {
        return new ArdentSoldier(this);
    }
}
