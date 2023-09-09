
package mage.cards.h;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author emerald000 & L_J
 */
public final class HiredGiant extends CardImpl {

    public HiredGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.GIANT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Hired Giant enters the battlefield, each other player may search their library for a land card and put that card onto the battlefield. Then each player who searched their library this way shuffles it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new HiredGiantEffect()));
    }

    private HiredGiant(final HiredGiant card) {
        super(card);
    }

    @Override
    public HiredGiant copy() {
        return new HiredGiant(this);
    }
}

class HiredGiantEffect extends OneShotEffect {

    HiredGiantEffect() {
        super(Outcome.Detriment);
        this.staticText = "each other player may search their library for a land card and put that card onto the battlefield. Then each player who searched their library this way shuffles";
    }

    private HiredGiantEffect(final HiredGiantEffect effect) {
        super(effect);
    }

    @Override
    public HiredGiantEffect copy() {
        return new HiredGiantEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Set<Player> playersThatSearched = new HashSet<>(1);
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                if (!playerId.equals(controller.getId())) {
                    Player player = game.getPlayer(playerId);
                    if (player != null && player.chooseUse(Outcome.PutCreatureInPlay, "Search your library for a land card and put it onto the battlefield?", source, game)) {
                        TargetCardInLibrary target = new TargetCardInLibrary(new FilterLandCard());
                        if (player.searchLibrary(target, source, game)) {
                            Card targetCard = player.getLibrary().getCard(target.getFirstTarget(), game);
                            if (targetCard != null) {
                                player.moveCards(targetCard, Zone.BATTLEFIELD, source, game);
                                playersThatSearched.add(player);
                            }
                        }
                    }
                }
            }
            for (Player player : playersThatSearched) {
                player.shuffleLibrary(source, game);
            }
            return true;
        }
        return false;
    }
}
