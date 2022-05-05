
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author TheElk801
 */
public final class ImperialEdict extends CardImpl {

    public ImperialEdict(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Target opponent chooses a creature they control. Destroy it.
        this.getSpellAbility().addEffect(new ImperialEdictEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private ImperialEdict(final ImperialEdict card) {
        super(card);
    }

    @Override
    public ImperialEdict copy() {
        return new ImperialEdict(this);
    }
}

class ImperialEdictEffect extends OneShotEffect {

    ImperialEdictEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target opponent chooses a creature they control. Destroy it.";
    }

    ImperialEdictEffect(final ImperialEdictEffect effect) {
        super(effect);
    }

    @Override
    public ImperialEdictEffect copy() {
        return new ImperialEdictEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player == null) {
            return false;
        }
        FilterCreaturePermanent filter = new FilterCreaturePermanent("creature you control");
        filter.add(new ControllerIdPredicate(player.getId()));
        Target target = new TargetPermanent(1, 1, filter, true);
        if (target.canChoose(player.getId(), source, game)) {
            while (!target.isChosen() && target.canChoose(player.getId(), source, game) && player.canRespond()) {
                player.chooseTarget(Outcome.DestroyPermanent, target, source, game);
            }
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent != null) {
                permanent.destroy(source, game, false);
            }
        }
        return true;
    }
}
