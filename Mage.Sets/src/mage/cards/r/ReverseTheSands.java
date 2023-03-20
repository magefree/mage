
package mage.cards.r;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class ReverseTheSands extends CardImpl {

    public ReverseTheSands(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{6}{W}{W}");

        // Redistribute any number of players' life totals.
        this.getSpellAbility().addEffect(new ReverseTheSandsEffect());
    }

    private ReverseTheSands(final ReverseTheSands card) {
        super(card);
    }

    @Override
    public ReverseTheSands copy() {
        return new ReverseTheSands(this);
    }
}

class ReverseTheSandsEffect extends OneShotEffect {

    public ReverseTheSandsEffect() {
        super(Outcome.Benefit);
        this.staticText = "Redistribute any number of players' life totals";
    }

    public ReverseTheSandsEffect(final ReverseTheSandsEffect effect) {
        super(effect);
    }

    @Override
    public ReverseTheSandsEffect copy() {
        return new ReverseTheSandsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Choice lifeChoice = new ChoiceImpl(true);
            Set<String> choices = new LinkedHashSet<>();
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    choices.add(Integer.toString(player.getLife()) + " life of " + player.getLogName());
                }
            }
            lifeChoice.setChoices(choices);
            for (UUID playersId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playersId);
                if (player != null) {
                    String selectedChoice;
                    if (choices.size() > 1) {
                        lifeChoice.setMessage("Which players life should get player " + player.getLogName());
                        if (!controller.choose(Outcome.Detriment, lifeChoice, game)) {
                            return false;
                        }
                        selectedChoice = lifeChoice.getChoice();
                    } else {
                        selectedChoice = choices.iterator().next();
                    }
                    int index = selectedChoice.indexOf(' ');
                    if (index > 0) {
                        String lifeString = selectedChoice.substring(0, index);
                        int life = Integer.parseInt(lifeString);
                        player.setLife(life, game, source);
                        choices.remove(selectedChoice);
                        game.informPlayers(new StringBuilder("Player ").append(player.getLogName()).append(" life set to ").append(life).toString());
                    }
                }
            }
        }
        return false;
    }
}
