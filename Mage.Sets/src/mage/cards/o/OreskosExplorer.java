package mage.cards.o;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterLandCard;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class OreskosExplorer extends CardImpl {

    public OreskosExplorer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Oreskos Explorer enters the battlefield, search your library for up to X Plains cards,
        // where X is the number of players who control more lands than you. Reveal those cards, put them into your hand, then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new OreskosExplorerEffect()));

    }

    private OreskosExplorer(final OreskosExplorer card) {
        super(card);
    }

    @Override
    public OreskosExplorer copy() {
        return new OreskosExplorer(this);
    }
}

class OreskosExplorerEffect extends OneShotEffect {

    public OreskosExplorerEffect() {
        super(Outcome.PutLandInPlay);
        this.staticText = "search your library for up to X Plains cards, where X is the number of players who control more lands than you. Reveal those cards, put them into your hand, then shuffle";
    }

    private OreskosExplorerEffect(final OreskosExplorerEffect effect) {
        super(effect);
    }

    @Override
    public OreskosExplorerEffect copy() {
        return new OreskosExplorerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller == null || sourceObject == null) {
            return false;
        }

        int controllerLands = game.getBattlefield().countAll(new FilterLandPermanent(), controller.getId(), game);
        int landsToSearch = 0;
        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            if (!playerId.equals(controller.getId())) {
                if (controllerLands < game.getBattlefield().countAll(new FilterLandPermanent(), playerId, game)) {
                    landsToSearch++;
                }
            }
        }
        if (landsToSearch > 0) {
            FilterLandCard filterPlains = new FilterLandCard("up to " + landsToSearch + " Plains cards");
            filterPlains.add(SubType.PLAINS.getPredicate());
            TargetCardInLibrary target = new TargetCardInLibrary(0, landsToSearch, filterPlains);
            if (controller.searchLibrary(target, source, game, controller.getId())) {
                Cards cards = new CardsImpl(target.getTargets());
                controller.revealCards(sourceObject.getIdName(), cards, game);
                controller.moveCards(cards.getCards(game), Zone.HAND, source, game);
            }
        }
        controller.shuffleLibrary(source, game);
        return true;
    }
}
