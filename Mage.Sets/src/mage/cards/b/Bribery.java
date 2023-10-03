
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public final class Bribery extends CardImpl {

    public Bribery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{U}{U}");

        // Search target opponent's library for a creature card and put that card onto the battlefield under your control. Then that player shuffles their library.
        this.getSpellAbility().addEffect(new BriberyEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private Bribery(final Bribery card) {
        super(card);
    }

    @Override
    public Bribery copy() {
        return new Bribery(this);
    }
}

class BriberyEffect extends OneShotEffect {

    public BriberyEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Search target opponent's library for a creature card and put that card onto the battlefield under your control. Then that player shuffles";
    }

    private BriberyEffect(final BriberyEffect effect) {
        super(effect);
    }

    @Override
    public BriberyEffect copy() {
        return new BriberyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && opponent != null) {
            TargetCardInLibrary target = new TargetCardInLibrary(0, 1, new FilterCreatureCard("creature card"));
            if (controller.searchLibrary(target, source, game, opponent.getId())) {
                Card card = opponent.getLibrary().getCard(target.getFirstTarget(), game);
                controller.moveCards(card, Zone.BATTLEFIELD, source, game);
            }
            opponent.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }
}
