package mage.cards.d;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.EntersWithCountersControlledEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DragonstormGlobe extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.DRAGON, "Dragon");

    public DragonstormGlobe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Each Dragon you control enters with an additional +1/+1 counter on it.
        this.addAbility(new SimpleStaticAbility(new EntersWithCountersControlledEffect(
                filter, CounterType.P1P1.createInstance(), false
        )));

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());
    }

    private DragonstormGlobe(final DragonstormGlobe card) {
        super(card);
    }

    @Override
    public DragonstormGlobe copy() {
        return new DragonstormGlobe(this);
    }
}
