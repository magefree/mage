
package mage.cards.t;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterNonlandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author Styxo
 */
public final class TheBattleOfYavin extends CardImpl {

    public TheBattleOfYavin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{B}{B}");

        // For each nonland permanent target opponent controls, that player sacrificies it unless they pay X life.
        this.getSpellAbility().addEffect(new TheBattleOfYavinEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());

    }

    private TheBattleOfYavin(final TheBattleOfYavin card) {
        super(card);
    }

    @Override
    public TheBattleOfYavin copy() {
        return new TheBattleOfYavin(this);
    }
}

class TheBattleOfYavinEffect extends OneShotEffect {

    public TheBattleOfYavinEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "For each nonland permanent target opponent controls, that player sacrificies it unless they pay X life";
    }

    private TheBattleOfYavinEffect(final TheBattleOfYavinEffect effect) {
        super(effect);
    }

    @Override
    public TheBattleOfYavinEffect copy() {
        return new TheBattleOfYavinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(source.getTargets().getFirstTarget());
        if (opponent == null) {
            return false;
        }

        int amount = (ManacostVariableValue.REGULAR).calculate(game, source, this);
        if (amount > 0) {
            LinkedList<Permanent> sacrifices = new LinkedList<>();

            FilterNonlandPermanent filter = new FilterNonlandPermanent();
            List<Permanent> permanents = game.getBattlefield().getAllActivePermanents(filter, opponent.getId(), game);

            int lifePaid = 0;
            int playerLife = opponent.getLife();
            for (Permanent permanent : permanents) {
                String message = "Pay " + amount + " life? If you don't, " + permanent.getName() + " will be sacrificed.";
                if (playerLife - amount - lifePaid >= 0 && opponent.chooseUse(Outcome.Neutral, message, source, game)) {
                    game.informPlayers(opponent.getLogName() + " pays " + amount + " life. They will not sacrifice " + permanent.getName());
                    lifePaid += amount;
                } else {
                    game.informPlayers(opponent.getLogName() + " will sacrifice " + permanent.getName());
                    sacrifices.add(permanent);
                }
            }

            if (lifePaid > 0) {
                Player player = game.getPlayer(opponent.getId());
                if (player != null) {
                    player.loseLife(lifePaid, game, source, false);
                }
            }

            for (Permanent permanent : sacrifices) {
                permanent.sacrifice(source, game);
            }
        }
        return true;
    }
}
