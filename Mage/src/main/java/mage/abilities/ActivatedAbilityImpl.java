package mage.abilities;

import mage.MageObject;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.PhyrexianManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.abilities.mana.ManaOptions;
import mage.cards.Card;
import mage.constants.*;
import mage.game.Game;
import mage.game.command.Commander;
import mage.game.command.Emblem;
import mage.game.command.Plane;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.UUID;
import mage.ApprovingObject;

/**
 * @author BetaSteward_at_googlemail.com
 */
public abstract class ActivatedAbilityImpl extends AbilityImpl implements ActivatedAbility {

    protected static class ActivationInfo {

        public int turnNum;
        public int activationCounter;

        public ActivationInfo(int turnNum, int activationCounter) {
            this.turnNum = turnNum;
            this.activationCounter = activationCounter;
        }
    }

    protected int maxActivationsPerTurn = Integer.MAX_VALUE;
    protected Condition condition;
    protected TimingRule timing = TimingRule.INSTANT;
    protected TargetController mayActivate = TargetController.YOU;
    protected UUID activatorId;

    protected ActivatedAbilityImpl(AbilityType abilityType, Zone zone) {
        super(abilityType, zone);
    }

    public ActivatedAbilityImpl(final ActivatedAbilityImpl ability) {
        super(ability);
        timing = ability.timing;
        mayActivate = ability.mayActivate;
        activatorId = ability.activatorId;
        maxActivationsPerTurn = ability.maxActivationsPerTurn;
        condition = ability.condition;
    }

    public ActivatedAbilityImpl(Zone zone) {
        this(zone, null);
    }

    public ActivatedAbilityImpl(Zone zone, Effect effect) {
        super(AbilityType.ACTIVATED, zone);
        if (effect != null) {
            this.addEffect(effect);
        }
    }

    public ActivatedAbilityImpl(Zone zone, Effect effect, ManaCosts cost) {
        super(AbilityType.ACTIVATED, zone);
        if (effect != null) {
            this.addEffect(effect);
        }
        if (cost != null) {
            this.addManaCost(cost);
        }
    }

    public ActivatedAbilityImpl(Zone zone, Effects effects, ManaCosts cost) {
        super(AbilityType.ACTIVATED, zone);
        if (effects != null) {
            for (Effect effect : effects) {
                this.addEffect(effect);
            }
        }
        if (cost != null) {
            this.addManaCost(cost);
        }
    }

    public ActivatedAbilityImpl(Zone zone, Effect effect, Cost cost) {
        super(AbilityType.ACTIVATED, zone);
        if (effect != null) {
            this.addEffect(effect);
        }
        if (cost != null) {
            if (cost instanceof PhyrexianManaCost) {
                this.addManaCost((PhyrexianManaCost) cost);
            } else {
                this.addCost(cost);
            }
        }
    }

    public ActivatedAbilityImpl(Zone zone, Effect effect, Costs<Cost> costs) {
        super(AbilityType.ACTIVATED, zone);
        if (effect != null) {
            this.addEffect(effect);
        }
        if (costs != null) {
            for (Cost cost : costs) {
                this.addCost(cost);
            }
        }
    }

    public ActivatedAbilityImpl(Zone zone, Effects effects, Cost cost) {
        super(AbilityType.ACTIVATED, zone);
        if (effects != null) {
            for (Effect effect : effects) {
                this.addEffect(effect);
            }
        }
        if (cost != null) {
            this.addCost(cost);
        }
    }

    public ActivatedAbilityImpl(Zone zone, Effects effects, Costs<Cost> costs) {
        super(AbilityType.ACTIVATED, zone);
        for (Effect effect : effects) {
            if (effect != null) {
                this.addEffect(effect);
            }
        }
        if (costs != null) {
            for (Cost cost : costs) {
                this.addCost(cost);
            }
        }
    }

    @Override
    public abstract ActivatedAbilityImpl copy();

    protected boolean checkTargetController(UUID playerId, Game game) {
        switch (mayActivate) {
            case ANY:
                return true;
            case ACTIVE:
                return game.getActivePlayerId() == playerId;
            case NOT_YOU:
                return !controlsAbility(playerId, game);
            case TEAM:
                return !game.getPlayer(controllerId).hasOpponent(playerId, game);
            case OPPONENT:
                return game.getPlayer(controllerId).hasOpponent(playerId, game);
            case OWNER:
                Permanent permanent = game.getPermanent(getSourceId());
                return permanent != null && permanent.isOwnedBy(playerId);
            case YOU:
                return controlsAbility(playerId, game);
            case CONTROLLER_ATTACHED_TO:
                Permanent enchantment = game.getPermanent(getSourceId());
                if (enchantment == null || enchantment.getAttachedTo() == null) {
                    return false;
                }
                Permanent enchanted = game.getPermanent(enchantment.getAttachedTo());
                return enchanted != null && enchanted.isControlledBy(playerId);
        }
        return true;
    }

    @Override
    public ActivationStatus canActivate(UUID playerId, Game game) {
        //20091005 - 602.2
        if (!(hasMoreActivationsThisTurn(game)
                && (condition == null
                || condition.apply(game, this)))) {
            return ActivationStatus.getFalse();
        }
        if (!this.checkTargetController(playerId, game)) {
            return ActivationStatus.getFalse();
        }
        //20091005 - 602.5d/602.5e
        ApprovingObject approvingObject = game.getContinuousEffects()
                .asThough(sourceId,
                        AsThoughEffectType.ACTIVATE_AS_INSTANT,
                        this,
                        controllerId,
                        game);
        if (timing == TimingRule.INSTANT
                || game.canPlaySorcery(playerId)
                || null != approvingObject) {
            if (costs.canPay(this, sourceId, playerId, game)
                    && canChooseTarget(game)) {
                this.activatorId = playerId;
                return new ActivationStatus(true, approvingObject);
            }
        }
        return ActivationStatus.getFalse();
    }

    @Override
    public ManaOptions getMinimumCostToActivate(UUID playerId, Game game) {
        return getManaCostsToPay().getOptions();
    }

    protected boolean controlsAbility(UUID playerId, Game game) {
        if (this.controllerId != null && this.controllerId.equals(playerId)) {
            return true;
        }
        MageObject mageObject = game.getObject(this.sourceId);
        if (mageObject instanceof Emblem) {
            return ((Emblem) mageObject).isControlledBy(playerId);
        } else if (mageObject instanceof Plane) {
            return ((Plane) mageObject).isControlledBy(playerId);
        } else if (mageObject instanceof Commander) {
            return ((Commander) mageObject).isControlledBy(playerId);
        } else if (game.getState().getZone(this.sourceId) != Zone.BATTLEFIELD) {
            return ((Card) mageObject).isOwnedBy(playerId);
        }
        return false;
    }

    @Override
    public void setMayActivate(TargetController mayActivate) {
        this.mayActivate = mayActivate;
    }

    public UUID getActivatorId() {
        return this.activatorId;
    }

    public TimingRule getTiming() {
        return timing;
    }

    public void setTiming(TimingRule timing) {
        this.timing = timing;
    }

    protected boolean hasMoreActivationsThisTurn(Game game) {
        if (getMaxActivationsPerTurn(game) == Integer.MAX_VALUE) {
            return true;
        }
        ActivationInfo activationInfo = getActivationInfo(game);
        return activationInfo == null
                || activationInfo.turnNum != game.getTurnNum()
                || activationInfo.activationCounter < getMaxActivationsPerTurn(game);
    }

    @Override
    public boolean activate(Game game, boolean noMana) {
        if (!hasMoreActivationsThisTurn(game) || !super.activate(game, noMana)) {
            return false;
        }
        ActivationInfo activationInfo = getActivationInfo(game);
        if (activationInfo == null) {
            activationInfo = new ActivationInfo(game.getTurnNum(), 1);
        } else if (activationInfo.turnNum != game.getTurnNum()) {
            activationInfo.turnNum = game.getTurnNum();
            activationInfo.activationCounter = 1;
        } else {
            activationInfo.activationCounter++;
        }
        setActivationInfo(activationInfo, game);
        return true;
    }

    @Override
    public void setMaxActivationsPerTurn(int maxActivationsPerTurn) {
        this.maxActivationsPerTurn = maxActivationsPerTurn;
    }

    @Override
    public int getMaxActivationsPerTurn(Game game) {
        return maxActivationsPerTurn;
    }

    protected ActivationInfo getActivationInfo(Game game) {
        Integer turnNum = (Integer) game.getState()
                .getValue(CardUtil.getCardZoneString("activationsTurn" + originalId, sourceId, game));
        Integer activationCount = (Integer) game.getState()
                .getValue(CardUtil.getCardZoneString("activationsCount" + originalId, sourceId, game));
        if (turnNum == null || activationCount == null) {
            return null;
        }
        return new ActivationInfo(turnNum, activationCount);
    }

    protected void setActivationInfo(ActivationInfo activationInfo, Game game) {
        game.getState().setValue(CardUtil
                .getCardZoneString("activationsTurn" + originalId, sourceId, game), activationInfo.turnNum);
        game.getState().setValue(CardUtil
                .getCardZoneString("activationsCount" + originalId, sourceId, game), activationInfo.activationCounter);
    }
}
