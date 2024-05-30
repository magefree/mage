package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.abilities.common.LandfallAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.BestowAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author grimreap124
 */
public final class GlyphElemental extends CardImpl {

    private static final DynamicValue xValue = new CountersSourceCount(CounterType.P1P1);

    public GlyphElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] { CardType.ENCHANTMENT, CardType.CREATURE }, "{1}{W}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Bestow {1}{W}
        this.addAbility(new BestowAbility(this, "{1}{W}"));

        // Landfall -- Whenever a land enters the battlefield under your control, put a +1/+1 counter on Glyph Elemental.
        this.addAbility(new LandfallAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance())));

        // Enchanted creature gets +1/+1 for each +1/+1 counter on Glyph Elemental.
        this.addAbility(new SimpleStaticAbility(new BoostEnchantedEffect(xValue, xValue)));
    }

    private GlyphElemental(final GlyphElemental card) {
        super(card);
    }

    @Override
    public GlyphElemental copy() {
        return new GlyphElemental(this);
    }
}
