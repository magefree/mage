
package mage.cards.m;

import java.util.UUID;
import mage.abilities.mana.AnyColorPermanentTypesManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SupertypePredicate;

/**
 *
 * @author CountAndromalius
 */
public final class MoxAmber extends CardImpl {

    public MoxAmber(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{0}");
        addSuperType(SuperType.LEGENDARY);

        // {tap}: Add one mana pool of any color among legendary creatures and planeswalkers you control.
        FilterPermanent filter = new FilterPermanent("legendary creatures and planeswalkers");
        filter.add(Predicates.or(
                Predicates.and(
                    new CardTypePredicate(CardType.CREATURE),
                    new SupertypePredicate(SuperType.LEGENDARY)
                ),
            new CardTypePredicate(CardType.PLANESWALKER))
        );
        this.addAbility(new AnyColorPermanentTypesManaAbility(TargetController.YOU, filter));
    }

    public MoxAmber(final MoxAmber card) {
        super(card);
    }

    @Override
    public MoxAmber copy() {
        return new MoxAmber(this);
    }
}
