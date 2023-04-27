
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetEnchantmentPermanent;

/**
 * @author nantuko
 */
public final class SilverchaseFox extends CardImpl {

    public SilverchaseFox(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.FOX);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {1}{W}, Sacrifice Silverchase Fox: Exile target enchantment.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExileTargetEffect(), new ManaCostsImpl<>("{1}{W}"));
        ability.addTarget(new TargetEnchantmentPermanent());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private SilverchaseFox(final SilverchaseFox card) {
        super(card);
    }

    @Override
    public SilverchaseFox copy() {
        return new SilverchaseFox(this);
    }
}
