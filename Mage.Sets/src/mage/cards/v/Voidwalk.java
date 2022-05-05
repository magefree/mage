package mage.cards.v;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CipherEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class Voidwalk extends CardImpl {

    public Voidwalk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}");

        // Exile target creature. Return it to the battlefield under its owner's control at the beginning of the next end step.
        this.getSpellAbility().addEffect(new VoidwalkEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Cipher
        this.getSpellAbility().addEffect(new CipherEffect());
    }

    private Voidwalk(final Voidwalk card) {
        super(card);
    }

    @Override
    public Voidwalk copy() {
        return new Voidwalk(this);
    }
}

class VoidwalkEffect extends OneShotEffect {

    public VoidwalkEffect() {
        super(Outcome.Detriment);
        staticText = "Exile target creature. Return it to the battlefield under its owner's control at the beginning of the next end step";
    }

    public VoidwalkEffect(final VoidwalkEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller != null && permanent != null && sourceObject != null) {
            if (controller.moveCardToExileWithInfo(permanent, source.getSourceId(), sourceObject.getIdName(), source, game, Zone.BATTLEFIELD, true)) {
                //create delayed triggered ability
                Effect effect = new ReturnToBattlefieldUnderOwnerControlTargetEffect(false, false);
                effect.setText("Return that card to the battlefield under its owner's control at the beginning of the next end step");
                effect.setTargetPointer(new FixedTarget(source.getFirstTarget(), game));
                game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect), source);
                return true;
            }
        }
        return false;
    }

    @Override
    public VoidwalkEffect copy() {
        return new VoidwalkEffect(this);
    }
}
