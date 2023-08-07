
package mage.cards.p;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author LoneFox
 */
public final class PsychicDrain extends CardImpl {

    public PsychicDrain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{U}{B}");

        // Target player puts the top X cards of their library into their graveyard and you gain X life.
        this.getSpellAbility().addEffect(new MillCardsTargetEffect(ManacostVariableValue.REGULAR));
        this.getSpellAbility().addTarget(new TargetPlayer());
        Effect effect = new GainLifeEffect(ManacostVariableValue.REGULAR);
        effect.setText("and you gain X life");
        this.getSpellAbility().addEffect(effect);
    }

    private PsychicDrain(final PsychicDrain card) {
        super(card);
    }

    @Override
    public PsychicDrain copy() {
        return new PsychicDrain(this);
    }
}
