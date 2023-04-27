
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.KickerAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

/**
 *
 * @author michael.napoleon@gmail.com
 */
public final class LlanowarElite extends CardImpl {

    public LlanowarElite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.ELF);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Kicker {8}
        this.addAbility(new KickerAbility("{8}"));
        
        // Trample
        this.addAbility(TrampleAbility.getInstance());
        
        // If Llanowar Elite was kicked, it enters the battlefield with five +1/+1 counters on it.
        Ability ability = new EntersBattlefieldAbility(
                new ConditionalOneShotEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance(5)), KickedCondition.ONCE, ""),
                  "If {this} was kicked, it enters the battlefield with five +1/+1 counters on it.");
        this.addAbility(ability);
        
    }

    private LlanowarElite(final LlanowarElite card) {
        super(card);
    }

    @Override
    public LlanowarElite copy() {
        return new LlanowarElite(this);
    }
}
