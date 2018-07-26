package mage.cards.o;

import java.util.UUID;

import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.players.PlayerList;

/**
 *
 * @author NinthWorld
 */
public final class Observatory extends CardImpl {

    public Observatory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        

        // Observatory enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // When Observatory enters the battlefield, look at the top card of each player's library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ObservatoryEffect()));

        // {T}: Add {U} or {W} to your mana pool.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.BlueMana(1), new TapSourceCost()));
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.WhiteMana(1), new TapSourceCost()));
    }

    public Observatory(final Observatory card) {
        super(card);
    }

    @Override
    public Observatory copy() {
        return new Observatory(this);
    }
}

class ObservatoryEffect extends OneShotEffect {

    public ObservatoryEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top card of each player's library";
    }

    public ObservatoryEffect(final ObservatoryEffect effect) {
        super(effect);
    }

    @Override
    public ObservatoryEffect copy() {
        return new ObservatoryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if(controller != null && sourceObject != null) {
            PlayerList playerList = game.getPlayerList().copy();
            playerList.setCurrent(game.getActivePlayerId());
            Player player = game.getPlayer(game.getActivePlayerId());
            do {
                Cards cards = new CardsImpl();
                cards.addAll(player.getLibrary().getTopCards(game, 1));
                controller.lookAtCards(sourceObject.getIdName(), cards, game);
                player = playerList.getNext(game);
            } while (!player.getId().equals(game.getActivePlayerId()));
            return true;
        }
        return false;
    }
}