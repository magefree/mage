
package mage.cards.e;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderYourControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Styxo
 */
public final class EscapePod extends CardImpl {

    public EscapePod(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Exile target creature you control. Return that card to the battlefield under your control at the beginning of the next end step.
        this.getSpellAbility().addEffect(new EscapePodEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());

    }

    public EscapePod(final EscapePod card) {
        super(card);
    }

    @Override
    public EscapePod copy() {
        return new EscapePod(this);
    }
}

class EscapePodEffect extends OneShotEffect {

    public EscapePodEffect() {
        super(Outcome.Detriment);
        staticText = "Exile target creature you control. Return that card to the battlefield under your control at the beginning of the next end step";
    }

    public EscapePodEffect(final EscapePodEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (permanent != null && sourceObject != null) {
            if (permanent.moveToExile(source.getSourceId(), sourceObject.getIdName(), source.getSourceId(), game)) {
                Effect effect = new ReturnToBattlefieldUnderYourControlTargetEffect();
                effect.setText("Return that card to the battlefield under your control at the beginning of the next end step");
                effect.setTargetPointer(new FixedTarget(source.getFirstTarget(), game));
                game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect), source);
                return true;
            }
        }
        return false;
    }

    @Override
    public EscapePodEffect copy() {
        return new EscapePodEffect(this);
    }

}
