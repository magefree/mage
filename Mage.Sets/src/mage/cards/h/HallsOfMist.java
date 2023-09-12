package mage.cards.h;

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

import java.util.Set;
import java.util.UUID;

/**
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

    private HallsOfMist(final HallsOfMist card) {
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

    private CantAttackIfAttackedLastTurnAllEffect(final CantAttackIfAttackedLastTurnAllEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.isCreature(game);
    }

    @Override
    public boolean canAttack(Permanent attacker, UUID defenderId, Ability source, Game game, boolean canUseChooseDialogs) {
        AttackedLastTurnWatcher watcher = game.getState().getWatcher(AttackedLastTurnWatcher.class);
        if (watcher != null) {
            Set<MageObjectReference> attackingCreatures = watcher.getAttackedLastTurnCreatures(attacker.getControllerId());
            MageObjectReference mor = new MageObjectReference(attacker, game);
            return !attackingCreatures.contains(mor);
        }
        return true;
    }

    @Override
    public CantAttackIfAttackedLastTurnAllEffect copy() {
        return new CantAttackIfAttackedLastTurnAllEffect(this);
    }

}
