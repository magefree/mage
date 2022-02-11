package mage.abilities.mana;

import mage.Mana;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.mana.ManaEffect;
import mage.constants.*;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;

import java.util.*;

/**
 * @author BetaSteward_at_googlemail.com
 */
public abstract class ActivatedManaAbilityImpl extends ActivatedAbilityImpl implements ManaAbility {

    protected List<Mana> netMana = new ArrayList<>();
    protected boolean undoPossible;
    protected boolean poolDependant;

    public ActivatedManaAbilityImpl(Zone zone, ManaEffect effect, Cost cost) {
        super(AbilityType.MANA, zone);
        this.usesStack = false;
        this.undoPossible = true;
        if (effect != null) {
            this.addEffect(effect);
        }
        if (cost != null) {
            this.addCost(cost);
        }
    }

    public ActivatedManaAbilityImpl(final ActivatedManaAbilityImpl ability) {
        super(ability);
        this.netMana.addAll(ability.netMana);
        this.undoPossible = ability.undoPossible;
        this.poolDependant = ability.poolDependant;
    }

    @Override
    public ActivationStatus canActivate(UUID playerId, Game game) {
        // check if player is in the process of playing spell costs and they are no longer allowed to use
        // activated mana abilities (e.g. because they started to use improvise or convoke)
        if (!game.getStack().isEmpty()) {
            StackObject stackObject = game.getStack().getFirst();
            if (stackObject instanceof Spell) {
                switch (((Spell) stackObject).getCurrentActivatingManaAbilitiesStep()) {
                    case BEFORE:
                    case NORMAL:
                        break;
                    case AFTER:
                        return ActivationStatus.getFalse();
                }
            }
        }

        return super.canActivate(playerId, game);
    }

    /**
     * Used to check the possible mana production to determine which spells
     * and/or abilities can be used. (player.getPlayable()).
     * <p>
     * Also used for deck's card mana cycle with game = null (score system, etc)
     *
     * @param game
     * @return
     */
    @Override
    public List<Mana> getNetMana(Game game) {
        if (netMana.isEmpty() || (game != null && game.inCheckPlayableState())) {
            List<Mana> dynamicNetMana = new ArrayList<>();
            for (Effect effect : getEffects()) {
                if (effect instanceof ManaEffect) {
                    List<Mana> effectNetMana = ((ManaEffect) effect).getNetMana(game, this);
                    if (effectNetMana != null) {
                        dynamicNetMana.addAll(effectNetMana);
                    }
                }
            }
            return dynamicNetMana;
        }
        List<Mana> netManaCopy = new ArrayList<>();
        for (Mana mana : netMana) {
            netManaCopy.add(mana.copy());
        }
        return netManaCopy;
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
        return !getNetMana(game).isEmpty();
    }

    /**
     * Is it allowed to undo the mana creation. It's e.g. not allowed if some
     * game revealing information is related (like reveal the top card of the
     * library)
     * <p>
     * TODO: it helps with single mana activate for mana pool, but will not work
     * while activates on paying for casting (e.g. user can cheats to see next
     * draw card)
     *
     * @return
     */
    public boolean isUndoPossible() {
        return undoPossible;
    }

    public void setUndoPossible(boolean undoPossible) {
        this.undoPossible = undoPossible;
    }

    @Override
    public boolean isPoolDependant() {
        return poolDependant;
    }

    @Override
    public ActivatedManaAbilityImpl setPoolDependant(boolean poolDependant) {
        this.poolDependant = poolDependant;
        return this;
    }

}
