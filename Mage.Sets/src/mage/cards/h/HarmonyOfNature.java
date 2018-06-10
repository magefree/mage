
package mage.cards.h;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author fireshoes
 */
public final class HarmonyOfNature extends CardImpl {

    public HarmonyOfNature(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{G}");

        // Tap any number of untapped creatures you control. You gain 4 life for each creature tapped this way.
        this.getSpellAbility().addEffect(new HarmonyOfNatureEffect());
    }

    public HarmonyOfNature(final HarmonyOfNature card) {
        super(card);
    }

    @Override
    public HarmonyOfNature copy() {
        return new HarmonyOfNature(this);
    }
}

class HarmonyOfNatureEffect extends OneShotEffect {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("untapped creatures you control");
    
    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(Predicates.not(new TappedPredicate()));
    }

    public HarmonyOfNatureEffect() {
        super(Outcome.GainLife);
        staticText = "Tap any number of untapped creatures you control. You gain 4 life for each creature tapped this way";
    }

    public HarmonyOfNatureEffect(HarmonyOfNatureEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int tappedAmount = 0;
            TargetPermanent target = new TargetPermanent(0, 1, filter, false);
            while (true) {
                target.clearChosen();
                if (target.canChoose(source.getControllerId(), game) && target.choose(Outcome.Tap, source.getControllerId(), source.getSourceId(), game)) {
                    Map<String, Serializable> options = new HashMap<>();
                    options.put("UI.right.btn.text", "Tapping complete");
                    controller.choose(outcome, target, source.getControllerId(), game, options);
                    if (!target.getTargets().isEmpty()) {
                        UUID creature = target.getFirstTarget();
                        if (creature != null) {
                            game.getPermanent(creature).tap(game);
                            tappedAmount++;
                        }
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
            if (tappedAmount > 0) {
                controller.gainLife(tappedAmount * 4, game, source);
            }
            return true;
        }
        return false;
    }

    @Override
    public HarmonyOfNatureEffect copy() {
        return new HarmonyOfNatureEffect(this);
    }

}
