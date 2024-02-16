package mage.cards.q;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.UnlessPaysDelayedEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.target.common.TargetPlayerOrPlaneswalker;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class QuenchableFire extends CardImpl {

    public QuenchableFire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // Quenchable Fire deals 3 damage to target player.
        this.getSpellAbility().addTarget(new TargetPlayerOrPlaneswalker());
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        // It deals an additional 3 damage to that player or planeswalker at the beginning of your next upkeep step unless that player or that planeswalker’s controller pays {U} before that step.
        this.getSpellAbility().addEffect(new UnlessPaysDelayedEffect(new ManaCostsImpl<>("{U}"),
                new DamageTargetEffect(3, true, "that player or that planeswalker's controller"), PhaseStep.UPKEEP, false,
                "It deals an additional 3 damage to that player or planeswalker at the beginning of your next upkeep step unless that player or that planeswalker's controller pays {U} before that step."));
    }

    private QuenchableFire(final QuenchableFire card) {
        super(card);
    }

    @Override
    public QuenchableFire copy() {
        return new QuenchableFire(this);
    }
}
