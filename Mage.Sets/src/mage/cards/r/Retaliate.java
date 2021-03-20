
package mage.cards.r;

import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.other.DamagedPlayerThisTurnPredicate;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class Retaliate extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creatures that dealt damage to you this turn");

    static {
        filter.add(new DamagedPlayerThisTurnPredicate(TargetController.YOU));
    }

    public Retaliate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}{W}");

        // Destroy all creatures that dealt damage to you this turn.
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter));
    }

    private Retaliate(final Retaliate card) {
        super(card);
    }

    @Override
    public Retaliate copy() {
        return new Retaliate(this);
    }
}
