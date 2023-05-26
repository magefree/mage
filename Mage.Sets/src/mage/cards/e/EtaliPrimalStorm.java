package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.common.FilterNonlandCard;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author ciaccona007 & L_J
 */
public final class EtaliPrimalStorm extends CardImpl {

    public EtaliPrimalStorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Whenever Etali, Primal Storm attacks, exile the top card of each player's library, 
        // then you may cast any number of nonland cards exiled this way without paying their mana costs.
        this.addAbility(new AttacksTriggeredAbility(new EtaliPrimalStormEffect(), false));
    }

    private EtaliPrimalStorm(final EtaliPrimalStorm card) {
        super(card);
    }

    @Override
    public EtaliPrimalStorm copy() {
        return new EtaliPrimalStorm(this);
    }
}

class EtaliPrimalStormEffect extends OneShotEffect {

    private static final FilterNonlandCard filter = new FilterNonlandCard("nonland cards");

    public EtaliPrimalStormEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "exile the top card of each player's library, then you may cast "
                + "any number of spells from among those cards without paying their mana costs";
    }

    public EtaliPrimalStormEffect(final EtaliPrimalStormEffect effect) {
        super(effect);
    }

    @Override
    public EtaliPrimalStormEffect copy() {
        return new EtaliPrimalStormEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        // move cards from library to exile
        Cards cards = new CardsImpl();
        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                cards.add(player.getLibrary().getFromTop(game));
            }
        }
        controller.moveCards(cards, Zone.EXILED, source, game);
        CardUtil.castMultipleWithAttributeForFree(controller, source, game, cards, filter);
        return true;
    }
}
