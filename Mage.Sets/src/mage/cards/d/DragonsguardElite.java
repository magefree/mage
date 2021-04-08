package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.MagecraftAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoubleCountersSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DragonsguardElite extends CardImpl {

    public DragonsguardElite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Magecraft — Whenever you cast or copy an instant or sorcery spell, put a +1/+1 counter on Dragonsguard Elite.
        this.addAbility(new MagecraftAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance())));

        // {4}{G}{G}: Double the number of +1/+1 counters on Dragonsguard Elite.
        this.addAbility(new SimpleActivatedAbility(new DoubleCountersSourceEffect(CounterType.P1P1), new ManaCostsImpl<>("{4}{G}{G}")));
    }

    private DragonsguardElite(final DragonsguardElite card) {
        super(card);
    }

    @Override
    public DragonsguardElite copy() {
        return new DragonsguardElite(this);
    }
}
