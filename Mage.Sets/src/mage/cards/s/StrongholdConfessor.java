
package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.KickerAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 *
 * @author Styxo
 */
public final class StrongholdConfessor extends CardImpl {

    public StrongholdConfessor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Menace
        this.addAbility(new MenaceAbility());
        // Kicker {3} (You may pay an additional {3} as you cast this spell.)
        this.addAbility(new KickerAbility("{3}"));
        // If Stronghold Confessor was kicked, it enters the battlefield with two +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)), KickedCondition.ONCE, "If {this} was kicked, it enters the battlefield with two +1/+1 counters on it.", ""));
    }

    private StrongholdConfessor(final StrongholdConfessor card) {
        super(card);
    }

    @Override
    public StrongholdConfessor copy() {
        return new StrongholdConfessor(this);
    }
}
