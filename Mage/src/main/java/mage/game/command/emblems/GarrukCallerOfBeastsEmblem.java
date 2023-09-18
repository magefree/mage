package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.game.command.Emblem;
import mage.target.common.TargetCardInLibrary;

/**
 * @author spjspj
 */
public final class GarrukCallerOfBeastsEmblem extends Emblem {

    /**
     * Emblem: "Whenever you cast a creature spell, you may search your library
     * for a creature card, put it onto the battlefield, then shuffle your
     * library."
     */
    public GarrukCallerOfBeastsEmblem() {
        super("Emblem Garruk");
        Effect effect = new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(new FilterCreatureCard("creature card")), false);
        Ability ability = new SpellCastControllerTriggeredAbility(
                Zone.COMMAND, effect,
                StaticFilters.FILTER_SPELL_A_CREATURE,
                true, SetTargetPointer.NONE
        );
        this.getAbilities().add(ability);
    }

    private GarrukCallerOfBeastsEmblem(final GarrukCallerOfBeastsEmblem card) {
        super(card);
    }

    @Override
    public GarrukCallerOfBeastsEmblem copy() {
        return new GarrukCallerOfBeastsEmblem(this);
    }
}
