package mage.game.command.emblems;

import mage.abilities.Ability;
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

import java.util.List;

/**
 * @author spjspj
 */
public final class JaceUnravelerOfSecretsEmblem extends Emblem {

    /**
     * Emblem: "Whenever an opponent casts their first spell each turn,
     * counter that spell."
     */
    public JaceUnravelerOfSecretsEmblem() {
        super("Emblem Jace");
        Effect effect = new CounterTargetEffect();
        effect.setText("counter that spell");
        Ability ability = new JaceUnravelerOfSecretsTriggeredAbility(effect, false);
        ability.addWatcher(new SpellsCastWatcher());
        this.getAbilities().add(ability);
    }

    private JaceUnravelerOfSecretsEmblem(final JaceUnravelerOfSecretsEmblem card) {
        super(card);
    }

    @Override
    public JaceUnravelerOfSecretsEmblem copy() {
        return new JaceUnravelerOfSecretsEmblem(this);
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
            SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
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
