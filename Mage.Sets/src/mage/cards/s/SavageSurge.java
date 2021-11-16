package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Plopman
 */
public final class SavageSurge extends CardImpl {

    public SavageSurge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Target creature gets +2/+2 until end of turn. Untap that creature.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        Effect boostEffect = new BoostTargetEffect(2, 2, Duration.EndOfTurn);
        boostEffect.setOutcome(Outcome.Benefit);
        this.getSpellAbility().addEffect(boostEffect);
        Effect untapEffect = new UntapTargetEffect();
        untapEffect.setOutcome(Outcome.Benefit);
        untapEffect.setText("Untap that creature");
        this.getSpellAbility().addEffect(untapEffect);
    }

    private SavageSurge(final SavageSurge card) {
        super(card);
    }

    @Override
    public SavageSurge copy() {
        return new SavageSurge(this);
    }
}
