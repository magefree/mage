
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LicidAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.AddCardTypeAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author emerald000
 */
public final class TransmogrifyingLicid extends CardImpl {

    public TransmogrifyingLicid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{3}");
        this.subtype.add(SubType.LICID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {1}, {tap}: Transmogrifying Licid loses this ability and becomes an Aura enchantment with enchant creature. Attach it to target creature. You may pay {1} to end this effect.
        this.addAbility(new LicidAbility(new GenericManaCost(1), new GenericManaCost(1)));
        
        // Enchanted creature gets +1/+1 and is an artifact in addition to its other types.
        Ability ability = new SimpleStaticAbility(new BoostEnchantedEffect(1, 1));
        ability.addEffect(new AddCardTypeAttachedEffect(CardType.ARTIFACT, AttachmentType.AURA));
        this.addAbility(ability);
    }

    private TransmogrifyingLicid(final TransmogrifyingLicid card) {
        super(card);
    }

    @Override
    public TransmogrifyingLicid copy() {
        return new TransmogrifyingLicid(this);
    }
}
