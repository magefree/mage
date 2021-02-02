
package mage.cards.r;

import java.util.UUID;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.GainLifePlayersCost;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterEnchantmentPermanent;

/**
 *
 * @author LevelX2
 */
public final class ReverentSilence extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("If you control a Forest");
    
    static {
        filter.add(SubType.FOREST.getPredicate());
    }
    
    public ReverentSilence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{G}");


        // If you control a Forest, rather than pay Reverent Silence's mana cost, you may have each other player gain 6 life.
        this.addAbility(new AlternativeCostSourceAbility(new GainLifePlayersCost(6), new PermanentsOnTheBattlefieldCondition(filter)));
        
        // Destroy all enchantments.
        this.getSpellAbility().addEffect(new DestroyAllEffect(new FilterEnchantmentPermanent("enchantments")));
    }

    private ReverentSilence(final ReverentSilence card) {
        super(card);
    }

    @Override
    public ReverentSilence copy() {
        return new ReverentSilence(this);
    }
}
