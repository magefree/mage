
package mage.cards.p;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class PressForAnswers extends CardImpl {

    public PressForAnswers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{U}");

        // Tap target creature. It doesn't untap during its controller's next untap step.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new TapTargetEffect());
        this.getSpellAbility().addEffect(new DontUntapInControllersNextUntapStepTargetEffect("It"));

        // Investigate. (Create a colorless Clue artifact token with "2, Sacrifice this artifact: Draw a card.")
        Effect effect = new InvestigateEffect();
        effect.setText("<br>Investigate. (Create a colorless Clue artifact token with \"2, Sacrifice this artifact: Draw a card.\")");
        this.getSpellAbility().addEffect(effect);

    }

    private PressForAnswers(final PressForAnswers card) {
        super(card);
    }

    @Override
    public PressForAnswers copy() {
        return new PressForAnswers(this);
    }
}
