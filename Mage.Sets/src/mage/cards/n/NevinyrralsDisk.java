
package mage.cards.n;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;

/**
 *
 * @author jeffwadsworth

 */
public final class NevinyrralsDisk extends CardImpl {
    
    private static final FilterPermanent filter = new FilterPermanent("artifacts, creatures, and enchantments");
    
    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()));
    }

    public NevinyrralsDisk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // Nevinyrral's Disk enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        
        // {1}, {tap}: Destroy all artifacts, creatures, and enchantments.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyAllEffect(filter, false), new ManaCostsImpl<>("{1}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private NevinyrralsDisk(final NevinyrralsDisk card) {
        super(card);
    }

    @Override
    public NevinyrralsDisk copy() {
        return new NevinyrralsDisk(this);
    }
}
