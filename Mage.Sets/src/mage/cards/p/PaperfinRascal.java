
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
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
public final class PaperfinRascal extends CardImpl {

    public PaperfinRascal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Paperfin Rascal enters the battlefield, clash with an opponent. If you win, put a +1/+1 counter on Paperfin Rascal.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DoIfClashWonEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance()))));   
    }

    private PaperfinRascal(final PaperfinRascal card) {
        super(card);
    }

    @Override
    public PaperfinRascal copy() {
        return new PaperfinRascal(this);
    }
}
