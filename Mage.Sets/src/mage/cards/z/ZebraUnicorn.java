package mage.cards.z;

import mage.MageInt;
import mage.abilities.common.DealsDamageSourceTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class ZebraUnicorn extends CardImpl {

    public ZebraUnicorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{W}");
        this.subtype.add(SubType.UNICORN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Zebra Unicorn deals damage, you gain that much life.
        this.addAbility(new DealsDamageSourceTriggeredAbility(new GainLifeEffect(SavedDamageValue.MUCH)));
    }

    private ZebraUnicorn(final ZebraUnicorn card) {
        super(card);
    }

    @Override
    public ZebraUnicorn copy() {
        return new ZebraUnicorn(this);
    }
}
