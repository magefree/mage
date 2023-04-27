package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.SearchEffect;
import mage.abilities.keyword.UnearthAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author Plopman
 */
public final class CorpseConnoisseur extends CardImpl {

    public CorpseConnoisseur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Corpse Connoisseur enters the battlefield, you may search your library for a creature card and put that card into your graveyard. If you do, shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInGraveyard(), true));
        // Unearth {3}{B}
        this.addAbility(new UnearthAbility(new ManaCostsImpl<>("{3}{B}")));
    }

    private CorpseConnoisseur(final CorpseConnoisseur card) {
        super(card);
    }

    @Override
    public CorpseConnoisseur copy() {
        return new CorpseConnoisseur(this);
    }
}

class SearchLibraryPutInGraveyard extends SearchEffect {

    public SearchLibraryPutInGraveyard() {
        super(new TargetCardInLibrary(StaticFilters.FILTER_CARD_CREATURE), Outcome.Neutral);
        staticText = "search your library for a creature card, put that card into your graveyard, then shuffle";
    }

    public SearchLibraryPutInGraveyard(final SearchLibraryPutInGraveyard effect) {
        super(effect);
    }

    @Override
    public SearchLibraryPutInGraveyard copy() {
        return new SearchLibraryPutInGraveyard(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (controller.searchLibrary(target, source, game)) {
                if (!target.getTargets().isEmpty()) {
                    Card card = controller.getLibrary().getCard(target.getFirstTarget(), game);
                    if (card != null) {
                        controller.moveCards(card, Zone.GRAVEYARD, source, game);
                    }
                }
            }
            controller.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }

}
