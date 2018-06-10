
package mage.cards.t;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.filter.predicate.permanent.TappedPredicate;

/**
 *
 * @author North
 */
public final class ToilToRenown extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("tapped artifact, creature, and land you control");

    static {
        filter.add(new TappedPredicate());
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.LAND)));
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public ToilToRenown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{G}");


        // You gain 1 life for each tapped artifact, creature, and land you control.
        this.getSpellAbility().addEffect(new GainLifeEffect(new PermanentsOnBattlefieldCount(filter)));
    }

    public ToilToRenown(final ToilToRenown card) {
        super(card);
    }

    @Override
    public ToilToRenown copy() {
        return new ToilToRenown(this);
    }
}
