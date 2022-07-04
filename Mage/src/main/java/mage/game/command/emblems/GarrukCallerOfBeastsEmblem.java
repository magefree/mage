
package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.game.command.Emblem;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author spjspj
 */
public final class GarrukCallerOfBeastsEmblem extends Emblem {

    /**
     * Emblem: "Whenever you cast a creature spell, you may search your library
     * for a creature card, put it onto the battlefield, then shuffle your
     * library."
     */
    public GarrukCallerOfBeastsEmblem() {
        this.setName("Emblem Garruk");
        Effect effect = new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(new FilterCreatureCard("creature card")), false, true, Outcome.PutCreatureInPlay);
        Ability ability = new SpellCastControllerTriggeredAbility(Zone.COMMAND, effect, StaticFilters.FILTER_SPELL_A_CREATURE, true, false);
        this.getAbilities().add(ability);

        this.setExpansionSetCodeForImage("M14");
    }
}
