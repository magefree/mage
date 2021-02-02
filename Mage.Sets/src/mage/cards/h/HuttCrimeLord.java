
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

/**
 *
 * @author Styxo
 */
public final class HuttCrimeLord extends CardImpl {

    public HuttCrimeLord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        this.subtype.add(SubType.HUTT);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Hutt Crime Lord enters the battlefield with a bounty counter on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.BOUNTY.createInstance()), "with a bounty counter on it"));

        // Whenever Hutt Crime Lord attacks, it does not untap during its controller's next untap step
        this.addAbility(new AttacksTriggeredAbility(new DontUntapInControllersNextUntapStepSourceEffect(), false));
    }

    private HuttCrimeLord(final HuttCrimeLord card) {
        super(card);
    }

    @Override
    public HuttCrimeLord copy() {
        return new HuttCrimeLord(this);
    }
}
