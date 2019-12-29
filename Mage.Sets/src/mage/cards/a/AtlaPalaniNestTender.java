package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.token.AtlaPalaniToken;
import mage.players.Library;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AtlaPalaniNestTender extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent(SubType.EGG, "an Egg you control");

    public AtlaPalaniNestTender(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{G}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {2}, {T}: Create a 0/1 green Egg creature token with defender.
        Ability ability = new SimpleActivatedAbility(
                new CreateTokenEffect(new AtlaPalaniToken()), new GenericManaCost(2)
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // Whenever an Egg you control dies, reveal cards from the top of your library until you reveal a creature card. Put that card onto the battlefield and the rest on the bottom of your library in a random order.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new AtlaPalaniNestTenderEffect(), false, filter, false
        ));
    }

    private AtlaPalaniNestTender(final AtlaPalaniNestTender card) {
        super(card);
    }

    @Override
    public AtlaPalaniNestTender copy() {
        return new AtlaPalaniNestTender(this);
    }
}

class AtlaPalaniNestTenderEffect extends OneShotEffect {

    AtlaPalaniNestTenderEffect() {
        super(Outcome.Benefit);
        staticText = "reveal cards from the top of your library until you reveal a creature card. " +
                "Put that card onto the battlefield and the rest on the bottom of your library in a random order";
    }

    private AtlaPalaniNestTenderEffect(final AtlaPalaniNestTenderEffect effect) {
        super(effect);
    }

    @Override
    public AtlaPalaniNestTenderEffect copy() {
        return new AtlaPalaniNestTenderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Library library = player.getLibrary();
        if (!library.hasCards()) {
            return true;
        }
        Cards cards = new CardsImpl();
        Card toBattlefield = null;
        for (Card card : library.getCards(game)) {
            cards.add(card);
            if (card.isCreature()) {
                toBattlefield = card;
                break;
            }
        }
        if (toBattlefield != null) {
            player.moveCards(toBattlefield, Zone.BATTLEFIELD, source, game);
        }
        player.revealCards(source, cards, game);
        cards.remove(toBattlefield);
        if (!cards.isEmpty()) {
            player.putCardsOnBottomOfLibrary(cards, game, source, false);
        }
        return true;
    }
}