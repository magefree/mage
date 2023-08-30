
package mage.cards.u;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.HighestManaValueCount;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public final class UginsInsight extends CardImpl {

    public UginsInsight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{U}{U}");

        // Scry X, where X is the highest converted mana cost among permanents you control, then draw three cards.
        this.getSpellAbility().addEffect(new UginsInsightEffect());
    }

    private UginsInsight(final UginsInsight card) {
        super(card);
    }

    @Override
    public UginsInsight copy() {
        return new UginsInsight(this);
    }
}

class UginsInsightEffect extends OneShotEffect {

    public UginsInsightEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Scry X, where X is the highest mana value among permanents you control, then draw three cards";
    }

    private UginsInsightEffect(final UginsInsightEffect effect) {
        super(effect);
    }

    @Override
    public UginsInsightEffect copy() {
        return new UginsInsightEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int highCMC = new HighestManaValueCount().calculate(game, source, this);
            if (highCMC > 0) {
                controller.scry(highCMC, source, game);
            }
            controller.drawCards(3, source, game);
            return true;
        }
        return false;
    }
}
