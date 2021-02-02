
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.abilities.keyword.WitherAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author Loki
 */
public final class NoxiousHatchling extends CardImpl {

    private static final FilterSpell filterBlackSpell = new FilterSpell("a black spell");
    private static final FilterSpell filterGreenSpell = new FilterSpell("a green spell");

    static {
        filterBlackSpell.add(new ColorPredicate(ObjectColor.BLACK));
        filterGreenSpell.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public NoxiousHatchling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B/G}");
        this.subtype.add(SubType.ELEMENTAL);


        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Noxious Hatchling enters the battlefield with four -1/-1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.M1M1.createInstance(4)),"with four -1/-1 counters on it"));
        // Wither (This deals damage to creatures in the form of -1/-1 counters.)
        this.addAbility(WitherAbility.getInstance());
        // Whenever you cast a black spell, remove a -1/-1 counter from Noxious Hatchling.
        this.addAbility(new SpellCastControllerTriggeredAbility(new RemoveCounterSourceEffect(CounterType.M1M1.createInstance()), filterBlackSpell, false));
        // Whenever you cast a green spell, remove a -1/-1 counter from Noxious Hatchling.
        this.addAbility(new SpellCastControllerTriggeredAbility(new RemoveCounterSourceEffect(CounterType.M1M1.createInstance()), filterGreenSpell, false));
    }

    private NoxiousHatchling(final NoxiousHatchling card) {
        super(card);
    }

    @Override
    public NoxiousHatchling copy() {
        return new NoxiousHatchling(this);
    }
}
