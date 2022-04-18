package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CaseTheJoint extends CardImpl {

    public CaseTheJoint(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}");

        // Draw two cards, then look at the top card of each player's library.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
        this.getSpellAbility().addEffect(new CaseTheJointEffect());
    }

    private CaseTheJoint(final CaseTheJoint card) {
        super(card);
    }

    @Override
    public CaseTheJoint copy() {
        return new CaseTheJoint(this);
    }
}

class CaseTheJointEffect extends OneShotEffect {

    CaseTheJointEffect() {
        super(Outcome.Benefit);
        staticText = ", then look at the top card of each player's library";
    }

    private CaseTheJointEffect(final CaseTheJointEffect effect) {
        super(effect);
    }

    @Override
    public CaseTheJointEffect copy() {
        return new CaseTheJointEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(source.getControllerId());
            if (player == null) {
                continue;
            }
            Card card = player.getLibrary().getFromTop(game);
            if (card == null) {
                continue;
            }
            controller.lookAtCards(source, "Top card of " + player.getName() + "'s library", new CardsImpl(card), game);
        }
        return true;
    }
}
