

package mage.player.ai.simulators;

import java.io.Serializable;
import java.util.UUID;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.game.permanent.Permanent;

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

    public CreatureSimulator(Permanent permanent) {
        this.id = permanent.getId();
        this.damage = permanent.getDamage();
        this.power = permanent.getPower().getValue();
        this.toughness = permanent.getToughness().getValue();
        this.hasDoubleStrike = permanent.getAbilities().containsKey(DoubleStrikeAbility.getInstance().getId());
        this.hasFirstStrike = permanent.getAbilities().containsKey(FirstStrikeAbility.getInstance().getId());
        this.hasTrample = permanent.getAbilities().containsKey(TrampleAbility.getInstance().getId());
    }

    public boolean isDead() {
        return damage >= toughness;
    }

    public int getLethalDamage() {
        return toughness - damage;
    }
}
