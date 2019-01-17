
package mage.cards.m;

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
public final class MarshalingTheTroops extends CardImpl {

    public MarshalingTheTroops(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{G}");

        // Tap any number of untapped creatures you control. You gain 4 life for each creature tapped this way.
        this.getSpellAbility().addEffect(new MarshalingTheTroopsEffect());
    }

    public MarshalingTheTroops(final MarshalingTheTroops card) {
        super(card);
    }

    @Override
    public MarshalingTheTroops copy() {
        return new MarshalingTheTroops(this);
    }
}

class MarshalingTheTroopsEffect extends OneShotEffect {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("untapped creatures you control");
    
    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(Predicates.not(TappedPredicate.instance));
    }

    public MarshalingTheTroopsEffect() {
        super(Outcome.GainLife);
        staticText = "Tap any number of untapped creatures you control. You gain 4 life for each creature tapped this way";
    }

    public MarshalingTheTroopsEffect(MarshalingTheTroopsEffect effect) {
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
    public MarshalingTheTroopsEffect copy() {
        return new MarshalingTheTroopsEffect(this);
    }

}
