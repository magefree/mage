
package mage.cards.d;

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
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.token.AngelToken;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class DevoutInvocation extends CardImpl {

    public DevoutInvocation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{6}{W}");

        // Tap any number of untapped creatures you control. Create a 4/4 white Angel creature token with flying for each creature tapped this way.
        this.getSpellAbility().addEffect(new DevoutInvocationEffect());

    }

    public DevoutInvocation(final DevoutInvocation card) {
        super(card);
    }

    @Override
    public DevoutInvocation copy() {
        return new DevoutInvocation(this);
    }
}

class DevoutInvocationEffect extends OneShotEffect {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("untapped creatures you control");

    static {
        filter.add(Predicates.not(new TappedPredicate()));
    }

    public DevoutInvocationEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Tap any number of untapped creatures you control. Create a 4/4 white Angel creature token with flying for each creature tapped this way";
    }

    public DevoutInvocationEffect(DevoutInvocationEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int tappedAmount = 0;
            TargetPermanent target = new TargetPermanent(0, 1, filter, false);
            while (true && controller.canRespond()) {
                target.clearChosen();
                if (target.canChoose(source.getControllerId(), game)) {
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
                AngelToken angelToken = new AngelToken();
                angelToken.putOntoBattlefield(tappedAmount, game, source.getSourceId(), source.getControllerId());
            }
            return true;
        }
        return false;
    }

    @Override
    public DevoutInvocationEffect copy() {
        return new DevoutInvocationEffect(this);
    }

}
