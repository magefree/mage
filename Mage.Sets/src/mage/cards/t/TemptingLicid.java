
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.LicidAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.combat.MustBeBlockedByAllAttachedEffect;
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
public final class TemptingLicid extends CardImpl {

    public TemptingLicid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.LICID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {G}, {tap}: Tempting Licid loses this ability and becomes an Aura enchantment with enchant creature. Attach it to target creature. You may pay {G} to end this effect.
        this.addAbility(new LicidAbility(new ColoredManaCost(ColoredManaSymbol.G), new ColoredManaCost(ColoredManaSymbol.G)));
        
        // All creatures able to block enchanted creature do so.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MustBeBlockedByAllAttachedEffect(AttachmentType.AURA)));
    }

    private TemptingLicid(final TemptingLicid card) {
        super(card);
    }

    @Override
    public TemptingLicid copy() {
        return new TemptingLicid(this);
    }
}
