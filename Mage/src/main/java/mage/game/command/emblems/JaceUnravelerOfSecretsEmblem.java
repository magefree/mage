
package mage.game.command.emblems;

import java.util.List;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.SpellsCastWatcher;

/**
 *
 * @author spjspj
 */
public final class JaceUnravelerOfSecretsEmblem extends Emblem {

    /**
     * Emblem: "Whenever an opponent casts their first spell each turn,
     * counter that spell."
     */
    public JaceUnravelerOfSecretsEmblem() {
        this.setName("Emblem Jace");
        setExpansionSetCodeForImage("SOI");
        Effect effect = new CounterTargetEffect();
        effect.setText("counter that spell");
        this.getAbilities().add(new JaceUnravelerOfSecretsTriggeredAbility(effect, false));
    }
}

class JaceUnravelerOfSecretsTriggeredAbility extends SpellCastOpponentTriggeredAbility {

    public JaceUnravelerOfSecretsTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.COMMAND, effect, new FilterSpell(), optional);
    }

    public JaceUnravelerOfSecretsTriggeredAbility(SpellCastOpponentTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SpellCastOpponentTriggeredAbility copy() {
        return new JaceUnravelerOfSecretsTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game)) {
            SpellsCastWatcher watcher = (SpellsCastWatcher) game.getState().getWatchers().get(SpellsCastWatcher.class.getSimpleName());
            if (watcher != null) {
                List<Spell> spells = watcher.getSpellsCastThisTurn(event.getPlayerId());
                if (spells != null && spells.size() == 1) {
                    for (Effect effect : getEffects()) {
                        effect.setTargetPointer(new FixedTarget(event.getTargetId()));
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent casts their first spell each turn, counter that spell.";
    }
}
