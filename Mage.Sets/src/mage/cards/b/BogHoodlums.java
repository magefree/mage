
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DoIfClashWonEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

/**
 *
 * @author LevelX2
 */
public final class BogHoodlums extends CardImpl {

    public BogHoodlums(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{B}");
        this.subtype.add(SubType.GOBLIN, SubType.WARRIOR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(1);

        // Bog Hoodlums can't block.
        this.addAbility(new CantBlockAbility());
        // When Bog Hoodlums enters the battlefield, clash with an opponent. If you win, put a +1/+1 counter on Bog Hoodlums.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DoIfClashWonEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance()))));        
    }

    private BogHoodlums(final BogHoodlums card) {
        super(card);
    }

    @Override
    public BogHoodlums copy() {
        return new BogHoodlums(this);
    }
}
