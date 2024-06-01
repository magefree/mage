
package mage.cards.w;

import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.XTargetsCountAdjuster;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class WaveOfIndifference extends CardImpl {

    public WaveOfIndifference(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}");

        // X target creatures can't block this turn.
        Effect effect = new CantBlockTargetEffect(Duration.EndOfTurn);
        effect.setText("X target creatures can't block this turn");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().setTargetAdjuster(new XTargetsCountAdjuster());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private WaveOfIndifference(final WaveOfIndifference card) {
        super(card);
    }

    @Override
    public WaveOfIndifference copy() {
        return new WaveOfIndifference(this);
    }
}
