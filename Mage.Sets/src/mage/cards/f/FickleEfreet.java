package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author L_J
 */
public final class FickleEfreet extends CardImpl {

    public FickleEfreet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.subtype.add(SubType.EFREET);

        this.power = new MageInt(5);
        this.toughness = new MageInt(2);

        // Whenever Fickle Efreet attacks or blocks, flip a coin at end of combat. If you lose the flip, an opponent gains control of Fickle Efreet.
        this.addAbility(new AttacksOrBlocksTriggeredAbility(new CreateDelayedTriggeredAbilityEffect(
                new AtTheEndOfCombatDelayedTriggeredAbility(new FickleEfreetChangeControlEffect()), true), false));
    }

    private FickleEfreet(final FickleEfreet card) {
        super(card);
    }

    @Override
    public FickleEfreet copy() {
        return new FickleEfreet(this);
    }
}

class FickleEfreetChangeControlEffect extends OneShotEffect {

    public FickleEfreetChangeControlEffect() {
        super(Outcome.Benefit);
        this.staticText = "flip a coin at end of combat. If you lose the flip, choose one of your opponents. That player gains control of {this}";
    }

    public FickleEfreetChangeControlEffect(final FickleEfreetChangeControlEffect effect) {
        super(effect);
    }

    @Override
    public FickleEfreetChangeControlEffect copy() {
        return new FickleEfreetChangeControlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (controller != null) {
            if (!controller.flipCoin(source, game, true)) {
                if (sourcePermanent != null) {
                    Target target = new TargetOpponent(true);
                    if (target.canChoose(controller.getId(), source, game)) {
                        while (!target.isChosen() && target.canChoose(controller.getId(), source, game) && controller.canRespond()) {
                            controller.chooseTarget(outcome, target, source, game);
                        }
                    }
                    Player chosenOpponent = game.getPlayer(target.getFirstTarget());
                    if (chosenOpponent != null) {
                        ContinuousEffect effect = new FickleEfreetGainControlEffect(Duration.Custom, target.getFirstTarget());
                        effect.setTargetPointer(new FixedTarget(sourcePermanent.getId(), game));
                        game.addEffect(effect, source);
                        game.informPlayers(chosenOpponent.getLogName() + " has gained control of " + sourcePermanent.getLogName());
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

class FickleEfreetGainControlEffect extends ContinuousEffectImpl {

    UUID controller;

    public FickleEfreetGainControlEffect(Duration duration, UUID controller) {
        super(duration, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        this.controller = controller;
    }

    public FickleEfreetGainControlEffect(final FickleEfreetGainControlEffect effect) {
        super(effect);
        this.controller = effect.controller;
    }

    @Override
    public FickleEfreetGainControlEffect copy() {
        return new FickleEfreetGainControlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (targetPointer != null) {
            permanent = game.getPermanent(targetPointer.getFirst(game, source));
        }
        if (permanent != null) {
            return permanent.changeControllerId(controller, game, source);
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        return "That player gains control of {this}";
    }
}
