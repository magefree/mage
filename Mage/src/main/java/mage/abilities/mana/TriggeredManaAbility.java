package mage.abilities.mana;

import mage.Mana;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.mana.ManaEffect;
import mage.constants.AbilityType;
import mage.constants.Zone;
import mage.game.Game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mage.constants.ManaType;

/**
 * see 20110715 - 605.1b
 *
 * @author BetaSteward_at_googlemail.com
 */
public abstract class TriggeredManaAbility extends TriggeredAbilityImpl implements ManaAbility {

    protected List<Mana> netMana = new ArrayList<>();
    protected boolean poolDependant;

    public TriggeredManaAbility(Zone zone, ManaEffect effect) {
        this(zone, effect, false);
    }

    public TriggeredManaAbility(Zone zone, ManaEffect effect, boolean optional) {
        super(zone, effect, optional);
        this.usesStack = false;
        this.abilityType = AbilityType.MANA;

    }

    protected TriggeredManaAbility(final TriggeredManaAbility ability) {
        super(ability);
        this.netMana.addAll(ability.netMana);
        this.poolDependant = ability.poolDependant;
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
            List<Mana> netMana = new ArrayList<>();
            for (Effect effect : getEffects()) {
                if (effect instanceof ManaEffect) {
                    netMana.addAll(((ManaEffect) effect).getNetMana(game, this));
                }
            }
            return netMana;
        }
        return new ArrayList<>(netMana);
    }

    @Override
    public List<Mana> getNetMana(Game game, Mana possibleManaInPool) {
        if (isPoolDependant()) {
            List<Mana> poolDependantNetMana = new ArrayList<>();
            for (Effect effect : getEffects()) {
                if (effect instanceof ManaEffect) {
                    List<Mana> effectNetMana = ((ManaEffect) effect).getNetMana(game, possibleManaInPool, this);
                    if (effectNetMana != null) {
                        poolDependantNetMana.addAll(effectNetMana);
                    }
                }
            }
            return poolDependantNetMana;
        }
        return getNetMana(game);
    }

    @Override
    public Set<ManaType> getProducableManaTypes(Game game) {
        Set<ManaType> manaTypes = new HashSet<>();
        for (Effect effect : getEffects()) {
            if (effect instanceof ManaEffect) {
                manaTypes.addAll(((ManaEffect) effect).getProducableManaTypes(game, this));
            }
        }
        return manaTypes;
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

    @Override
    public boolean isPoolDependant() {
        return poolDependant;
    }

    @Override
    public TriggeredManaAbility setPoolDependant(boolean poolDependant) {
        this.poolDependant = poolDependant;
        return this;
    }

}
