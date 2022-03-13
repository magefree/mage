package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class PryingQuestions extends CardImpl {

    public PryingQuestions(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Target opponent loses 3 life and puts a card from their hand on top of their library.
        this.getSpellAbility().addEffect(new LoseLifeTargetEffect(3));
        this.getSpellAbility().addEffect(new PryingQuestionsEffect().concatBy("and"));
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private PryingQuestions(final PryingQuestions card) {
        super(card);
    }

    @Override
    public PryingQuestions copy() {
        return new PryingQuestions(this);
    }
}

class PryingQuestionsEffect extends OneShotEffect {

    public PryingQuestionsEffect() {
        super(Outcome.Detriment);
        this.staticText = "puts a card from their hand on top of their library";
    }

    public PryingQuestionsEffect(final PryingQuestionsEffect effect) {
        super(effect);
    }

    @Override
    public PryingQuestionsEffect copy() {
        return new PryingQuestionsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetOpponent = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (targetOpponent != null) {
            if (!targetOpponent.getHand().isEmpty()) {
                TargetCardInHand target = new TargetCardInHand();
                target.setNotTarget(true);
                target.setTargetName("a card from your hand to put on top of your library");
                targetOpponent.choose(Outcome.Detriment, target, source, game);
                Card card = targetOpponent.getHand().get(target.getFirstTarget(), game);
                if (card != null) {
                    targetOpponent.moveCardToLibraryWithInfo(card, source, game, Zone.HAND, true, false);
                }
            }
            return true;
        }
        return false;
    }
}
