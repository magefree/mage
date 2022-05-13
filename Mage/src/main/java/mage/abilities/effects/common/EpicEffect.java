package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfYourNextUpkeepDelayedTriggeredAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;

/**
 * @author jeffwadsworth
 * <p>
 * 702.49. Epic 702.49a Epic represents two spell abilities, one of which
 * creates a delayed triggered ability. “Epic” means “For the rest of the game,
 * you can't cast spells,” and “At the beginning of each of your upkeeps for the
 * rest of the game, copy this spell except for its epic ability. If the spell
 * has any targets, you may choose new targets for the copy.” See rule 706.10.
 * 702.49b A player can't cast spells once a spell with epic they control
 * resolves, but effects (such as the epic ability itself) can still put copies
 * of spells onto the stack. *
 */
public class EpicEffect extends OneShotEffect {

    private static final String rule = "Epic <i>(For the rest of the game, you can't cast spells. " +
            "At the beginning of each of your upkeeps for the rest of the game, copy this spell " +
            "except for its epic ability. If the spell has targets, you may choose new targets for the copy)</i>";

    public EpicEffect() {
        super(Outcome.Benefit);
        staticText = "<br>" + rule;
    }

    private EpicEffect(final EpicEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Spell spell = (Spell) source.getSourceObject(game);
        if (spell == null) {
            return false;
        }
        spell = spell.copy(); // it's a fake spell (template), real copy with events in EpicPushEffect
        spell.getSpellAbility().getEffects().removeIf(EpicEffect.class::isInstance);
        game.addDelayedTriggeredAbility(new AtTheBeginOfYourNextUpkeepDelayedTriggeredAbility(
                new EpicPushEffect(spell, rule), Duration.EndOfGame, false
        ), source);
        game.addEffect(new EpicReplacementEffect(), source);
        return true;
    }

    @Override
    public EpicEffect copy() {
        return new EpicEffect(this);
    }
}

class EpicReplacementEffect extends ContinuousRuleModifyingEffectImpl {

    EpicReplacementEffect() {
        super(Duration.EndOfGame, Outcome.Neutral);
        staticText = "For the rest of the game, you can't cast spells";
    }

    private EpicReplacementEffect(final EpicReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public EpicReplacementEffect copy() {
        return new EpicReplacementEffect(this);
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source);
        if (mageObject != null) {
            return "For the rest of the game, you can't cast spells (Epic - " + mageObject.getName() + ')';
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.isControlledBy(event.getPlayerId())
                && game.getObject(event.getSourceId()) != null;
    }
}

class EpicPushEffect extends OneShotEffect {

    private final Spell spell;

    EpicPushEffect(Spell spell, String ruleText) {
        super(Outcome.Copy);
        this.spell = spell;
        staticText = ruleText;
    }

    private EpicPushEffect(final EpicPushEffect effect) {
        super(effect);
        this.spell = effect.spell;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (spell != null) {
            spell.createCopyOnStack(game, source, source.getControllerId(), true);
            return true;
        }
        return false;
    }

    @Override
    public EpicPushEffect copy() {
        return new EpicPushEffect(this);
    }
}
