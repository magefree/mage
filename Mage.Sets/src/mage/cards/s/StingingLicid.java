
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesTappedAttachedTriggeredAbility;
import mage.abilities.common.LicidAbility;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageAttachedControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ColoredManaSymbol;

/**
 *
 * @author emerald000
 */
public final class StingingLicid extends CardImpl {

    public StingingLicid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.LICID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}{U}, {tap}: Stinging Licid loses this ability and becomes an Aura enchantment with enchant creature. Attach it to target creature. You may pay {U} to end this effect.
        this.addAbility(new LicidAbility(new ManaCostsImpl<>("{1}{U}"), new ColoredManaCost(ColoredManaSymbol.U)));
        
        // Whenever enchanted creature becomes tapped, Stinging Licid deals 2 damage to that creature's controller.
        this.addAbility(new BecomesTappedAttachedTriggeredAbility(new DamageAttachedControllerEffect(2), "enchanted creature"));
    }

    private StingingLicid(final StingingLicid card) {
        super(card);
    }

    @Override
    public StingingLicid copy() {
        return new StingingLicid(this);
    }
}
