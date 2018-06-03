

package mage.cards.q;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.UnlessPaysDelayedEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.target.TargetPlayer;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class QuenchableFire extends CardImpl {

    public QuenchableFire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{R}");

        // Quenchable Fire deals 3 damage to target player.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        // It deals an additional 3 damage to that player at the beginning of your next upkeep step unless he or she pays {U} before that step.
        this.getSpellAbility().addEffect(new UnlessPaysDelayedEffect(new ManaCostsImpl("{U}"),
            new DamageTargetEffect(3, true, "that player"), PhaseStep.UPKEEP, false,
            "It deals an additional 3 damage to that player at the beginning of your next upkeep step unless he or she pays {U} before that step."));
    }

    public QuenchableFire(final QuenchableFire card) {
        super(card);
    }

    @Override
    public QuenchableFire copy() {
        return new QuenchableFire(this);
    }
}
