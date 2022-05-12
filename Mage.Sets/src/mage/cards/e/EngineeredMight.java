
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class EngineeredMight extends CardImpl {

    public EngineeredMight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{G}{W}");

        // Choose one -
        // • Target creature gets +5/+5 and gains trample until end of turn.
        Effect effect = new BoostTargetEffect(5, 5, Duration.EndOfTurn);
        effect.setText("Target creature gets +5/+5");
        this.getSpellAbility().addEffect(effect);
        effect = new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains trample until end of turn");
        this.getSpellAbility().addEffect(effect);
        Target target = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(target);
        // • Creatures you control get +2/+2 and gain vigilance until end of turn.
        effect = new BoostControlledEffect(2, 2, Duration.EndOfTurn);
        effect.setText("Creatures you control get +2/+2");
        Mode mode = new Mode(effect);
        effect = new GainAbilityControlledEffect(VigilanceAbility.getInstance(), Duration.EndOfTurn, new FilterCreaturePermanent());
        effect.setText("and gain vigilance until end of turn");
        mode.addEffect(effect);
        this.getSpellAbility().addMode(mode);
    }

    private EngineeredMight(final EngineeredMight card) {
        super(card);
    }

    @Override
    public EngineeredMight copy() {
        return new EngineeredMight(this);
    }
}
