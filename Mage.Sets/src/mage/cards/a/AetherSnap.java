
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.Counter;
import mage.counters.Counters;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class AetherSnap extends CardImpl {

    public AetherSnap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{B}{B}");

        // Remove all counters from all permanents and exile all tokens.
        this.getSpellAbility().addEffect(new AetherSnapEffect());
    }

    public AetherSnap(final AetherSnap card) {
        super(card);
    }

    @Override
    public AetherSnap copy() {
        return new AetherSnap(this);
    }
}

class AetherSnapEffect extends OneShotEffect {

    public AetherSnapEffect() {
        super(Outcome.Benefit);
        this.staticText = "Remove all counters from all permanents and exile all tokens";
    }

    public AetherSnapEffect(final AetherSnapEffect effect) {
        super(effect);
    }

    @Override
    public AetherSnapEffect copy() {
        return new AetherSnapEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (Permanent permanent : game.getBattlefield().getActivePermanents(new FilterPermanent(), controller.getId(), source.getSourceId(), game)) {
                if (permanent instanceof PermanentToken) {
                    controller.moveCardToExileWithInfo(permanent, null, "", source.getSourceId(), game, Zone.BATTLEFIELD, true);
                } else if (!permanent.getCounters(game).isEmpty()) {
                    Counters counters = permanent.getCounters(game).copy();
                    for (Counter counter : counters.values()) {
                        permanent.removeCounters(counter, game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
