package mage.cards.r;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author L_J
 */
public final class RiskyMove extends CardImpl {

    public RiskyMove(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}{R}{R}");

        // At the beginning of each player's upkeep, that player gains control of Risky Move.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new RiskyMoveGetControlEffect(), TargetController.ANY, false, true));

        // When you gain control of Risky Move from another player, choose a creature you control and an opponent. Flip a coin. If you lose the flip, that opponent gains control of that creature.
        this.addAbility(new RiskyMoveTriggeredAbility());
    }

    private RiskyMove(final RiskyMove card) {
        super(card);
    }

    @Override
    public RiskyMove copy() {
        return new RiskyMove(this);
    }
}

class RiskyMoveGetControlEffect extends OneShotEffect {

    RiskyMoveGetControlEffect() {
        super(Outcome.GainControl);
        this.staticText = "that player gains control of {this}";
    }

    private RiskyMoveGetControlEffect(final RiskyMoveGetControlEffect effect) {
        super(effect);
    }

    @Override
    public RiskyMoveGetControlEffect copy() {
        return new RiskyMoveGetControlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        Player newController = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (newController != null
                && controller != null
                && sourceObject != null
                && sourceObject.equals(sourcePermanent)) {
            // remove old control effects of the same player
            for (ContinuousEffect effect : game.getState().getContinuousEffects().getLayeredEffects(game)) {
                if (effect instanceof GainControlTargetEffect) {
                    UUID checkId = (UUID) effect.getValue("RiskyMoveSourceId");
                    UUID controllerId = (UUID) effect.getValue("RiskyMoveControllerId");
                    if (source.getSourceId().equals(checkId)
                            && newController.getId().equals(controllerId)) {
                        effect.discard();
                    }
                }
            }
            ContinuousEffect effect = new GainControlTargetEffect(Duration.EndOfGame, true, newController.getId());
            effect.setValue("RiskyMoveSourceId", source.getSourceId());
            effect.setValue("RiskyMoveControllerId", newController.getId());
            effect.setTargetPointer(new FixedTarget(sourcePermanent.getId(), game));
            effect.setText("and gains control of it");
            game.addEffect(effect, source);
            return true;
        }
        return false;
    }
}

class RiskyMoveTriggeredAbility extends TriggeredAbilityImpl {

    RiskyMoveTriggeredAbility() {
        super(Zone.BATTLEFIELD, new RiskyMoveFlipCoinEffect(), false);
        setTriggerPhrase("When you gain control of {this} from another player, ");
    }

    private RiskyMoveTriggeredAbility(final RiskyMoveTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RiskyMoveTriggeredAbility copy() {
        return new RiskyMoveTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.GAINED_CONTROL;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getTargetId().equals(sourceId);
    }
}

class RiskyMoveFlipCoinEffect extends OneShotEffect {

    RiskyMoveFlipCoinEffect() {
        super(Outcome.Detriment);
        this.staticText = "choose a creature you control and an opponent. Flip a coin. If you lose the flip, that opponent gains control of that creature";
    }

    private RiskyMoveFlipCoinEffect(final RiskyMoveFlipCoinEffect effect) {
        super(effect);
    }

    @Override
    public RiskyMoveFlipCoinEffect copy() {
        return new RiskyMoveFlipCoinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Target target1 = new TargetControlledCreaturePermanent().withNotTarget(true);
            Target target2 = new TargetOpponent(true);

            if (target1.canChoose(controller.getId(), source, game)) {
                while (!target1.isChosen(game)
                        && target1.canChoose(controller.getId(), source, game)
                        && controller.canRespond()) {
                    controller.chooseTarget(outcome, target1, source, game);
                }
            }
            if (target2.canChoose(controller.getId(), source, game)) {
                while (!target2.isChosen(game)
                        && target2.canChoose(controller.getId(), source, game)
                        && controller.canRespond()) {
                    controller.chooseTarget(outcome, target2, source, game);
                }
            }
            Permanent permanent = game.getPermanent(target1.getFirstTarget());
            Player chosenOpponent = game.getPlayer(target2.getFirstTarget());
            if (!controller.flipCoin(source, game, true)) {
                if (permanent != null && chosenOpponent != null) {
                    ContinuousEffect effect = new RiskyMoveCreatureGainControlEffect(Duration.EndOfGame, chosenOpponent.getId());
                    effect.setTargetPointer(new FixedTarget(permanent, game));
                    game.addEffect(effect, source);
                    game.informPlayers(chosenOpponent.getLogName() + " has gained control of " + permanent.getLogName());
                    return true;
                }
            }
        }
        return false;
    }
}

class RiskyMoveCreatureGainControlEffect extends ContinuousEffectImpl {

    private final UUID controller;

    RiskyMoveCreatureGainControlEffect(Duration duration, UUID controller) {
        super(duration, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        this.controller = controller;
        this.staticText = "If you lose the flip, that opponent gains control of that creature";
    }

    private RiskyMoveCreatureGainControlEffect(final RiskyMoveCreatureGainControlEffect effect) {
        super(effect);
        this.controller = effect.controller;
    }

    @Override
    public RiskyMoveCreatureGainControlEffect copy() {
        return new RiskyMoveCreatureGainControlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            permanent = game.getPermanent(source.getFirstTarget());
        }

        if (permanent != null) {
            return permanent.changeControllerId(controller, game, source);
        }
        return false;
    }
}
