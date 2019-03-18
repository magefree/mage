
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author North
 */
public final class ChillOfForeboding extends CardImpl {

    public ChillOfForeboding(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{U}");

        // Each player puts the top five cards of their library into their graveyard.
        this.getSpellAbility().addEffect(new ChillOfForebodingEffect());
        // Flashback {7}{U}
        this.addAbility(new FlashbackAbility(new ManaCostsImpl("{7}{U}"), TimingRule.SORCERY));
    }

    public ChillOfForeboding(final ChillOfForeboding card) {
        super(card);
    }

    @Override
    public ChillOfForeboding copy() {
        return new ChillOfForeboding(this);
    }
}

class ChillOfForebodingEffect extends OneShotEffect {

    public ChillOfForebodingEffect() {
        super(Outcome.Detriment);
        this.staticText = "Each player puts the top five cards of their library into their graveyard";
    }

    public ChillOfForebodingEffect(final ChillOfForebodingEffect effect) {
        super(effect);
    }

    @Override
    public ChillOfForebodingEffect copy() {
        return new ChillOfForebodingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player sourcePlayer = game.getPlayer(source.getControllerId());
        for (UUID playerId : game.getState().getPlayersInRange(sourcePlayer.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                player.moveCards(player.getLibrary().getTopCards(game, 5), Zone.GRAVEYARD, source, game);
            }
        }
        return true;
    }
}
