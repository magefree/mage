
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.LicidAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.FearAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Zone;

/**
 *
 * @author emerald000
 */
public final class CorruptingLicid extends CardImpl {

    public CorruptingLicid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.LICID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {B}, {tap}: Corrupting Licid loses this ability and becomes an Aura enchantment with enchant creature. Attach it to target creature. You may pay {B} to end this effect.
        this.addAbility(new LicidAbility(new ColoredManaCost(ColoredManaSymbol.B), new ColoredManaCost(ColoredManaSymbol.B)));
        
        // Enchanted creature has fear.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(FearAbility.getInstance(), AttachmentType.AURA)));
    }

    private CorruptingLicid(final CorruptingLicid card) {
        super(card);
    }

    @Override
    public CorruptingLicid copy() {
        return new CorruptingLicid(this);
    }
}
