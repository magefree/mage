
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.turn.TurnMod;

/**
 *
 * @author Plopman
 */
public final class Meditate extends CardImpl {

    public Meditate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}");


        // Draw four cards. You skip your next turn.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(4));
        this.getSpellAbility().addEffect(new SpipTurnEffect());
    }

    private Meditate(final Meditate card) {
        super(card);
    }

    @Override
    public Meditate copy() {
        return new Meditate(this);
    }
}

class SpipTurnEffect extends OneShotEffect {

    public SpipTurnEffect() {
        super(Outcome.Neutral);
        staticText = "You skip your next turn";
    }

    public SpipTurnEffect(final SpipTurnEffect effect) {
        super(effect);
    }

    @Override
    public SpipTurnEffect copy() {
        return new SpipTurnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.getState().getTurnMods().add(new TurnMod(source.getControllerId()).withSkipTurn());
        return true;
    }

}