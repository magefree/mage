
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.MorphAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class BirchloreRangers extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("untapped Elves you control");

    static {
        filter.add(Predicates.not(new TappedPredicate()));
        filter.add(new SubtypePredicate(SubType.ELF));
    }

    public BirchloreRangers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.ELF, SubType.DRUID);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Tap two untapped Elves you control: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility(new TapTargetCost(new TargetControlledCreaturePermanent(2, 2, filter, false))));
        // Morph {G}
        this.addAbility(new MorphAbility(this, new ManaCostsImpl("{G}")));
    }

    public BirchloreRangers(final BirchloreRangers card) {
        super(card);
    }

    @Override
    public BirchloreRangers copy() {
        return new BirchloreRangers(this);
    }
}
