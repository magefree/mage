
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.AttackingFilterCreatureCount;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public final class FoulTongueShriek extends CardImpl {

    public FoulTongueShriek(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{B}");

        // Target opponent loses 1 life for each attacking creature you control. You gain that much life.
        this.getSpellAbility().addEffect(new FoulTongueShriekEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());

    }

    public FoulTongueShriek(final FoulTongueShriek card) {
        super(card);
    }

    @Override
    public FoulTongueShriek copy() {
        return new FoulTongueShriek(this);
    }
}

class FoulTongueShriekEffect extends OneShotEffect {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();
    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
    }
    
    public FoulTongueShriekEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target opponent loses 1 life for each attacking creature you control. You gain that much life";
    }

    public FoulTongueShriekEffect(final FoulTongueShriekEffect effect) {
        super(effect);
    }

    @Override
    public FoulTongueShriekEffect copy() {
        return new FoulTongueShriekEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetOpponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller != null && targetOpponent != null) {
            int amount = new AttackingFilterCreatureCount(filter).calculate(game, source, this);
            if (amount > 0) {
                targetOpponent.loseLife(amount, game, false);
                controller.gainLife(amount, game, source);
            }
            return true;
        }
        return false;
    }
}
