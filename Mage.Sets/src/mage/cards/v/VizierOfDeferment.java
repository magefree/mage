
package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.AttackedThisTurnWatcher;
import mage.watchers.common.BlockedThisTurnWatcher;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class VizierOfDeferment extends CardImpl {

    public VizierOfDeferment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Vizier of Deferment enters the battlefield, you may exile target creature if it attacked or blocked this turn. Return that card to the battlefield under its owner's control at the beginning of the next end step.
        Ability ability = new EntersBattlefieldTriggeredAbility(new VizierOfDefermentEffect(), true);
        ability.addTarget(new TargetCreaturePermanent());
        ability.addWatcher(new AttackedThisTurnWatcher());
        ability.addWatcher(new BlockedThisTurnWatcher());
        this.addAbility(ability);
    }

    public VizierOfDeferment(final VizierOfDeferment card) {
        super(card);
    }

    @Override
    public VizierOfDeferment copy() {
        return new VizierOfDeferment(this);
    }
}

class VizierOfDefermentEffect extends OneShotEffect {

    public VizierOfDefermentEffect() {
        super(Outcome.Detriment);
        staticText = "you may exile target creature if it attacked or blocked this turn. " +
                "Return that card to the battlefield under its owner's control at the beginning of the next end step";
    }

    public VizierOfDefermentEffect(final VizierOfDefermentEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        AttackedThisTurnWatcher watcherAttacked = game.getState().getWatcher(AttackedThisTurnWatcher.class);
        BlockedThisTurnWatcher watcherBlocked = game.getState().getWatcher(BlockedThisTurnWatcher.class);
        boolean attackedOrBlocked = false;
        if (watcherAttacked != null && watcherAttacked.checkIfAttacked(permanent, game)) {
            attackedOrBlocked = true;
        }
        if (watcherBlocked != null && watcherBlocked.checkIfBlocked(permanent, game)) {
            attackedOrBlocked = true;
        }
        if (controller != null
                && attackedOrBlocked
                && sourcePermanent != null) {
            if (controller.moveCardToExileWithInfo(permanent, source.getSourceId(), sourcePermanent.getIdName(), source.getSourceId(), game, Zone.BATTLEFIELD, true)) {
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
    public VizierOfDefermentEffect copy() {
        return new VizierOfDefermentEffect(this);
    }

}
