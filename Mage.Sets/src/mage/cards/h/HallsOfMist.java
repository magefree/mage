
package mage.cards.h;

import java.util.Set;
import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.watchers.common.AttackedLastTurnWatcher;

/**
 *
 * @author L_J
 */
public final class HallsOfMist extends CardImpl {

    public HallsOfMist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Cumulative upkeep-Pay {1}.
        this.addAbility(new CumulativeUpkeepAbility(new GenericManaCost(1)));

        // Creatures that attacked during their controller's last turn can't attack.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantAttackIfAttackedLastTurnAllEffect()), new AttackedLastTurnWatcher());
    }

    public HallsOfMist(final HallsOfMist card) {
        super(card);
    }

    @Override
    public HallsOfMist copy() {
        return new HallsOfMist(this);
    }
}

class CantAttackIfAttackedLastTurnAllEffect extends RestrictionEffect {

    public CantAttackIfAttackedLastTurnAllEffect() {
        super(Duration.WhileOnBattlefield);
        this.staticText = "Creatures that attacked during their controller's last turn can't attack";
    }

    public CantAttackIfAttackedLastTurnAllEffect(final CantAttackIfAttackedLastTurnAllEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.isCreature();
    }

    @Override
    public boolean canAttack(Permanent attacker, UUID defenderId, Ability source, Game game) {
        AttackedLastTurnWatcher watcher = (AttackedLastTurnWatcher) game.getState().getWatchers().get(AttackedLastTurnWatcher.class.getSimpleName());
        if (watcher != null) {
            Set<MageObjectReference> attackingCreatures = watcher.getAttackedLastTurnCreatures(attacker.getControllerId());
            MageObjectReference mor = new MageObjectReference(attacker, game);
            if (attackingCreatures.contains(mor)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public CantAttackIfAttackedLastTurnAllEffect copy() {
        return new CantAttackIfAttackedLastTurnAllEffect(this);
    }

}
