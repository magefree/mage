package mage.cards.l;

import mage.abilities.Mode;
import mage.abilities.common.delayed.AtTheBeginOfNextUpkeepDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author Quercitron
 */
public final class LibraryOfLatNam extends CardImpl {

    public LibraryOfLatNam(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}");

        // An opponent chooses one
        this.getSpellAbility().getModes().setModeChooser(TargetController.OPPONENT);

        // You draw three cards at the beginning of the next turn's upkeep;
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new AtTheBeginOfNextUpkeepDelayedTriggeredAbility(new DrawCardSourceControllerEffect(3)), false));

        // or you search your library for a card, put that card into your hand, then shuffle your library.
        this.getSpellAbility().addMode(new Mode(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(), false, true)));
    }

    private LibraryOfLatNam(final LibraryOfLatNam card) {
        super(card);
    }

    @Override
    public LibraryOfLatNam copy() {
        return new LibraryOfLatNam(this);
    }
}
