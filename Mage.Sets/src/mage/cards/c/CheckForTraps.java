package mage.cards.c;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author weirddan455
 */
public final class CheckForTraps extends CardImpl {

    public CheckForTraps(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Target opponent reveals their hand. You choose a nonland card from it. Exile that card.
        // If an instant or a card with flash is exiled this way, they lose 1 life. Otherwise, you lose 1 life.
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addEffect(new CheckForTrapsEffect());
    }

    private CheckForTraps(final CheckForTraps card) {
        super(card);
    }

    @Override
    public CheckForTraps copy() {
        return new CheckForTraps(this);
    }
}

class CheckForTrapsEffect extends OneShotEffect {

    public CheckForTrapsEffect() {
        super(Outcome.Discard);
        this.staticText = "Target opponent reveals their hand. You choose a nonland card from it. Exile that card. " +
                "If an instant card or a card with flash is exiled this way, they lose 1 life. Otherwise, you lose 1 life";
    }

    private CheckForTrapsEffect(final CheckForTrapsEffect effect) {
        super(effect);
    }

    @Override
    public CheckForTrapsEffect copy() {
        return new CheckForTrapsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(targetPointer.getFirst(game, source));
        if (controller == null || opponent == null) {
            return false;
        }
        opponent.revealCards(source, opponent.getHand(), game);
        TargetCard target = new TargetCard(Zone.HAND, StaticFilters.FILTER_CARD_NON_LAND);
        target.setNotTarget(true);
        boolean opponentLoseLife = false;
        if (controller.choose(outcome, opponent.getHand(), target, source, game)) {
            Card card = game.getCard(target.getFirstTarget());
            if (card != null) {
                controller.moveCards(card, Zone.EXILED, source, game);
                if (card.isInstant(game) || card.hasAbility(FlashAbility.getInstance(), game)) {
                    opponentLoseLife = true;
                }
            }
        }
        if (opponentLoseLife) {
            opponent.loseLife(1, game, source, false);
        } else {
            controller.loseLife(1, game, source, false);
        }
        return true;
    }
}
