package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.game.command.Emblem;
import mage.target.common.TargetOpponent;

/**
 * @author spjspj
 */
public final class JaceTelepathUnboundEmblem extends Emblem {
    // You get an emblem with "Whenever you cast a spell, target opponent puts the top five cards of their library into their graveyard".

    public JaceTelepathUnboundEmblem() {
        this.setName("Emblem Jace");
        Effect effect = new MillCardsTargetEffect(5);
        effect.setText("target opponent mills five cards");
        Ability ability = new SpellCastControllerTriggeredAbility(Zone.COMMAND, effect, StaticFilters.FILTER_SPELL_A, false, false);
        ability.addTarget(new TargetOpponent());
        getAbilities().add(ability);

        this.setExpansionSetCodeForImage("ORI");
    }
}
