
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterInstantOrSorcerySpell;

/**
 *
 * @author LevelX2
 */
public final class PyreHound extends CardImpl {

    public PyreHound(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.DOG);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Whenever you cast an instant or sorcery spell, put a +1/+1 counter on Pyre Hound.
        this.addAbility(new SpellCastControllerTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                new FilterInstantOrSorcerySpell("an instant or sorcery spell"), false));
    }

    private PyreHound(final PyreHound card) {
        super(card);
    }

    @Override
    public PyreHound copy() {
        return new PyreHound(this);
    }
}
