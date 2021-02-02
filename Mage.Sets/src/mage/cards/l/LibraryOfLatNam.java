
package mage.cards.l;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.common.delayed.AtTheBeginOfNextUpkeepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author Quercitron
 */
public final class LibraryOfLatNam extends CardImpl {

    public LibraryOfLatNam(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{U}");

        // An opponent chooses one
        this.getSpellAbility().getModes().setModeChooser(TargetController.OPPONENT);
        // You draw three cards at the beginning of the next turn's upkeep;
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new AtTheBeginOfNextUpkeepDelayedTriggeredAbility(new DrawCardSourceControllerEffect(3)), false));
        // or you search your library for a card, put that card into your hand, then shuffle your library.
        Mode mode = new Mode();
        Effect effect = new SearchLibraryPutInHandEffect(new TargetCardInLibrary(), false, true);
        //effect.setText("you search your library for a card, put that card into your hand, then shuffle your library");
        mode.addEffect(effect);
        this.getSpellAbility().addMode(mode);
    }

    private LibraryOfLatNam(final LibraryOfLatNam card) {
        super(card);
    }

    @Override
    public LibraryOfLatNam copy() {
        return new LibraryOfLatNam(this);
    }
}
