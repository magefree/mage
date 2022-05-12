
package mage.cards.f;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author spjspj
 */
public final class FortunateFew extends CardImpl {

    public FortunateFew(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}{W}");

        // Choose a nonland permanent you don't control, then each other player chooses a nonland permanent they don't control that hasn't been chosen this way. Destroy all other nonland permanents.
        this.getSpellAbility().addEffect(new FortunateFewEffect());
    }

    private FortunateFew(final FortunateFew card) {
        super(card);
    }

    @Override
    public FortunateFew copy() {
        return new FortunateFew(this);
    }
}

class FortunateFewEffect extends OneShotEffect {

    public FortunateFewEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "Choose a nonland permanent you don't control, then each other player chooses a nonland permanent they don't control that hasn't been chosen this way. Destroy all other nonland permanents";
    }

    public FortunateFewEffect(FortunateFewEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Map<Permanent, Integer> chosenCards = new HashMap<>(2);
            int maxCount = 0;

            // Players each choose a legal permanent
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {

                    FilterNonlandPermanent filter = new FilterNonlandPermanent("a nonland permanent you don't control");
                    filter.add(Predicates.not(new ControllerIdPredicate(player.getId())));

                    for (Permanent chosenPerm : chosenCards.keySet()) {
                        filter.add(Predicates.not(new PermanentIdPredicate(chosenPerm.getId())));
                    }

                    Target target = new TargetNonlandPermanent(filter);
                    target.setNotTarget(true);
                    if (player.choose(Outcome.Exile, target, source, game)) {
                        Permanent permanent = game.getPermanent(target.getFirstTarget());
                        if (permanent != null) {
                            chosenCards.put(permanent, 1);
                            game.informPlayers(player.getLogName() + " has chosen: " + permanent.getName());
                        }
                    }
                }
            }

            for (Permanent permanent : game.getBattlefield().getActivePermanents(new FilterNonlandPermanent(), source.getControllerId(), source, game)) {
                if (!chosenCards.containsKey(permanent)) {
                    permanent.destroy(source, game, false);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public FortunateFewEffect copy() {
        return new FortunateFewEffect(this);
    }
}
