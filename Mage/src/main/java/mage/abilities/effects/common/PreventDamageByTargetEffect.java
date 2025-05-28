package mage.abilities.effects.common;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.hint.Hint;
import mage.abilities.hint.StaticHint;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.TargetSpell;

/**
 * @author nantuko
 */
public class PreventDamageByTargetEffect extends PreventionEffectImpl {

    private boolean passiveVoice = true;
    private boolean durationRuleAtEnd = true;

    public PreventDamageByTargetEffect(Duration duration, boolean onlyCombat) {
        this(duration, Integer.MAX_VALUE, onlyCombat);
    }

    public PreventDamageByTargetEffect(Duration duration, int amount, boolean onlyCombat) {
        super(duration, amount, onlyCombat);
    }

    protected PreventDamageByTargetEffect(final PreventDamageByTargetEffect effect) {
        super(effect);
        this.passiveVoice = effect.passiveVoice;
        this.durationRuleAtEnd = effect.durationRuleAtEnd;
    }

    @Override
    public PreventDamageByTargetEffect copy() {
        return new PreventDamageByTargetEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!this.used && super.applies(event, source, game)) {
            MageObject mageObject = game.getObject(event.getSourceId());
            if (mageObject != null
                    && mageObject.isInstantOrSorcery(game)) {
                for (Target target : source.getTargets()) {
                    if (target instanceof TargetSpell) {
                        if (((TargetSpell) target).getSourceIds().contains(event.getSourceId())) {
                            return true;
                        }
                    }
                }
            }
            return this.getTargetPointer().getTargets(game, source).contains(event.getSourceId());
        }
        return false;
    }

    public PreventDamageByTargetEffect withTextOptions(boolean passiveVoice, boolean durationRuleAtEnd) {
        this.passiveVoice = passiveVoice;
        this.durationRuleAtEnd = durationRuleAtEnd;
        return this;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }

        return generateText(getTargetPointer().describeTargets(mode.getTargets(), "it"));
    }

    private String generateText(String targetText) {
        String durationText = duration == Duration.EndOfTurn ? " this turn" : ' ' + duration.toString();
        String preventText = (amountToPrevent == Integer.MAX_VALUE ? "Prevent all" : "Prevent the next" + amountToPrevent)
                + (onlyCombat ? " combat damage " : " damage ");
        if (passiveVoice) {
            preventText += "that would be dealt" + (durationRuleAtEnd ?
                    " by " + targetText + durationText :
                    durationText + " by " + targetText);
        } else {
            preventText += targetText + " would deal" + durationText;
        }
        return preventText;
    }

    @Override
    public boolean hasHint() {
        return true;
    }

    @Override
    public List<Hint> getAffectedHints(Permanent permanent, Ability source, Game game) {
        if (!this.getTargetPointer().getTargets(game, source).contains(permanent.getId())) {
            return Collections.emptyList();
        }

        return Arrays.asList(new StaticHint(generateText("{this}")));
    }
}
