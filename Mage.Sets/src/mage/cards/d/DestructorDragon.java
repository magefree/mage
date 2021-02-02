
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

/**
 *
 * @author fireshoes
 */
public final class DestructorDragon extends CardImpl {
    
    private static final FilterPermanent filter = new FilterPermanent("noncreature permanent");
    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public DestructorDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{G}");
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Destructor Dragon dies, destroy target noncreature permanent.
        Ability ability = new DiesSourceTriggeredAbility(new DestroyTargetEffect(), false);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private DestructorDragon(final DestructorDragon card) {
        super(card);
    }

    @Override
    public DestructorDragon copy() {
        return new DestructorDragon(this);
    }
}
