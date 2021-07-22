
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
import mage.filter.predicate.permanent.TappedPredicate;

/**
 *
 * @author North
 */
public final class ToilToRenown extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("tapped artifact, creature, and land you control");

    static {
        filter.add(TappedPredicate.TAPPED);
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate(),
                CardType.LAND.getPredicate()));
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public ToilToRenown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{G}");


        // You gain 1 life for each tapped artifact, creature, and land you control.
        this.getSpellAbility().addEffect(new GainLifeEffect(new PermanentsOnBattlefieldCount(filter)));
    }

    private ToilToRenown(final ToilToRenown card) {
        super(card);
    }

    @Override
    public ToilToRenown copy() {
        return new ToilToRenown(this);
    }
}
