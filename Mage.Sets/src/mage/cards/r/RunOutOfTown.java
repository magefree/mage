package mage.cards.r;

import mage.abilities.effects.common.PutOnTopOrBottomLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author weirddan455
 */
public final class RunOutOfTown extends CardImpl {

    public RunOutOfTown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}");

        // The owner of target nonland permanent puts it on the top or bottom of their library.
        this.getSpellAbility().addEffect(new PutOnTopOrBottomLibraryTargetEffect());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
    }

    private RunOutOfTown(final RunOutOfTown card) {
        super(card);
    }

    @Override
    public RunOutOfTown copy() {
        return new RunOutOfTown(this);
    }
}
