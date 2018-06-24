package mage.abilities.mana;

import java.util.ArrayList;
import java.util.List;
import mage.Mana;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ManaEffect;
import mage.constants.AbilityType;
import mage.constants.Zone;
import mage.game.Game;

/**
 * see 20110715 - 605.1b
 *
 * @author BetaSteward_at_googlemail.com
 */
public abstract class TriggeredManaAbility extends TriggeredAbilityImpl implements ManaAbility {

    protected List<Mana> netMana = new ArrayList<>();

    public TriggeredManaAbility(Zone zone, ManaEffect effect) {
        this(zone, effect, false);
    }

    public TriggeredManaAbility(Zone zone, ManaEffect effect, boolean optional) {
        super(zone, effect, optional);
        this.usesStack = false;
        this.abilityType = AbilityType.MANA;

    }

    public TriggeredManaAbility(final TriggeredManaAbility ability) {
        super(ability);
        this.netMana.addAll(ability.netMana);
    }

    /**
     * Used to check the possible mana production to determine which spells
     * and/or abilities can be used. (player.getPlayable()).
     *
     * @param game
     * @return
     */
    @Override
    public List<Mana> getNetMana(Game game) {
        if (game != null) {
            ArrayList<Mana> newNetMana = new ArrayList<>();
            for (Effect effect : getEffects()) {
                if (effect instanceof ManaEffect) {
                    newNetMana.addAll(((ManaEffect) effect).getNetMana(game, this));
                }
            }
            return newNetMana;
        }
        return new ArrayList<Mana>(netMana);
    }

    /**
     * Used to check if the ability itself defines mana types it can produce.
     *
     * @return
     */
    @Override
    public boolean definesMana(Game game) {
        return !netMana.isEmpty();
    }
}
