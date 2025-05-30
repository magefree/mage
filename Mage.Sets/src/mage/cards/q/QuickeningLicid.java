
package mage.cards.q;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.LicidAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.FirstStrikeAbility;
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
public final class QuickeningLicid extends CardImpl {

    public QuickeningLicid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.LICID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}{W}, {tap}: Quickening Licid loses this ability and becomes an Aura enchantment with enchant creature. Attach it to target creature. You may pay {W} to end this effect.
        this.addAbility(new LicidAbility(new ManaCostsImpl<>("{1}{W}"), new ColoredManaCost(ColoredManaSymbol.W)));
        
        // Enchanted creature has first strike.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(FirstStrikeAbility.getInstance(), AttachmentType.AURA)));
    }

    private QuickeningLicid(final QuickeningLicid card) {
        super(card);
    }

    @Override
    public QuickeningLicid copy() {
        return new QuickeningLicid(this);
    }
}
