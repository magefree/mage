
package mage.cards.l;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
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
 * @author LoneFox
 *
 */
public final class Liberate extends CardImpl {

    public Liberate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Exile target creature you control. Return that card to the battlefield under its owner's control at the beginning of the next end step.
        this.getSpellAbility().addEffect(new LiberateEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
    }

    public Liberate(final Liberate card) {
        super(card);
    }

    @Override
    public Liberate copy() {
        return new Liberate(this);
    }
}

class LiberateEffect extends OneShotEffect {

    public LiberateEffect() {
        super(Outcome.Detriment);
        staticText = "exile target creature you control. Return that card to the battlefield under its owner's control at the beginning of the next end step";
    }

    public LiberateEffect(final LiberateEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (permanent != null && sourceObject != null) {
            if (permanent.moveToExile(source.getSourceId(), sourceObject.getIdName(), source.getSourceId(), game)) {
                Effect effect = new ReturnToBattlefieldUnderOwnerControlTargetEffect();
                effect.setText("Return that card to the battlefield under its owner's control at the beginning of the next end step");
                effect.setTargetPointer(new FixedTarget(source.getFirstTarget(), game));
                game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect), source);
                return true;
            }
        }
        return false;
    }

    @Override
    public LiberateEffect copy() {
        return new LiberateEffect(this);
    }
}
