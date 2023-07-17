
package mage.abilities.keyword;

import mage.abilities.Mode;
import mage.abilities.SpellAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.AbilityWord;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.Target;

import java.util.UUID;

/**
 * Heroic
 *
 * @author LevelX2
 */
public class HeroicAbility extends TriggeredAbilityImpl {

    public HeroicAbility(Effect effect) {
        this(effect, false);
    }

    public HeroicAbility(Effect effect, boolean optional) {
        this(effect, optional, true);
    }

    public HeroicAbility(Effect effect, boolean optional, boolean isHeroic) {
        super(Zone.BATTLEFIELD, effect, optional);
        if (isHeroic) {
            this.setAbilityWord(AbilityWord.HEROIC);
        }
        this.replaceRuleText = false;
        setTriggerPhrase("Whenever you cast a spell that targets {this}, ");
    }

    public HeroicAbility(final HeroicAbility ability) {
        super(ability);
    }

    @Override
    public HeroicAbility copy() {
        return new HeroicAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(this.getControllerId())) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (checkSpell(spell, game)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkSpell(Spell spell, Game game) {
        if (spell == null) {
            return false;
        }
        SpellAbility sa = spell.getSpellAbility();
        for (UUID modeId : sa.getModes().getSelectedModes()) {
            Mode mode = sa.getModes().get(modeId);
            for (Target target : mode.getTargets()) {
                if (!target.isNotTarget() && target.getTargets().contains(this.getSourceId())) {
                    return true;
                }
            }
            for (Effect effect : mode.getEffects()) {
                for (UUID targetId : effect.getTargetPointer().getTargets(game, sa)) {
                    if (targetId.equals(this.getSourceId())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
