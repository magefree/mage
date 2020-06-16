package mage.game.command.emblems;

import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.common.FilterCreatureCard;
import mage.game.command.Emblem;
import mage.target.common.TargetCardInLibrary;

public class GarrukUnleashedEmblem extends Emblem {

    // At the beginning of your end step, you may search your library for a creature card, put it onto the battlefield, then shuffle your library.
    public GarrukUnleashedEmblem() {
        this.setName("Emblem Garruk");
        Effect effect = new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(new FilterCreatureCard("creature card")), false, true, Outcome.PutCreatureInPlay)
            .setText("you may search your library for a creature card, put it onto the battlefield, then shuffle your library");
        this.getAbilities().add(new BeginningOfEndStepTriggeredAbility(effect, TargetController.YOU, true));
    }
}
