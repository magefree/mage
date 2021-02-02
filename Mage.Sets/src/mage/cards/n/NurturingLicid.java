
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.LicidAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.RegenerateAttachedEffect;
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
public final class NurturingLicid extends CardImpl {

    public NurturingLicid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.LICID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {G}, {tap}: Nurturing Licid loses this ability and becomes an Aura enchantment with enchant creature. Attach it to target creature. You may pay {G} to end this effect.
        this.addAbility(new LicidAbility(new ColoredManaCost(ColoredManaSymbol.G), new ColoredManaCost(ColoredManaSymbol.G)));
        
        // {G}: Regenerate enchanted creature.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateAttachedEffect(AttachmentType.AURA), new ColoredManaCost(ColoredManaSymbol.G)));
    }

    private NurturingLicid(final NurturingLicid card) {
        super(card);
    }

    @Override
    public NurturingLicid copy() {
        return new NurturingLicid(this);
    }
}
