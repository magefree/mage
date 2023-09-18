
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;

/**
 *
 * @author daagar
 */
public final class MagusOfTheDisk extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifacts, creatures, and enchantments");
    
    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()));
    }
    
    public MagusOfTheDisk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Magus of the Disk enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        
        // {1}, {tap}: Destroy all artifacts, creatures, and enchantments.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyAllEffect(filter, false), new ManaCostsImpl<>("{1}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private MagusOfTheDisk(final MagusOfTheDisk card) {
        super(card);
    }

    @Override
    public MagusOfTheDisk copy() {
        return new MagusOfTheDisk(this);
    }
}
