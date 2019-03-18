package mage.abilities.mana;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.Mana;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ManaEffect;
import mage.constants.AbilityType;
import mage.constants.AsThoughEffectType;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.game.Game;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public abstract class ActivatedManaAbilityImpl extends ActivatedAbilityImpl implements ManaAbility {

    protected List<Mana> netMana = new ArrayList<>();
    protected boolean undoPossible;

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
    }

    @Override
    public ActivationStatus canActivate(UUID playerId, Game game) {
        if (!super.hasMoreActivationsThisTurn(game) || !(condition == null || condition.apply(game, this))) {
            return ActivationStatus.getFalse();
        }
        if (!controlsAbility(playerId, game)) {
            return ActivationStatus.getFalse();
        }
        if (timing == TimingRule.SORCERY
                && !game.canPlaySorcery(playerId)
                && null == game.getContinuousEffects().asThough(sourceId, AsThoughEffectType.ACTIVATE_AS_INSTANT, this, controllerId, game)) {
            return ActivationStatus.getFalse();
        }
        // check if player is in the process of playing spell costs and he is no longer allowed to use activated mana abilities (e.g. because he started to use improvise)
        //20091005 - 605.3a
        return new ActivationStatus(costs.canPay(this, sourceId, controllerId, game), null);

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
        if (netMana.isEmpty()) {
            ArrayList<Mana> dynamicNetMana = new ArrayList<>();
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
        ArrayList<Mana> netManaCopy = new ArrayList<>();
        for (Mana mana : netMana) {
            netManaCopy.add(mana.copy());
        }
        return netManaCopy;
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
     *
     * @return
     */
    public boolean isUndoPossible() {
        return undoPossible;
    }

    public void setUndoPossible(boolean undoPossible) {
        this.undoPossible = undoPossible;
    }

}
