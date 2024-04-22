package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class AshiokNightmareMuseToken extends TokenImpl {

    public AshiokNightmareMuseToken() {
        super("Nightmare Token", "2/3 blue and black Nightmare creature token with " +
                "\"Whenever this creature attacks or blocks, each opponent exiles the top two cards of their library.\"");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        color.setBlack(true);
        subtype.add(SubType.NIGHTMARE);
        power = new MageInt(2);
        toughness = new MageInt(3);
        this.addAbility(new AttacksOrBlocksTriggeredAbility(new AshiokNightmareMuseTokenEffect(), false));
    }

    private AshiokNightmareMuseToken(final AshiokNightmareMuseToken token) {
        super(token);
    }

    public AshiokNightmareMuseToken copy() {
        return new AshiokNightmareMuseToken(this);
    }
}

class AshiokNightmareMuseTokenEffect extends OneShotEffect {

    AshiokNightmareMuseTokenEffect() {
        super(Outcome.Benefit);
        staticText = "each opponent exiles the top two cards of their library.";
    }

    private AshiokNightmareMuseTokenEffect(final AshiokNightmareMuseTokenEffect effect) {
        super(effect);
    }

    @Override
    public AshiokNightmareMuseTokenEffect copy() {
        return new AshiokNightmareMuseTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Set<Card> cards = game
                .getOpponents(source.getControllerId())
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .map(Player::getLibrary)
                .map(library -> library.getTopCards(game, 2))
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
        return player.moveCards(cards, Zone.EXILED, source, game);
    }
}