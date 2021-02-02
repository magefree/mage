
package mage.cards.p;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.effects.keyword.SupportEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;

/**
 *
 * @author fireshoes
 */
public final class PressIntoService extends CardImpl {

    public PressIntoService(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{R}");

        // Support 2.
        getSpellAbility().addEffect(new SupportEffect(this, 2, false));

        // Gain control of target creature until end of turn. Untap that creature. It gains haste until end of turn.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        Effect effect = new GainControlTargetEffect(Duration.EndOfTurn);
        effect.setTargetPointer(new SecondTargetPointer()); // First target is used by Support
        this.getSpellAbility().addEffect(effect);
        effect = new UntapTargetEffect();
        effect.setText("Untap that creature");
        effect.setTargetPointer(new SecondTargetPointer()); // First target is used by Support
        this.getSpellAbility().addEffect(effect);
        effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn);
        effect.setTargetPointer(new SecondTargetPointer()); // First target is used by Suppor
        effect.setText("It gains haste until end of turn");
        this.getSpellAbility().addEffect(effect);
    }

    private PressIntoService(final PressIntoService card) {
        super(card);
    }

    @Override
    public PressIntoService copy() {
        return new PressIntoService(this);
    }
}
