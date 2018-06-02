
package mage.cards.n;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.common.ProwlCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.turn.AddExtraTurnControllerEffect;
import mage.abilities.keyword.ProwlAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.FaerieRogueToken;
import mage.players.Player;
import mage.watchers.common.AmountOfDamageAPlayerReceivedThisTurnWatcher;

/**
 *
 * @author LoneFox
 */
public final class NotoriousThrong extends CardImpl {

    public NotoriousThrong(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.TRIBAL,CardType.SORCERY},"{3}{U}");
        this.subtype.add(SubType.ROGUE);

        // Prowl {5}{U}
        this.addAbility(new ProwlAbility(this, "{5}{U}"));
        // create X 1/1 black Faerie Rogue creature tokens with flying, where X is the damage dealt to your opponents this turn.
        this.getSpellAbility().addEffect(new NotoriousThrongEffect());
        this.getSpellAbility().addWatcher(new AmountOfDamageAPlayerReceivedThisTurnWatcher());
        // If Notorious Throng's prowl cost was paid, take an extra turn after this one.
        Effect effect = new ConditionalOneShotEffect(new AddExtraTurnControllerEffect(), ProwlCondition.instance);
        effect.setText("If {this}'s prowl cost was paid, take an extra turn after this one.");
        this.getSpellAbility().addEffect(effect);
    }

    public NotoriousThrong(final NotoriousThrong card) {
        super(card);
    }

    @Override
    public NotoriousThrong copy() {
        return new NotoriousThrong(this);
    }
}

class NotoriousThrongEffect extends OneShotEffect {

    public NotoriousThrongEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "create X 1/1 black Faerie Rogue creature tokens with flying, where X is the damage dealt to your opponents this turn";
    }

    public NotoriousThrongEffect(NotoriousThrongEffect effect) {
        super(effect);
    }

    @Override
    public NotoriousThrongEffect copy() {
        return new NotoriousThrongEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        AmountOfDamageAPlayerReceivedThisTurnWatcher watcher = (AmountOfDamageAPlayerReceivedThisTurnWatcher) game.getState().getWatchers().get(AmountOfDamageAPlayerReceivedThisTurnWatcher.class.getSimpleName());
        if(controller != null && watcher != null) {
            int numTokens = 0;
            for(UUID opponentId: game.getOpponents(controller.getId())) {
                numTokens += watcher.getAmountOfDamageReceivedThisTurn(opponentId);
            }
            if(numTokens > 0) {
                new CreateTokenEffect(new FaerieRogueToken(), numTokens).apply(game, source);
            }
            return true;
        }
        return false;
    }
}
