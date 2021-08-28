
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.GoblinToken;
import mage.players.Player;

/**
 *
 * @author spjspj
 */
public final class BoxOfFreerangeGoblins extends CardImpl {

    public BoxOfFreerangeGoblins(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}{R}");

        // Roll a six-sided die. Create a number of 1/1 red Goblin creature tokens equal to the result.
        this.getSpellAbility().addEffect(new BoxOfFreerangeGoblinsEffect());
    }

    private BoxOfFreerangeGoblins(final BoxOfFreerangeGoblins card) {
        super(card);
    }

    @Override
    public BoxOfFreerangeGoblins copy() {
        return new BoxOfFreerangeGoblins(this);
    }
}

class BoxOfFreerangeGoblinsEffect extends OneShotEffect {

    public BoxOfFreerangeGoblinsEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Roll a six-sided die. Create a number of 1/1 red Goblin creature tokens equal to the result";
    }

    public BoxOfFreerangeGoblinsEffect(final BoxOfFreerangeGoblinsEffect effect) {
        super(effect);
    }

    @Override
    public BoxOfFreerangeGoblinsEffect copy() {
        return new BoxOfFreerangeGoblinsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int amount = controller.rollDice(outcome, source, game, 6);
            CreateTokenEffect effect = new CreateTokenEffect(new GoblinToken(), amount);
            effect.apply(game, source);
            return true;
        }
        return false;
    }
}
