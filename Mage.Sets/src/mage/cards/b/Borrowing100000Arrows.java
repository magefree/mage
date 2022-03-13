
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public final class Borrowing100000Arrows extends CardImpl {

    public Borrowing100000Arrows(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{U}");


        // Draw a card for each tapped creature target opponent controls.
        this.getSpellAbility().addEffect(new Borrowing100000ArrowsEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private Borrowing100000Arrows(final Borrowing100000Arrows card) {
        super(card);
    }

    @Override
    public Borrowing100000Arrows copy() {
        return new Borrowing100000Arrows(this);
    }
}

class Borrowing100000ArrowsEffect extends OneShotEffect {

    public Borrowing100000ArrowsEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Draw a card for each tapped creature target opponent controls";
    }

    public Borrowing100000ArrowsEffect(final Borrowing100000ArrowsEffect effect) {
        super(effect);
    }

    @Override
    public Borrowing100000ArrowsEffect copy() {
        return new Borrowing100000ArrowsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(this.getTargetPointer().getFirst(game, source));
            if (opponent != null) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent();
            filter.add(TappedPredicate.TAPPED);
            filter.add(new ControllerIdPredicate(opponent.getId()));
            return new DrawCardSourceControllerEffect(game.getBattlefield().count(filter, source.getControllerId(), source, game)).apply(game, source);
        }
        return false;
    }
}
