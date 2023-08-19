
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.search.SearchLibraryPutOnLibraryEffect;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterEnchantmentCard;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author emerald000
 */
public final class SterlingGrove extends CardImpl {
    
    public SterlingGrove(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{G}{W}");

        // Other enchantments you control have shroud.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(ShroudAbility.getInstance(), Duration.WhileOnBattlefield, new FilterEnchantmentPermanent("enchantments"), true)));
        
        // {1}, Sacrifice Sterling Grove: Search your library for an enchantment card and reveal that card. Shuffle your library, then put the card on top of it.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SearchLibraryPutOnLibraryEffect(new TargetCardInLibrary(new FilterEnchantmentCard("enchantment card")), true), new GenericManaCost(1));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private SterlingGrove(final SterlingGrove card) {
        super(card);
    }

    @Override
    public SterlingGrove copy() {
        return new SterlingGrove(this);
    }
}
