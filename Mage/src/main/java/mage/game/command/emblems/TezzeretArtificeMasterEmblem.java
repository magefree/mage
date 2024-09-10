package mage.game.command.emblems;

import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterPermanentCard;
import mage.game.command.Emblem;
import mage.target.common.TargetCardInLibrary;

/**
 * @author TheElk801
 */
public final class TezzeretArtificeMasterEmblem extends Emblem {

    // −9: You get an emblem with "At the beginning of your end step, search your library for a permanent card, put it into the battlefield, then shuffle your library."
    public TezzeretArtificeMasterEmblem() {
        super("Emblem Tezzeret");
        this.getAbilities().add(new BeginningOfEndStepTriggeredAbility(
                Zone.COMMAND,
                new SearchLibraryPutInPlayEffect(
                        new TargetCardInLibrary(new FilterPermanentCard())
                ), TargetController.YOU, null, false
        ));
    }

    private TezzeretArtificeMasterEmblem(final TezzeretArtificeMasterEmblem card) {
        super(card);
    }

    @Override
    public TezzeretArtificeMasterEmblem copy() {
        return new TezzeretArtificeMasterEmblem(this);
    }
}
