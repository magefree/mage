
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.LicidAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.ControlEnchantedEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Zone;

/**
 *
 * @author emerald000
 */
public final class DominatingLicid extends CardImpl {

    public DominatingLicid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}{U}");
        this.subtype.add(SubType.LICID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}{U}{U}, {tap}: Dominating Licid loses this ability and becomes an Aura enchantment with enchant creature. Attach it to target creature. You may pay {U} to end this effect.
        this.addAbility(new LicidAbility(new ManaCostsImpl<>("{1}{U}{U}"), new ColoredManaCost(ColoredManaSymbol.U)));
        
        // You control enchanted creature.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ControlEnchantedEffect()));
    }

    private DominatingLicid(final DominatingLicid card) {
        super(card);
    }

    @Override
    public DominatingLicid copy() {
        return new DominatingLicid(this);
    }
}
