package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author L_J
 */
public final class LiarsPendulum extends CardImpl {

    public LiarsPendulum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // {2}, {T}: Choose a card name. Target opponent guesses whether a card with that name is in your hand. You may reveal your hand. If you do and your opponent guessed wrong, draw a card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new LiarsPendulumEffect(), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private LiarsPendulum(final LiarsPendulum card) {
        super(card);
    }

    @Override
    public LiarsPendulum copy() {
        return new LiarsPendulum(this);
    }
}

class LiarsPendulumEffect extends OneShotEffect {

    public LiarsPendulumEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Choose a card name. Target opponent guesses whether a card with that name is in your hand. You may reveal your hand. If you do and your opponent guessed wrong, draw a card";
    }

    private LiarsPendulumEffect(final LiarsPendulumEffect effect) {
        super(effect);
    }

    @Override
    public LiarsPendulumEffect copy() {
        return new LiarsPendulumEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (controller == null || opponent == null) {
            return false;
        }
        // Name a card.
        String cardName = ChooseACardNameEffect.TypeOfName.ALL.getChoice(controller, game, source, false);
        boolean opponentGuess = false;

        if (opponent.chooseUse(Outcome.Neutral, "Is the chosen card (" + cardName + ") in " + controller.getLogName() + "'s hand?", source, game)) {
            opponentGuess = true;
        }
        boolean rightGuess = !opponentGuess;

        for (Card card : controller.getHand().getCards(game)) {
            if (CardUtil.haveSameNames(card, cardName, game)) {
                rightGuess = opponentGuess;
            }
        }
        game.informPlayers(opponent.getLogName() + " guesses that " + cardName + " is " + (opponentGuess ? "" : "not") + " in " + controller.getLogName() + "'s hand");

        if (controller.chooseUse(outcome, "Reveal your hand?", source, game)) {
            controller.revealCards("hand of " + controller.getName(), controller.getHand(), game);
            if (!rightGuess) {
                controller.drawCards(1, source, game);
            }
        }
        return true;
    }

}
