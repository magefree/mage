
package mage.cards.a;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetDiscard;

/**
 *
 * @author TheElk801
 */
public final class AzraBladeseeker extends CardImpl {

    public AzraBladeseeker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.AZRA);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Azra Bladeseeker enters the battlefield, each player on your team may discard a card, then each player who discarded a card this way draws a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AzraBladeseekerEffect(), false));
    }

    private AzraBladeseeker(final AzraBladeseeker card) {
        super(card);
    }

    @Override
    public AzraBladeseeker copy() {
        return new AzraBladeseeker(this);
    }
}

class AzraBladeseekerEffect extends OneShotEffect {

    AzraBladeseekerEffect() {
        super(Outcome.Benefit);
        this.staticText = "each player on your team may discard a card, then each player who discarded a card this way draws a card";
    }

    AzraBladeseekerEffect(final AzraBladeseekerEffect effect) {
        super(effect);
    }

    @Override
    public AzraBladeseekerEffect copy() {
        return new AzraBladeseekerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<PlayerCard> playerCardList = new ArrayList<>();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null
                    || player.hasOpponent(source.getControllerId(), game)
                    || player.getHand().isEmpty()
                    || !player.chooseUse(Outcome.DrawCard, "Discard a card?", source, game)) {
                continue;
            }
            Target target = new TargetDiscard(playerId);
            if (target.choose(Outcome.DrawCard, playerId, source.getSourceId(), source, game)) {
                Card card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    playerCardList.add(new PlayerCard(player, card));
                }
            }
        }
        for (PlayerCard playerCard : playerCardList) {
            if (playerCard.getPlayer().discard(playerCard.getCard(), false, source, game)) {
                playerCard.getPlayer().drawCards(1, source, game);
            }
        }
        return true;
    }

    class PlayerCard {

        private final Player player;
        private final Card card;

        private PlayerCard(Player player, Card card) {
            this.player = player;
            this.card = card;
        }

        public Player getPlayer() {
            return player;
        }

        public Card getCard() {
            return card;
        }
    }
}
