
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.LicidAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.combat.CantAttackAttachedEffect;
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
public final class CalmingLicid extends CardImpl {

    public CalmingLicid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.LICID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {W}, {tap}: Calming Licid loses this ability and becomes an Aura enchantment with enchant creature. Attach it to target creature. You may pay {W} to end this effect.
        this.addAbility(new LicidAbility(new ColoredManaCost(ColoredManaSymbol.W), new ColoredManaCost(ColoredManaSymbol.W)));
        
        // Enchanted creature can't attack.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantAttackAttachedEffect(AttachmentType.AURA)));
    }

    private CalmingLicid(final CalmingLicid card) {
        super(card);
    }

    @Override
    public CalmingLicid copy() {
        return new CalmingLicid(this);
    }
}
