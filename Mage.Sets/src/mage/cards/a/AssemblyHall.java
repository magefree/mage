package mage.cards.a;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInLibrary;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AssemblyHall extends CardImpl {

    public AssemblyHall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        // {4}, {tap}: Reveal a creature card in your hand. Search your library for a card with the same name as that card, reveal it, and put it into your hand. Then shuffle your library.
        Ability ability = new SimpleActivatedAbility(new AssemblyHallEffect(), new GenericManaCost(4));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private AssemblyHall(final AssemblyHall card) {
        super(card);
    }

    @Override
    public AssemblyHall copy() {
        return new AssemblyHall(this);
    }
}

class AssemblyHallEffect extends OneShotEffect {

    public AssemblyHallEffect() {
        super(Outcome.Benefit);
        this.staticText = "reveal a creature card from your hand. "
                + "Search your library for a card with the same name as that card, "
                + "reveal it, and put it into your hand. Then shuffle";
    }

    public AssemblyHallEffect(final AssemblyHallEffect effect) {
        super(effect);
    }

    @Override
    public AssemblyHallEffect copy() {
        return new AssemblyHallEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller == null || controller.getHand().isEmpty() || sourceObject == null) {
            return false;
        }
        Card cardToReveal = null;
        Target target = new TargetCardInHand(StaticFilters.FILTER_CARD_CREATURE);
        target.setNotTarget(true);
        if (controller.chooseTarget(outcome, target, source, game)) {
            cardToReveal = game.getCard(target.getFirstTarget());
        }
        if (cardToReveal == null) {
            return false;
        }
        controller.revealCards("from hand :" + sourceObject.getName(), new CardsImpl(cardToReveal), game);
        String nameToSearch = CardUtil.getCardNameForSameNameSearch(cardToReveal);
        FilterCard filterCard = new FilterCard("card named " + nameToSearch);
        filterCard.add(new NamePredicate(nameToSearch));
        return new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filterCard), true).apply(game, source);
    }
}
