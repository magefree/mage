package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * Created by Eric on 9/24/2016.
 */
public class MistmeadowWitchEffect extends OneShotEffect {

    public MistmeadowWitchEffect() {
        super(Outcome.Detriment);
        staticText = "Exile target creature. Return that card to the battlefield under its owner's control at the beginning of the next end step";
    }

    public MistmeadowWitchEffect(final MistmeadowWitchEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            if (permanent.moveToExile(source.getSourceId(), "Mistmeadow Witch Exile", source.getSourceId(), game)) {
                //create delayed triggered ability
                AtTheBeginOfNextEndStepDelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new ReturnFromExileEffect(source.getSourceId(), Zone.BATTLEFIELD));
                game.addDelayedTriggeredAbility(delayedAbility, source);
                return true;
            }
        }
        return false;
    }

    @Override
    public MistmeadowWitchEffect copy() {
        return new MistmeadowWitchEffect(this);
    }
}
