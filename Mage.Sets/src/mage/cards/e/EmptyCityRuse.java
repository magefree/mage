package mage.cards.e;

import java.util.UUID;
import mage.abilities.effects.common.SkipCombatStepEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetOpponent;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class EmptyCityRuse extends CardImpl {

    public EmptyCityRuse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{W}");

        // Target opponent skips all combat phases of their next turn.
        this.getSpellAbility().addEffect(new SkipCombatStepEffect(Duration.UntilYourNextTurn).setText("Target opponent skips all combat phases of their next turn."));
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private EmptyCityRuse(final EmptyCityRuse card) {
        super(card);
    }

    @Override
    public EmptyCityRuse copy() {
        return new EmptyCityRuse(this);
    }
}
