
package mage.cards.s;

import java.util.UUID;
import mage.abilities.condition.common.SpellMasteryCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class SendToSleep extends CardImpl {

    public SendToSleep(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");

        // Tap up to two target creatures.
        this.getSpellAbility().addEffect(new TapTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 2));

        // <i>Spell mastery</i> &mdash; If there are two or more instant and/or sorcery cards in your graveyard, those creatures don't untap during their controllers' next untap steps.
        Effect effect = new ConditionalOneShotEffect(
                new AddContinuousEffectToGame(new DontUntapInControllersNextUntapStepTargetEffect(", those creatures")),
                SpellMasteryCondition.instance);
        effect.setText("<br><i>Spell mastery</i> &mdash; If there are two or more instant and/or sorcery cards in your graveyard, those creatures don't untap during their controllers' next untap steps");
        this.getSpellAbility().addEffect(effect);
    }

    public SendToSleep(final SendToSleep card) {
        super(card);
    }

    @Override
    public SendToSleep copy() {
        return new SendToSleep(this);
    }
}
