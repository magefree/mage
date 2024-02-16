
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.KickerAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

/**
 *
 * @author tcontis
 */
public final class UntamedKavu extends CardImpl {

    public UntamedKavu(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.KAVU);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Kicker {3}
        this.addAbility(new KickerAbility("{3}"));

        //Vigilance, Trample
        this.addAbility(VigilanceAbility.getInstance());
        this.addAbility(TrampleAbility.getInstance());

        // If Untamed Kavu was kicked, it enters the battlefield with three +1/+1 counters on it.
        Ability ability = new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(3)),
                KickedCondition.ONCE,
                "If Untamed Kavu was kicked, it enters the battlefield with three +1/+1 counters on it.", "");
        this.addAbility(ability);
    }

    private UntamedKavu(final UntamedKavu card) {
        super(card);
    }

    @Override
    public UntamedKavu copy() {
        return new UntamedKavu(this);
    }
}