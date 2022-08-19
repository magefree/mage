package mage.abilities.abilityword;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.AbilityWord;
import mage.constants.EffectType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 * @author LevelX2
 */
public class KinshipAbility extends TriggeredAbilityImpl {

    public KinshipAbility(Effect kinshipEffect) {
        super(Zone.BATTLEFIELD, new KinshipBaseEffect(kinshipEffect), true);
        this.setAbilityWord(AbilityWord.KINSHIP);
        setTriggerPhrase("At the beginning of your upkeep, ");
    }

    public KinshipAbility(final KinshipAbility ability) {
        super(ability);
    }

    public void addKinshipEffect(Effect kinshipEffect) {
        for (Effect effect : this.getEffects()) {
            if (effect instanceof KinshipBaseEffect) {
                ((KinshipBaseEffect) effect).addEffect(kinshipEffect);
                break;
            }
        }
    }

    @Override
    public KinshipAbility copy() {
        return new KinshipAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.controllerId);
    }
}

class KinshipBaseEffect extends OneShotEffect {

    private final Effects kinshipEffects = new Effects();

    public KinshipBaseEffect(Effect kinshipEffect) {
        super(kinshipEffect.getOutcome());
        this.kinshipEffects.add(kinshipEffect);
        this.staticText = "you may look at the top card of your library. If it shares a creature type with {this}, you may reveal it. If you do, ";
    }

    public KinshipBaseEffect(final KinshipBaseEffect effect) {
        super(effect);
        this.kinshipEffects.addAll(effect.kinshipEffects.copy());
    }

    public void addEffect(Effect kinshipEffect) {
        this.kinshipEffects.add(kinshipEffect);
    }

    @Override
    public KinshipBaseEffect copy() {
        return new KinshipBaseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null && sourcePermanent != null) {
            if (controller.getLibrary().hasCards()) {
                Card card = controller.getLibrary().getFromTop(game);
                if (card != null) {
                    Cards cards = new CardsImpl(card);
                    controller.lookAtCards(sourcePermanent.getName(), cards, game);
                    if (sourcePermanent.shareCreatureTypes(game, card)) {
                        if (controller.chooseUse(outcome, new StringBuilder("Kinship - Reveal ").append(card.getLogName()).append('?').toString(), source, game)) {
                            controller.revealCards(sourcePermanent.getName(), cards, game);
                            for (Effect effect : kinshipEffects) {
                                effect.setTargetPointer(new FixedTarget(card.getId(), game));
                                if (effect.getEffectType() == EffectType.ONESHOT) {
                                    effect.apply(game, source);
                                } else {
                                    if (effect instanceof ContinuousEffect) {
                                        game.addEffect((ContinuousEffect) effect, source);
                                    } else {
                                        throw new UnsupportedOperationException("This kind of effect is not supported");
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        return new StringBuilder(super.getText(mode)).append(kinshipEffects.getText(mode)).toString();
    }

}
