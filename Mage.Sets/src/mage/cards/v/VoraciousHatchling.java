
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.abilities.keyword.LifelinkAbility;
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
public final class VoraciousHatchling extends CardImpl {

    private static final FilterSpell filterWhiteSpell = new FilterSpell("a white spell");
    private static final FilterSpell filterBlackSpell = new FilterSpell("a black spell");

    static {
        filterWhiteSpell.add(new ColorPredicate(ObjectColor.WHITE));
        filterBlackSpell.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public VoraciousHatchling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W/B}");
        this.subtype.add(SubType.ELEMENTAL);


        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());
        // Voracious Hatchling enters the battlefield with four -1/-1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.M1M1.createInstance(4)),"with four -1/-1 counters on it"));
        // Whenever you cast a white spell, remove a -1/-1 counter from Voracious Hatchling.
        this.addAbility(new SpellCastControllerTriggeredAbility(new RemoveCounterSourceEffect(CounterType.M1M1.createInstance(1)), filterWhiteSpell, false));
        // Whenever you cast a black spell, remove a -1/-1 counter from Voracious Hatchling.
        this.addAbility(new SpellCastControllerTriggeredAbility(new RemoveCounterSourceEffect(CounterType.M1M1.createInstance(1)), filterBlackSpell, false));
    }

    private VoraciousHatchling(final VoraciousHatchling card) {
        super(card);
    }

    @Override
    public VoraciousHatchling copy() {
        return new VoraciousHatchling(this);
    }
}
