package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BisonWhistle extends CardImpl {

    public BisonWhistle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{G}");

        // {1}, {T}: Look at the top card of your library. If it's a Bison card, you may put it onto the battlefield. If it's a creature card, you may reveal it and put it into your hand. Otherwise, you may put it into your graveyard.
        Ability ability = new SimpleActivatedAbility(new BisonWhistleEffect(), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private BisonWhistle(final BisonWhistle card) {
        super(card);
    }

    @Override
    public BisonWhistle copy() {
        return new BisonWhistle(this);
    }
}

class BisonWhistleEffect extends OneShotEffect {

    BisonWhistleEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top card of your library. If it's a Bison card, you may put it onto the battlefield. " +
                "If it's a creature card, you may reveal it and put it into your hand. Otherwise, you may put it into your graveyard";
    }

    private BisonWhistleEffect(final BisonWhistleEffect effect) {
        super(effect);
    }

    @Override
    public BisonWhistleEffect copy() {
        return new BisonWhistleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = player.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        player.lookAtCards("Top of library", card, game);
        if (card.hasSubtype(SubType.BISON, game) && player.chooseUse(
                Outcome.PutCardInPlay, "Put " + card.getIdName() + " onto the battlefield?", source, game
        )) {
            player.moveCards(card, Zone.BATTLEFIELD, source, game);
            // cards like Crib Swap and Containment Priest make it possible for the card
            // to either stay in the library or go to a different zone, so we only exit if it's not still on top
            if (!Zone.LIBRARY.match(game.getState().getZone(card.getId()))) {
                return true;
            }
        }
        if (card.isCreature(game) && player.chooseUse(
                Outcome.DrawCard, "Put " + card.getIdName() + " into your hand?", source, game
        )) {
            player.revealCards(source, new CardsImpl(card), game);
            return player.moveCards(card, Zone.HAND, source, game);
        }
        return player.chooseUse(
                Outcome.Discard, "Put " + card.getIdName() + " into your graveyard?", source, game
        ) && player.moveCards(card, Zone.GRAVEYARD, source, game);
    }
}
