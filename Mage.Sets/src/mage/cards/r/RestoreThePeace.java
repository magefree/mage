
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.watchers.common.SourceDidDamageWatcher;

/**
 * @author LevelX2
 */
public final class RestoreThePeace extends CardImpl {

    public RestoreThePeace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}{U}");


        // Return each creature that dealt damage this turn to its owner's hand.
        this.getSpellAbility().addEffect(new RestoreThePeaceEffect());
        this.getSpellAbility().addWatcher(new SourceDidDamageWatcher());

    }

    public RestoreThePeace(final RestoreThePeace card) {
        super(card);
    }

    @Override
    public RestoreThePeace copy() {
        return new RestoreThePeace(this);
    }
}

class RestoreThePeaceEffect extends OneShotEffect {

    public RestoreThePeaceEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Return each creature that dealt damage this turn to its owner's hand";
    }

    public RestoreThePeaceEffect(final RestoreThePeaceEffect effect) {
        super(effect);
    }

    @Override
    public RestoreThePeaceEffect copy() {
        return new RestoreThePeaceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        SourceDidDamageWatcher watcher = game.getState().getWatcher(SourceDidDamageWatcher.class);
        if (watcher != null) {
            for (UUID permId : watcher.damageSources) {
                Permanent perm = game.getPermanent(permId);
                if (perm != null) {
                    perm.moveToZone(Zone.HAND, source.getSourceId(), game, true);
                }
            }
            return true;
        }
        return false;
    }
}
