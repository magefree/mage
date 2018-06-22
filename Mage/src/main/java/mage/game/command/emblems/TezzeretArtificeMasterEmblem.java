package mage.game.command.emblems;

import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.constants.TargetController;
import mage.filter.common.FilterPermanentCard;
import mage.game.command.Emblem;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author spjspj
 */
public class TezzeretArtificeMasterEmblem extends Emblem {

    // −9: You get an emblem with "At the beginning of your end step, search your library for a permanent card, put it into the battlefield, then shuffle your library."
    public TezzeretArtificeMasterEmblem() {
        this.setName("Emblem Tezzeret");
        this.setExpansionSetCodeForImage("M19");
        this.getAbilities().add(new BeginningOfEndStepTriggeredAbility(
                new SearchLibraryPutInPlayEffect(
                        new TargetCardInLibrary(new FilterPermanentCard())
                ), TargetController.YOU, false
        ));
    }
}
