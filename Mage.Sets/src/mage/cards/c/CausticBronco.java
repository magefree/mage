package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.common.SaddledCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.SaddleAbility;
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
public final class CausticBronco extends CardImpl {

    public CausticBronco(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.HORSE);
        this.subtype.add(SubType.MOUNT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Caustic Bronco attacks, reveal the top card of your library and put it into your hand. You lose life equal to that card's mana value if Caustic Bronco isn't saddled. Otherwise, each opponent loses that much life.
        this.addAbility(new AttacksTriggeredAbility(new CausticBroncoEffect()));

        // Saddle 3
        this.addAbility(new SaddleAbility(3));
    }

    private CausticBronco(final CausticBronco card) {
        super(card);
    }

    @Override
    public CausticBronco copy() {
        return new CausticBronco(this);
    }
}

class CausticBroncoEffect extends OneShotEffect {

    CausticBroncoEffect() {
        super(Outcome.Benefit);
        staticText = "reveal the top card of your library and put it into your hand. " +
                "You lose life equal to that card's mana value if {this} isn't saddled. " +
                "Otherwise, each opponent loses that much life";
    }

    private CausticBroncoEffect(final CausticBroncoEffect effect) {
        super(effect);
    }

    @Override
    public CausticBroncoEffect copy() {
        return new CausticBroncoEffect(this);
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
        player.revealCards(source, new CardsImpl(card), game);
        player.moveCards(card, Zone.HAND, source, game);
        if (!SaddledCondition.instance.apply(game, source)) {
            player.loseLife(card.getManaValue(), game, source, false);
            return true;
        }
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(playerId);
            if (opponent != null) {
                opponent.loseLife(card.getManaValue(), game, source, false);
            }
        }
        return true;
    }
}
