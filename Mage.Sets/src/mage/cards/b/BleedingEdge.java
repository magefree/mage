package mage.cards.b;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.keyword.AmassEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BleedingEdge extends CardImpl {

    public BleedingEdge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}{B}");

        // Up to one target creature gets -2/-2 until end of turn. Amass 2.
        this.getSpellAbility().addEffect(new BoostTargetEffect(-2, -2, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 1));
        this.getSpellAbility().addEffect(new AmassEffect(2, SubType.ZOMBIE));
    }

    private BleedingEdge(final BleedingEdge card) {
        super(card);
    }

    @Override
    public BleedingEdge copy() {
        return new BleedingEdge(this);
    }
}
// It's nanotech, you like it?