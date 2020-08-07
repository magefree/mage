package mage.player.ai.simulators;

import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class CreatureSimulator implements Serializable {
    public UUID id;
    public int damage;
    public int power;
    public int toughness;
    public boolean hasFirstStrike;
    public boolean hasDoubleStrike;
    public boolean hasTrample;
    public Permanent permanent;

    public CreatureSimulator(Permanent permanent) {
        this.id = permanent.getId();
        this.damage = permanent.getDamage();
        this.power = permanent.getPower().getValue();
        this.toughness = permanent.getToughness().getValue();
        this.hasDoubleStrike = permanent.getAbilities().containsKey(DoubleStrikeAbility.getInstance().getId());
        this.hasFirstStrike = permanent.getAbilities().containsKey(FirstStrikeAbility.getInstance().getId());
        this.hasTrample = permanent.getAbilities().containsKey(TrampleAbility.getInstance().getId());
        this.permanent = permanent;
    }

    public boolean isDead() {
        return damage >= toughness;
    }

    public int getLethalDamage(Game game) {
        List<FilterCreaturePermanent> usePowerInsteadOfToughnessForDamageLethalityFilters = game.getState().getActivePowerInsteadOfToughnessForDamageLethalityFilters();
        /*
         * for handling Zilortha, Strength Incarnate:
         * 2020-04-17
         * Any time the game is checking whether damage is lethal or if a creature should be destroyed for having lethal damage marked on it, use the power of your creatures rather than their toughness to check the damage against. This includes being assigned trample damage, damage from Flame Spill, and so on.
         */
        boolean usePowerInsteadOfToughnessForDamageLethality = usePowerInsteadOfToughnessForDamageLethalityFilters.stream()
                .anyMatch(filter -> filter.match(permanent, game));
        int lethalDamageThreshold = usePowerInsteadOfToughnessForDamageLethality ?
                // Zilortha, Strength Incarnate, 2020-04-17: A creature with 0 power isn’t destroyed unless it has at least 1 damage marked on it.
                Math.max(power, 1) : toughness;
        return Math.max(lethalDamageThreshold - damage, 0);
    }
}
