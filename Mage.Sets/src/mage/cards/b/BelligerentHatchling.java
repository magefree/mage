
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 * @author Loki
 */
public final class BelligerentHatchling extends CardImpl {

    private static final FilterSpell filterRedSpell = new FilterSpell("a red spell");
    private static final FilterSpell filterWhiteSpell = new FilterSpell("a white spell");

    static {
        filterRedSpell.add(new ColorPredicate(ObjectColor.RED));
        filterWhiteSpell.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public BelligerentHatchling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R/W}");
        this.subtype.add(SubType.ELEMENTAL);


        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());
        // Belligerent Hatchling enters the battlefield with four -1/-1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.M1M1.createInstance(4)),"with four -1/-1 counters on it"));
        // Whenever you cast a red spell, remove a -1/-1 counter from Belligerent Hatchling.
        this.addAbility(new SpellCastControllerTriggeredAbility(new RemoveCounterSourceEffect(CounterType.M1M1.createInstance(1)), filterRedSpell, false));
        // Whenever you cast a white spell, remove a -1/-1 counter from Belligerent Hatchling.
        this.addAbility(new SpellCastControllerTriggeredAbility(new RemoveCounterSourceEffect(CounterType.M1M1.createInstance(1)), filterWhiteSpell, false));
    }

    private BelligerentHatchling(final BelligerentHatchling card) {
        super(card);
    }

    @Override
    public BelligerentHatchling copy() {
        return new BelligerentHatchling(this);
    }
}
