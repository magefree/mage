
package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class SpellCastOpponentTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterSpell spellCard = new FilterSpell("a spell");
    protected FilterSpell filter;
    protected SetTargetPointer setTargetPointer;

    public SpellCastOpponentTriggeredAbility(Effect effect, boolean optional) {
        this(effect, spellCard, optional);
    }

    public SpellCastOpponentTriggeredAbility(Effect effect, FilterSpell filter, boolean optional) {
        this(Zone.BATTLEFIELD, effect, filter, optional);
    }

    public SpellCastOpponentTriggeredAbility(Zone zone, Effect effect, FilterSpell filter, boolean optional) {
        this(zone, effect, filter, optional, SetTargetPointer.NONE);
    }

    /**
     * @param zone
     * @param effect
     * @param filter
     * @param optional
     * @param setTargetPointer Supported: SPELL, PLAYER
     */
    public SpellCastOpponentTriggeredAbility(Zone zone, Effect effect, FilterSpell filter, boolean optional, SetTargetPointer setTargetPointer) {
        super(zone, effect, optional);
        this.filter = filter;
        this.setTargetPointer = setTargetPointer;
    }

    public SpellCastOpponentTriggeredAbility(final SpellCastOpponentTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getPlayer(this.getControllerId()).hasOpponent(event.getPlayerId(), game)) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null && filter.match(spell, game)) {
                if (setTargetPointer != SetTargetPointer.NONE) {
                    for (Effect effect : this.getEffects()) {
                        switch (setTargetPointer) {
                            case SPELL:
                                effect.setTargetPointer(new FixedTarget(event.getTargetId()));
                                break;
                            case PLAYER:
                                effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                                break;
                            default:
                                throw new UnsupportedOperationException("Value of SetTargetPointer not supported!");
                        }

                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent casts " + filter.getMessage() + ", " + super.getRule();
    }

    @Override
    public SpellCastOpponentTriggeredAbility copy() {
        return new SpellCastOpponentTriggeredAbility(this);
    }
}
