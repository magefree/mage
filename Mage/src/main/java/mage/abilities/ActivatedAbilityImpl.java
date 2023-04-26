package mage.abilities;

import mage.ApprovingObject;
import mage.MageObject;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.abilities.mana.ManaOptions;
import mage.cards.Card;
import mage.constants.*;
import mage.game.Game;
import mage.game.command.CommandObject;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public abstract class ActivatedAbilityImpl extends AbilityImpl implements ActivatedAbility {

    protected static class ActivationInfo {

        public int turnNum;
        public int activationCounter;
        public int totalActivations;

        public ActivationInfo(int turnNum, int activationCounter, int totalActivations) {
            this.turnNum = turnNum;
            this.activationCounter = activationCounter;
            this.totalActivations = totalActivations;
        }
    }

    protected int maxActivationsPerTurn = Integer.MAX_VALUE;
    protected int maxActivationsPerGame = Integer.MAX_VALUE;
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
        maxActivationsPerGame = ability.maxActivationsPerGame;
        condition = ability.condition;
    }

    public ActivatedAbilityImpl(Zone zone) {
        this(zone, null);
    }

    public ActivatedAbilityImpl(Zone zone, Effect effect) {
        super(AbilityType.ACTIVATED, zone);
        this.addEffect(effect);
    }

    public ActivatedAbilityImpl(Zone zone, Effect effect, ManaCosts cost) {
        super(AbilityType.ACTIVATED, zone);
        this.addEffect(effect);
        this.addManaCost(cost);
    }

    public ActivatedAbilityImpl(Zone zone, Effects effects, ManaCosts cost) {
        super(AbilityType.ACTIVATED, zone);
        if (effects != null) {
            for (Effect effect : effects) {
                this.addEffect(effect);
            }
        }
        this.addManaCost(cost);
    }

    public ActivatedAbilityImpl(Zone zone, Effect effect, Cost cost) {
        super(AbilityType.ACTIVATED, zone);
        this.addEffect(effect);
        this.addCost(cost);
    }

    public ActivatedAbilityImpl(Zone zone, Effect effect, Costs<Cost> costs) {
        super(AbilityType.ACTIVATED, zone);
        this.addEffect(effect);
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
        this.addCost(cost);
    }

    public ActivatedAbilityImpl(Zone zone, Effects effects, Costs<Cost> costs) {
        super(AbilityType.ACTIVATED, zone);
        if (effects != null) {
            for (Effect effect : effects) {
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
            case EACH_PLAYER:
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

    /**
     * Activated ability check, not spells. It contains costs and targets legality too.
     * <p>
     * WARNING, don't forget to call super.canActivate on override in card's code in most cases.
     *
     * @param playerId
     * @param game
     * @return
     */
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

        // timing check
        //20091005 - 602.5d/602.5e
        boolean asInstant;
        ApprovingObject approvingObject = game.getContinuousEffects()
                .asThough(sourceId,
                        AsThoughEffectType.ACTIVATE_AS_INSTANT,
                        this,
                        controllerId,
                        game);
        asInstant = approvingObject != null;
        asInstant |= (timing == TimingRule.INSTANT);
        if (!asInstant && !game.canPlaySorcery(playerId)) {
            return ActivationStatus.getFalse();
        }

        // targets and costs check
        if (!costs.canPay(this, this, playerId, game)
                || !canChooseTarget(game, playerId)) {
            return ActivationStatus.getFalse();
        }

        // all fine, can be activated
        // TODO: WTF, must be rework to remove data change in canActivate call
        //  (it can be called from any place by any player or card).
        //  game.inCheckPlayableState() can't be a help here cause some cards checking activating status,
        //  activatorId must be removed
        this.activatorId = playerId;
        return new ActivationStatus(true, approvingObject);
    }

    @Override
    public ManaOptions getMinimumCostToActivate(UUID playerId, Game game) {
        Player player = game.getPlayer(playerId);

        return getManaCostsToPay().getOptions(player.canPayLifeCost(this));
    }

    protected boolean controlsAbility(UUID playerId, Game game) {
        if (this.controllerId != null && this.controllerId.equals(playerId)) {
            return true;
        }
        MageObject mageObject = game.getObject(this.sourceId);
        if (mageObject instanceof CommandObject) {
            return ((CommandObject) mageObject).isControlledBy(playerId);
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

    @Override
    public ActivatedAbilityImpl setTiming(TimingRule timing) {
        this.timing = timing;
        return this;
    }

    protected boolean hasMoreActivationsThisTurn(Game game) {
        if (getMaxActivationsPerTurn(game) == Integer.MAX_VALUE && maxActivationsPerGame == Integer.MAX_VALUE) {
            return true;
        }
        ActivationInfo activationInfo = getActivationInfo(game);
        if (activationInfo == null) {
            return true;
        }
        if (activationInfo.totalActivations >= maxActivationsPerGame) {
            return false;
        }
        return activationInfo.turnNum != game.getTurnNum()
                || activationInfo.activationCounter < getMaxActivationsPerTurn(game);
    }

    @Override
    public boolean activate(Game game, boolean noMana) {
        if (!hasMoreActivationsThisTurn(game) || !super.activate(game, noMana)) {
            return false;
        }
        ActivationInfo activationInfo = getActivationInfo(game);
        if (activationInfo == null) {
            activationInfo = new ActivationInfo(game.getTurnNum(), 1, 0);
        } else if (activationInfo.turnNum != game.getTurnNum()) {
            activationInfo.turnNum = game.getTurnNum();
            activationInfo.activationCounter = 1;
        } else {
            activationInfo.activationCounter++;
        }
        activationInfo.totalActivations++;
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
        Integer totalActivations = (Integer) game.getState()
                .getValue(CardUtil.getCardZoneString("totalActivations" + originalId, sourceId, game));
        if (turnNum == null || activationCount == null || totalActivations == null) {
            return null;
        }
        return new ActivationInfo(turnNum, activationCount, totalActivations);
    }

    protected void setActivationInfo(ActivationInfo activationInfo, Game game) {
        game.getState().setValue(CardUtil
                .getCardZoneString("activationsTurn" + originalId, sourceId, game), activationInfo.turnNum);
        game.getState().setValue(CardUtil
                .getCardZoneString("activationsCount" + originalId, sourceId, game), activationInfo.activationCounter);
        game.getState().setValue(CardUtil
                .getCardZoneString("totalActivations" + originalId, sourceId, game), activationInfo.totalActivations);
    }

    @Override
    public ActivatedAbilityImpl setCondition(Condition condition) {
        this.condition = condition;
        return this;
    }
}
