
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.dynamicvalue.common.CreaturesDiedThisTurnCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.watchers.common.CreaturesDiedWatcher;

/**
 *
 * @author TheElk801
 */
public final class BloodcrazedPaladin extends CardImpl {

    public BloodcrazedPaladin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Bloodcrazed Paladin enters the battlefield with a +1/+1 counter on it for each creature that died this turn.
        Effect effect = new AddCountersSourceEffect(CounterType.P1P1.createInstance(0),
                new CreaturesDiedThisTurnCount(), true);
        effect.setText("with a +1/+1 counter on it for each creature that died this turn.");
        this.addAbility(new EntersBattlefieldAbility(effect), new CreaturesDiedWatcher());
    }

    public BloodcrazedPaladin(final BloodcrazedPaladin card) {
        super(card);
    }

    @Override
    public BloodcrazedPaladin copy() {
        return new BloodcrazedPaladin(this);
    }
}
