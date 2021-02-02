
package mage.cards.e;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.MustBeBlockedByAtLeastOneTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class EmergentGrowth extends CardImpl {

    public EmergentGrowth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        // Target creature gets +5/+5 until end of turn and must be blocked this turn if able.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new BoostTargetEffect(5, 5, Duration.EndOfTurn));
        Effect effect = new MustBeBlockedByAtLeastOneTargetEffect(Duration.EndOfTurn);
        effect.setText("and must be blocked this turn if able");
        this.getSpellAbility().addEffect(effect);
    }

    private EmergentGrowth(final EmergentGrowth card) {
        super(card);
    }

    @Override
    public EmergentGrowth copy() {
        return new EmergentGrowth(this);
    }
}
