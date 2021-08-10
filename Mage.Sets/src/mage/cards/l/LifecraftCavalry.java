
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.RevoltCondition;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.watchers.common.RevoltWatcher;

/**
 *
 * @author fireshoes
 */
public final class LifecraftCavalry extends CardImpl {

    public LifecraftCavalry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // <i>Revolt</i> &mdash; Lifecraft Cavalry enters the battlefield with two +1/+1 counters on it if a permanent you controlled left the battlefield this turn.
        this.addAbility(
                new EntersBattlefieldAbility(
                        new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)),
                        false,
                        RevoltCondition.instance,
                        "<i>Revolt</i> &mdash; {this} enters the battlefield with two +1/+1 counters on it if a permanent you controlled left the battlefield this turn.", null),
                new RevoltWatcher()
        );
    }

    private LifecraftCavalry(final LifecraftCavalry card) {
        super(card);
    }

    @Override
    public LifecraftCavalry copy() {
        return new LifecraftCavalry(this);
    }
}
