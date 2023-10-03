
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Styxo
 */
public final class SabaccGame extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("permanent an opponent controls");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public SabaccGame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}");

        /*
         * Choose target permanent an opponent controls. That opponent chooses a permanent you control.
         * Flip a coin. If you win the flip, gain control of the permanent you chose.
         * If you lose the flip, your opponent gains control of the permanent they chose.
         */
        this.getSpellAbility().getEffects().add(new SabaccGameEffect());
        this.getSpellAbility().getTargets().add(new TargetPermanent(filter));
    }

    private SabaccGame(final SabaccGame card) {
        super(card);
    }

    @Override
    public SabaccGame copy() {
        return new SabaccGame(this);
    }
}

class SabaccGameEffect extends OneShotEffect {

    public SabaccGameEffect() {
        super(Outcome.Detriment);
        this.staticText = "Choose target permanent an opponent controls. That opponent chooses a permanent you control. "
                + "Flip a coin. If you win the flip, gain control of the permanent you chose. "
                + "If you lose the flip, your opponent gains control of the permanent they chose";
    }

    private SabaccGameEffect(final SabaccGameEffect effect) {
        super(effect);
    }

    @Override
    public SabaccGameEffect copy() {
        return new SabaccGameEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent targetPermanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
            if (targetPermanent != null) {
                Player opponent = game.getPlayer(targetPermanent.getControllerId());
                if (opponent != null) {
                    FilterPermanent filter = new FilterPermanent("permanent controlled by " + controller.getName());
                    filter.add(new ControllerIdPredicate(controller.getId()));
                    TargetPermanent target = new TargetPermanent(1, 1, filter, true);
                    Permanent chosenPermanent = null;
                    if (target.chooseTarget(outcome, opponent.getId(), source, game)) {
                        chosenPermanent = game.getPermanent(target.getFirstTarget());
                    }
                    boolean flipWin = controller.flipCoin(source, game, true);
                    if (flipWin) {
                        ContinuousEffect effect = new GainControlTargetEffect(Duration.Custom, true, controller.getId());
                        effect.setTargetPointer(new FixedTarget(targetPermanent, game));
                        game.addEffect(effect, source);
                    } else if (chosenPermanent != null) {
                        ContinuousEffect effect = new GainControlTargetEffect(Duration.Custom, true, opponent.getId());
                        effect.setTargetPointer(new FixedTarget(chosenPermanent, game));
                        game.addEffect(effect, source);
                    }

                }
            }
            return true;
        }
        return false;
    }
}
