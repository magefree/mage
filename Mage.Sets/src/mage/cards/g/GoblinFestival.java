
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author L_J
 */
public final class GoblinFestival extends CardImpl {

    public GoblinFestival(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{R}");

        // {2}: Goblin Festival deals 1 damage to any target. Flip a coin. If you lose the flip, choose one of your opponents. That player gains control of Goblin Festival.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new ManaCostsImpl<>("{2}"));
        ability.addTarget(new TargetAnyTarget());
        ability.addEffect(new GoblinFestivalChangeControlEffect());
        this.addAbility(ability);
    }

    private GoblinFestival(final GoblinFestival card) {
        super(card);
    }

    @Override
    public GoblinFestival copy() {
        return new GoblinFestival(this);
    }
}

class GoblinFestivalChangeControlEffect extends OneShotEffect {

    public GoblinFestivalChangeControlEffect() {
        super(Outcome.Benefit);
        this.staticText = "Flip a coin. If you lose the flip, choose one of your opponents. That player gains control of {this}";
    }

    public GoblinFestivalChangeControlEffect(final GoblinFestivalChangeControlEffect effect) {
        super(effect);
    }

    @Override
    public GoblinFestivalChangeControlEffect copy() {
        return new GoblinFestivalChangeControlEffect(this);
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
                        ContinuousEffect effect = new GoblinFestivalGainControlEffect(Duration.Custom, chosenOpponent.getId());
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

class GoblinFestivalGainControlEffect extends ContinuousEffectImpl {

    UUID controller;

    public GoblinFestivalGainControlEffect(Duration duration, UUID controller) {
        super(duration, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        this.controller = controller;
    }

    public GoblinFestivalGainControlEffect(final GoblinFestivalGainControlEffect effect) {
        super(effect);
        this.controller = effect.controller;
    }

    @Override
    public GoblinFestivalGainControlEffect copy() {
        return new GoblinFestivalGainControlEffect(this);
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
