package mage.cards.r;

import mage.abilities.Mode;
import mage.abilities.effects.common.PutOnTopOrBottomLibraryTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RunAshore extends CardImpl {

    public RunAshore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{U}{U}");

        // Choose one or both —
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(2);

        // • The owner of target nonland permanent puts it on the top or bottom of their library.
        this.getSpellAbility().addEffect(new PutOnTopOrBottomLibraryTargetEffect());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());

        // • Return target nonland permanent to its owner's hand.
        Mode mode = new Mode(new ReturnToHandTargetEffect());
        mode.addTarget(new TargetNonlandPermanent());
        this.getSpellAbility().addMode(mode);
    }

    private RunAshore(final RunAshore card) {
        super(card);
    }

    @Override
    public RunAshore copy() {
        return new RunAshore(this);
    }
}
