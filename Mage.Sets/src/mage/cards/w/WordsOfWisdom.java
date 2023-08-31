
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author cbt33, LevelX2 (Hunted Wumpus)
 */
 
public final class WordsOfWisdom extends CardImpl {

    public WordsOfWisdom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");


        // You draw two cards, then each other player draws a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2).setText("you draw two cards"));
        this.getSpellAbility().addEffect(new WordsOfWisdomEffect());
    }

    private WordsOfWisdom(final WordsOfWisdom card) {
        super(card);
    }

    @Override
    public WordsOfWisdom copy() {
        return new WordsOfWisdom(this);
    }
}

class WordsOfWisdomEffect extends OneShotEffect {

    public WordsOfWisdomEffect() {
        super(Outcome.Detriment);
        this.staticText = ", then each other player draws a card";
    }

    private WordsOfWisdomEffect(final WordsOfWisdomEffect effect) {
        super(effect);
    }

    @Override
    public WordsOfWisdomEffect copy() {
        return new WordsOfWisdomEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for(UUID playerId: game.getState().getPlayersInRange(controller.getId(), game)) {
                if (!playerId.equals(controller.getId())) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        player.drawCards(1, source, game);
                    }
                }
            }
        }
       return false;
    }
       
}
