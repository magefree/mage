package mage.game.command.emblems;

import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.command.Emblem;
import mage.target.common.TargetCardInLibrary;

public class GarrukUnleashedEmblem extends Emblem {

    // At the beginning of your end step, you may search your library for a creature card, put it onto the battlefield, then shuffle your library.
    public GarrukUnleashedEmblem() {
        this.setName("Emblem Garruk");
        Effect effect = new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(StaticFilters.FILTER_CARD_CREATURE), false, true, Outcome.PutCreatureInPlay)
            .setText("search your library for a creature card, put it onto the battlefield, then shuffle");
        this.getAbilities().add(new BeginningOfYourEndStepTriggeredAbility(Zone.COMMAND, effect, true));

        this.setExpansionSetCodeForImage("M21");
    }
}
