
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.watchers.common.CastSpellYourLastTurnWatcher;
import mage.watchers.common.PermanentsEnteredBattlefieldYourLastTurnWatcher;

/**
 *
 * @author spjspj
 */
public final class Arboria extends CardImpl {

    public Arboria(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}{G}");

        addSuperType(SuperType.WORLD);

        // Creatures can't attack a player unless that player cast a spell or put a nontoken permanent onto the battlefield during their last turn.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ArboriaEffect()), new PermanentsEnteredBattlefieldYourLastTurnWatcher());
    }

    public Arboria(final Arboria card) {
        super(card);
    }

    @Override
    public Arboria copy() {
        return new Arboria(this);
    }
}

class ArboriaEffect extends RestrictionEffect {

    public ArboriaEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "Creatures can't attack a player unless that player cast a spell or put a nontoken permanent onto the battlefield during their last turn";
    }

    public ArboriaEffect(final ArboriaEffect effect) {
        super(effect);
    }

    @Override
    public ArboriaEffect copy() {
        return new ArboriaEffect(this);
    }

    @Override
    public boolean canAttack(Permanent attacker, UUID defenderId, Ability source, Game game) {
        CastSpellYourLastTurnWatcher watcher = (CastSpellYourLastTurnWatcher) game.getState().getWatchers().get(CastSpellYourLastTurnWatcher.class.getSimpleName());
        if (watcher != null && watcher.getAmountOfSpellsCastOnPlayersTurn(defenderId) > 0) {
            return true;
        }

        PermanentsEnteredBattlefieldYourLastTurnWatcher watcher2
                = (PermanentsEnteredBattlefieldYourLastTurnWatcher) game.getState().getWatchers().get(PermanentsEnteredBattlefieldYourLastTurnWatcher.class.getSimpleName());

        if (watcher2 != null && watcher2.getPermanentsEnteringOnPlayersLastTurn(game, defenderId) != null) {
            for (Permanent permanent : watcher2.getPermanentsEnteringOnPlayersLastTurn(game, defenderId)) {
                if (permanent != null && !(permanent instanceof PermanentToken)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return true;
    }
}
