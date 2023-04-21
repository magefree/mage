package mage.cards.n;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author TheElk801
 */
public final class NeverHappened extends CardImpl {

    public NeverHappened(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Target opponent reveals their hand. You choose a nonland card from that player's graveyard or hand and exile it.
        this.getSpellAbility().addEffect(new NeverHappenedEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private NeverHappened(final NeverHappened card) {
        super(card);
    }

    @Override
    public NeverHappened copy() {
        return new NeverHappened(this);
    }
}

class NeverHappenedEffect extends OneShotEffect {

    public NeverHappenedEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target opponent reveals their hand. "
                + "You choose a nonland card from that player's graveyard "
                + "or hand and exile it.";
    }

    public NeverHappenedEffect(final NeverHappenedEffect effect) {
        super(effect);
    }

    @Override
    public NeverHappenedEffect copy() {
        return new NeverHappenedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(source.getFirstTarget());
        if (controller == null || opponent == null) {
            return false;
        }
        opponent.revealCards(source, opponent.getHand(), game);
        TargetCard target;
        Cards cards;
        if (controller.chooseUse(outcome, "Exile a card from hand or graveyard?", null, "Hand", "Graveyard", source, game)) {
            FilterCard filter = new FilterNonlandCard("nonland card in " + opponent.getName() + "'s hand");
            target = new TargetCard(Zone.HAND, filter);
            target.setNotTarget(true);
            cards = opponent.getHand();
        } else {
            FilterCard filter = new FilterNonlandCard("nonland card in " + opponent.getName() + "'s graveyard");
            target = new TargetCard(Zone.GRAVEYARD, filter);
            target.setNotTarget(true);
            cards = opponent.getGraveyard();
        }
        if (controller.choose(outcome, cards, target, source, game)) {
            Card card = game.getCard(target.getFirstTarget());
            if (card != null) {
                controller.moveCards(card, Zone.EXILED, source, game);
            }
        }
        return true;
    }
}
