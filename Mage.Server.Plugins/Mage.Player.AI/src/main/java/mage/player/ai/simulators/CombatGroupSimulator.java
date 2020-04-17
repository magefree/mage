package mage.player.ai.simulators;

import mage.game.Game;
import mage.game.permanent.Permanent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class CombatGroupSimulator implements Serializable {
    public List<CreatureSimulator> attackers = new ArrayList<>();
    public List<CreatureSimulator> blockers = new ArrayList<>();
    public UUID defenderId;
    public boolean defenderIsPlaneswalker;
    public int unblockedDamage;
    private CreatureSimulator attacker;

    public CombatGroupSimulator(UUID defenderId, List<UUID> attackers, List<UUID> blockers, Game game) {
        this.defenderId = defenderId;
        for (UUID attackerId: attackers) {
            Permanent permanent = game.getPermanent(attackerId);
            this.attackers.add(new CreatureSimulator(permanent));
        }
        for (UUID blockerId: blockers) {
            Permanent permanent = game.getPermanent(blockerId);
            this.blockers.add(new CreatureSimulator(permanent));
        }
        //NOTE:  assumes no banding
        attacker = this.attackers.get(0);
    }

    private boolean hasFirstOrDoubleStrike() {
        for (CreatureSimulator creature: attackers) {
            if (creature.hasDoubleStrike || creature.hasFirstStrike)
                return true;
        }
        for (CreatureSimulator creature: blockers) {
            if (creature.hasDoubleStrike || creature.hasFirstStrike)
                return true;
        }
        return false;
    }

    public boolean canBlock(Permanent blocker, Game game) {
        return blocker.canBlock(attacker.id, game);
    }

    public void simulateCombat(Game game) {
        unblockedDamage = 0;

        if (hasFirstOrDoubleStrike())
            assignDamage(true, game);
        assignDamage(false, game);
    }

    private void assignDamage(boolean first, Game game) {
        if (blockers.isEmpty()) {
            if (canDamage(attacker, first))
                unblockedDamage += attacker.power;
        }
        else if (blockers.size() == 1) {
            CreatureSimulator blocker = blockers.get(0);
            if (canDamage(attacker, first)) {
                if (attacker.hasTrample) {
                    int lethalDamage = blocker.getLethalDamage(game);
                    if (attacker.power > lethalDamage) {
                        blocker.damage += lethalDamage;
                        unblockedDamage += attacker.power - lethalDamage;
                    }
                    else {
                        blocker.damage += attacker.power;
                    }
                }
            }
            if (canDamage(blocker, first)) {
                attacker.damage += blocker.power;
            }
        }
        else {
            int damage = attacker.power;
            for (CreatureSimulator blocker: blockers) {
                if (damage > 0 && canDamage(attacker, first)) {
                    int lethalDamage = blocker.getLethalDamage(game);
                    if (damage > lethalDamage) {
                        blocker.damage += lethalDamage;
                        damage -= lethalDamage;
                    }
                    else {
                        blocker.damage += damage;
                        damage = 0;
                    }
                }
                if (canDamage(blocker, first)) {
                    attacker.damage += blocker.power;
                }
            }
            if (damage > 0) {
                if (attacker.hasTrample) {
                    unblockedDamage += damage;
                }
                else {
                    blockers.get(0).damage += damage;
                }
            }
        }
    }

    private boolean canDamage(CreatureSimulator creature, boolean first) {
        if (first && (creature.hasFirstStrike || creature.hasDoubleStrike))
            return true;
        if (!first && (!creature.hasFirstStrike || creature.hasDoubleStrike))
            return true;
        return false;
    }

    /**
     * returns 3   attacker survives blockers destroyed
     * returns 2   both destroyed
     * returns 1   both survive
     * returns 0   attacker destroyed blockers survive
     *
     * @return int
     */
    public int evaluateCombat() {
        int survivingBlockers = 0;
        for (CreatureSimulator blocker: blockers) {
            if (blocker.damage < blocker.toughness)
                survivingBlockers++;
        }
        if (attacker.isDead()) {
            if (survivingBlockers > 0) {
                return 0;
            }
            return 2;
        }
        else {
            if (survivingBlockers > 0) {
                return 1;
            }
            return 3;
        }
    }
}
