package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.discard.DiscardHandControllerEffect;
import mage.abilities.effects.common.discard.DiscardHandDrawSameNumberSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BorrowedKnowledge extends CardImpl {

    public BorrowedKnowledge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}{W}");

        // Choose one --
        // * Discard your hand, then draw cards equal to the number of cards in target opponent's hand.
        this.getSpellAbility().addEffect(new DiscardHandControllerEffect());
        this.getSpellAbility().addEffect(new BorrowedKnowledgeEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());

        // * Discard your hand, then draw cards equal to the number of cards discarded this way.
        this.getSpellAbility().addMode(new Mode(new DiscardHandDrawSameNumberSourceEffect()
                .setText("discard your hand, then draw cards equal to the number of cards discarded this way")));
    }

    private BorrowedKnowledge(final BorrowedKnowledge card) {
        super(card);
    }

    @Override
    public BorrowedKnowledge copy() {
        return new BorrowedKnowledge(this);
    }
}

class BorrowedKnowledgeEffect extends OneShotEffect {

    BorrowedKnowledgeEffect() {
        super(Outcome.Benefit);
        staticText = ", then draw cards equal to the number of cards in target opponent's hand";
    }

    private BorrowedKnowledgeEffect(final BorrowedKnowledgeEffect effect) {
        super(effect);
    }

    @Override
    public BorrowedKnowledgeEffect copy() {
        return new BorrowedKnowledgeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        return player != null && opponent != null && player.drawCards(opponent.getHand().size(), source, game) > 0;
    }
}
