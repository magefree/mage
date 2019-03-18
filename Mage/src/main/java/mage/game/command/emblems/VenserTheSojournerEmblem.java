
package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.Spell;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author spjspj
 */
public final class VenserTheSojournerEmblem extends Emblem {

    /**
     * Emblem: "Whenever you cast a spell, exile target permanent."
     */

    public VenserTheSojournerEmblem() {
        this.setName("Emblem Venser");
        Ability ability = new VenserTheSojournerSpellCastTriggeredAbility(new ExileTargetEffect(), false);
        Target target = new TargetPermanent();
        ability.addTarget(target);
        this.getAbilities().add(ability);
    }
}

class VenserTheSojournerSpellCastTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterSpell spellCard = new FilterSpell("a spell");
    protected FilterSpell filter;

    /**
     * If true, the source that triggered the ability will be set as target to
     * effect.
     */
    protected boolean rememberSource = false;

    public VenserTheSojournerSpellCastTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.COMMAND, effect, optional);
        this.filter = spellCard;
    }

    public VenserTheSojournerSpellCastTriggeredAbility(final VenserTheSojournerSpellCastTriggeredAbility ability) {
        super(ability);
        filter = ability.filter;
        this.rememberSource = ability.rememberSource;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(this.getControllerId())) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null && filter.match(spell, game)) {
                if (rememberSource) {
                    this.getEffects().get(0).setTargetPointer(new FixedTarget(spell.getId()));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you cast a spell, exile target permanent.";
    }

    @Override
    public VenserTheSojournerSpellCastTriggeredAbility copy() {
        return new VenserTheSojournerSpellCastTriggeredAbility(this);
    }
}
