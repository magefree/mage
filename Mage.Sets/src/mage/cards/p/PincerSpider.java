
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.KickerAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

/**
 *
 * @author michael.napoleon@gmail.com
 */
public final class PincerSpider extends CardImpl {

    public PincerSpider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.SPIDER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Kicker {3}
        this.addAbility(new KickerAbility("{3}"));
        
        // Reach
        this.addAbility(ReachAbility.getInstance());
        
        // If Pincer Spider was kicked, it enters the battlefield with a +1/+1 counter on it.
        Ability ability = new EntersBattlefieldAbility(
                new ConditionalOneShotEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance(1)), KickedCondition.ONCE, ""),
                  "If {this} was kicked, it enters the battlefield with a +1/+1 counter on it.");
        this.addAbility(ability);
        
    }

    private PincerSpider(final PincerSpider card) {
        super(card);
    }

    @Override
    public PincerSpider copy() {
        return new PincerSpider(this);
    }
}
