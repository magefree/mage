
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ReboundAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author jeffwadsworth
 */
public final class RecurringInsight extends CardImpl {

    public RecurringInsight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{U}{U}");
        
        // Rebound
        this.addAbility(new ReboundAbility());

        // Draw cards equal to the number of cards in target opponent's hand.
        this.getSpellAbility().addEffect(new RecurringInsightEffect());    
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private RecurringInsight(final RecurringInsight card) {
        super(card);
    }

    @Override
    public RecurringInsight copy() {
        return new RecurringInsight(this);
    }
}

class RecurringInsightEffect extends OneShotEffect {

    public RecurringInsightEffect() {
        super(Outcome.DrawCard);
        staticText = "Draw cards equal to the number of cards in target opponent's hand";
    }

    private RecurringInsightEffect(final RecurringInsightEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
            if (opponent != null) {
                controller.drawCards(opponent.getHand().size(), source, game);
            }
            return true;
        }
        return false;
    }

    @Override
    public RecurringInsightEffect copy() {
        return new RecurringInsightEffect(this);
    }

}
