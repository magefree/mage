
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author cbt33
 */
public final class SetonKrosanProtector extends CardImpl {
    
    private final static FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("untapped Druid you control");
    
    static {
        filter.add(Predicates.not(new TappedPredicate()));
        filter.add(new SubtypePredicate(SubType.DRUID));
    }

    public SetonKrosanProtector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}{G}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.CENTAUR);
        this.subtype.add(SubType.DRUID);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Tap an untapped Druid you control: Add {G}.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.GreenMana(1),
                new TapTargetCost(new TargetControlledCreaturePermanent(1, 1, filter, true))));
    }

    public SetonKrosanProtector(final SetonKrosanProtector card) {
        super(card);
    }

    @Override
    public SetonKrosanProtector copy() {
        return new SetonKrosanProtector(this);
    }
}
