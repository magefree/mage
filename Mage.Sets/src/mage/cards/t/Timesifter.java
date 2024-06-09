
package mage.cards.t;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.turn.AddExtraTurnTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author emerald000
 */
public final class Timesifter extends CardImpl {

    public Timesifter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{5}");

        // At the beginning of each upkeep, each player exiles the top card of their library. The player who exiled the card with the highest converted mana cost takes an extra turn after this one. If two or more players' cards are tied for highest cost, the tied players repeat this process until the tie is broken.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new TimesifterEffect(), TargetController.EACH_PLAYER, false));
    }

    private Timesifter(final Timesifter card) {
        super(card);
    }

    @Override
    public Timesifter copy() {
        return new Timesifter(this);
    }
}

class TimesifterEffect extends OneShotEffect {

    TimesifterEffect() {
        super(Outcome.ExtraTurn);
        this.staticText = "each player exiles the top card of their library. The player who exiled the card with the highest mana value takes an extra turn after this one. If two or more players' cards are tied for highest, the tied players repeat this process until the tie is broken";
    }

    private TimesifterEffect(final TimesifterEffect effect) {
        super(effect);
    }

    @Override
    public TimesifterEffect copy() {
        return new TimesifterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<UUID> playersExiling = game.getState().getPlayersInRange(source.getControllerId(), game);
        do {
            int highestCMC = Integer.MIN_VALUE;
            List<UUID> playersWithHighestCMC = new ArrayList<>(1);
            for (UUID playerId : playersExiling) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    Card card = player.getLibrary().getFromTop(game);
                    if (card != null) {
                        int cardCMC = card.getManaValue();
                        player.moveCardsToExile(card, source, game, true, null, "");
                        if (cardCMC > highestCMC) {
                            highestCMC = cardCMC;
                            playersWithHighestCMC.clear();
                            playersWithHighestCMC.add(playerId);
                        }
                        else if (cardCMC == highestCMC) {
                            playersWithHighestCMC.add(playerId);
                        }
                    }
                }
            }
            playersExiling = new ArrayList<>(playersWithHighestCMC);
        } while (playersExiling.size() > 1);
        for (UUID playerId : playersExiling) {
            Effect effect = new AddExtraTurnTargetEffect();
            effect.setTargetPointer(new FixedTarget(playerId));
            effect.apply(game, source);
            Player player = game.getPlayer(playerId);
            if (player != null) {
                game.informPlayers(player.getLogName() + " will take an extra turn after this one.");
            }
        }
        return true;
    }
}
