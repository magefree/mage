
package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.CardsInControllerLibraryCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.game.Game;
import mage.game.turn.Step;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class EnterTheInfinite extends CardImpl {

    public EnterTheInfinite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{8}{U}{U}{U}{U}");

        // Draw cards equal to the number of cards in your library,
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(CardsInControllerLibraryCount.instance));
        //then put a card from your hand on top of your library.
        this.getSpellAbility().addEffect(new PutCardOnLibraryEffect());
        //You have no maximum hand size until your next turn.
        this.getSpellAbility().addEffect(new MaximumHandSizeEffect());
    }

    private EnterTheInfinite(final EnterTheInfinite card) {
        super(card);
    }

    @Override
    public EnterTheInfinite copy() {
        return new EnterTheInfinite(this);
    }
}

class PutCardOnLibraryEffect extends OneShotEffect {

    PutCardOnLibraryEffect() {
        super(Outcome.DrawCard);
        staticText = "Then put a card from your hand on top of your library";
    }

    private PutCardOnLibraryEffect(final PutCardOnLibraryEffect effect) {
        super(effect);
    }

    @Override
    public PutCardOnLibraryEffect copy() {
        return new PutCardOnLibraryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            TargetCardInHand target = new TargetCardInHand();
            controller.chooseTarget(Outcome.ReturnToHand, target, source, game);
            Card card = controller.getHand().get(target.getFirstTarget(), game);
            if (card != null) {
                controller.putCardsOnTopOfLibrary(new CardsImpl(card), game, source, false);
            }
            return true;
        }
        return false;
    }
}

class MaximumHandSizeEffect extends MaximumHandSizeControllerEffect {

    MaximumHandSizeEffect() {
        super(Integer.MAX_VALUE, Duration.Custom, MaximumHandSizeControllerEffect.HandSizeModification.SET);
        staticText = "You have no maximum hand size until your next turn";
    }

    private MaximumHandSizeEffect(final MaximumHandSizeEffect effect) {
        super(effect);
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        if (game.getPhase().getStep().getType() == PhaseStep.UNTAP && game.getStep().getStepPart() == Step.StepPart.PRE) {
            if (game.isActivePlayer(source.getControllerId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public MaximumHandSizeEffect copy() {
        return new MaximumHandSizeEffect(this);
    }
}
