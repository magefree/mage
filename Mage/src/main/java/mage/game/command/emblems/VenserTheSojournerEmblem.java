
package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.Spell;
import mage.target.Target;
import mage.target.TargetPermanent;

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

        this.setExpansionSetCodeForImage("DDI");
    }
}

class VenserTheSojournerSpellCastTriggeredAbility extends TriggeredAbilityImpl {


    /**
     * If true, the source that triggered the ability will be set as target to
     * effect.
     */

    public VenserTheSojournerSpellCastTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.COMMAND, effect, optional);
    }

    public VenserTheSojournerSpellCastTriggeredAbility(final VenserTheSojournerSpellCastTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(this.getControllerId())) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            return StaticFilters.FILTER_SPELL_A.match(spell, game);
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
