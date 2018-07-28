package mage.cards.v;

import java.util.UUID;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetNonlandPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author NinthWorld
 */
public final class Vortex extends CardImpl {

    public Vortex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");
        

        // Exile target nonland permanent. Return it to the battlefield under its owner's control at the beginning of the next end step.
        this.getSpellAbility().addEffect(new VortexEffect());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
    }

    public Vortex(final Vortex card) {
        super(card);
    }

    @Override
    public Vortex copy() {
        return new Vortex(this);
    }
}

// From LiberateEffect
class VortexEffect extends OneShotEffect {

    public VortexEffect() {
        super(Outcome.Exile);
        staticText = "exile target nonland permanent. Return it to the battlefield under its owner's control at the beginning of the next end step";
    }

    public VortexEffect(final VortexEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (permanent != null && sourceObject != null) {
            if (permanent.moveToExile(source.getSourceId(), sourceObject.getIdName(), source.getSourceId(), game)) {
                Effect effect = new ReturnToBattlefieldUnderOwnerControlTargetEffect();
                effect.setText("Return exiled nonland permanent to the battlefield under its owner's control");
                effect.setTargetPointer(new FixedTarget(source.getFirstTarget(), game));
                game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect), source);
                return true;
            }
        }
        return false;
    }

    @Override
    public VortexEffect copy() {
        return new VortexEffect(this);
    }
}