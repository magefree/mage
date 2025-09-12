

package mage.cards.t;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;

import java.util.UUID;

/**
 * @author Backfir3
 */
public final class TitaniasChosen extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("green spell");

    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public TitaniasChosen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ARCHER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever a player casts a green spell, put a +1/+1 counter on Titania's Chosen.
        this.addAbility(new SpellCastAllTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), filter, false));
    }

    private TitaniasChosen(final TitaniasChosen card) {
        super(card);
    }

    @Override
    public TitaniasChosen copy() {
        return new TitaniasChosen(this);
    }

}
