
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author North
 */
public final class PrimalSurge extends CardImpl {

    public PrimalSurge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{8}{G}{G}");

        // Exile the top card of your library. If it's a permanent card, you may put it onto the battlefield. If you do, repeat this process.
        this.getSpellAbility().addEffect(new PrimalSurgeEffect());
    }

    private PrimalSurge(final PrimalSurge card) {
        super(card);
    }

    @Override
    public PrimalSurge copy() {
        return new PrimalSurge(this);
    }
}

class PrimalSurgeEffect extends OneShotEffect {

    public PrimalSurgeEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Exile the top card of your library. If it's a permanent card, you may put it onto the battlefield. If you do, repeat this process";
    }

    private PrimalSurgeEffect(final PrimalSurgeEffect effect) {
        super(effect);
    }

    @Override
    public PrimalSurgeEffect copy() {
        return new PrimalSurgeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        boolean repeat;
        do {
            repeat = false;
            Card card = controller.getLibrary().getFromTop(game);
            if (card != null) {
                controller.moveCards(card, Zone.EXILED, source, game);
                if (card.isPermanent(game)
                        && controller.chooseUse(Outcome.PutCardInPlay, "Put " + card.getName() + " onto the battlefield?", source, game)) {
                    controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                    repeat = true;
                }
            }
        } while (controller.canRespond() && repeat);

        return true;
    }
}
