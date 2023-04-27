
package mage.abilities.effects;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public abstract class PayCostToAttackBlockEffectImpl extends ReplacementEffectImpl implements PayCostToAttackBlockEffect {

    public enum RestrictType {

        ATTACK("attack"),
        ATTACK_AND_BLOCK("attack or block"),
        BLOCK("block");

        private final String text;

        RestrictType(String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    protected final Cost cost;
    protected final ManaCosts manaCosts;
    protected final RestrictType restrictType;

    public PayCostToAttackBlockEffectImpl(Duration duration, Outcome outcome, RestrictType restrictType) {
        super(duration, outcome, false);
        this.restrictType = restrictType;
        this.cost = null;
        this.manaCosts = null;
    }

    public PayCostToAttackBlockEffectImpl(Duration duration, Outcome outcome, RestrictType restrictType, Cost cost) {
        super(duration, outcome, false);
        this.restrictType = restrictType;
        if (cost instanceof ManaCosts) {
            this.cost = null;
            this.manaCosts = (ManaCosts) cost;
        }
        else {
            this.cost = cost;
            this.manaCosts = null;
        }
    }

    public PayCostToAttackBlockEffectImpl(final PayCostToAttackBlockEffectImpl effect) {
        super(effect);
        if (effect.cost != null) {
            this.cost = effect.cost.copy();
        } else {
            this.cost = null;
        }
        if (effect.manaCosts != null) {
            this.manaCosts = effect.manaCosts.copy();
        } else {
            this.manaCosts = null;
        }
        this.restrictType = effect.restrictType;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        switch (restrictType) {
            case ATTACK:
                return event.getType() == GameEvent.EventType.DECLARE_ATTACKER;
            case BLOCK:
                return event.getType() == GameEvent.EventType.DECLARE_BLOCKER;
            case ATTACK_AND_BLOCK:
                return event.getType() == GameEvent.EventType.DECLARE_ATTACKER || event.getType() == GameEvent.EventType.DECLARE_BLOCKER;
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        ManaCosts attackBlockManaTax = getManaCostToPay(event, source, game);
        if (attackBlockManaTax != null) {
            return handleManaCosts(attackBlockManaTax.copy(), event, source, game);
        }
        Cost attackBlockOtherTax = getOtherCostToPay(event, source, game);
        if (attackBlockOtherTax != null) {
            return handleOtherCosts(attackBlockOtherTax.copy(), event, source, game);
        }
        return false;
    }

    private boolean handleManaCosts(ManaCosts attackBlockManaTax, GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getPlayerId());
        if (player != null) {
            String chooseText;
            if (event.getType() == GameEvent.EventType.DECLARE_ATTACKER) {
                chooseText = "Pay " + attackBlockManaTax.getText() + " to attack?";
            } else {
                chooseText = "Pay " + attackBlockManaTax.getText() + " to block?";
            }
            attackBlockManaTax.clearPaid();
            if (attackBlockManaTax.canPay(source, source, player.getId(), game)
                    && player.chooseUse(Outcome.Neutral, chooseText, source, game)) {
                if (attackBlockManaTax instanceof ManaCostsImpl) {
                    if (attackBlockManaTax.payOrRollback(source, game, source, event.getPlayerId())) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    private boolean handleOtherCosts(Cost attackBlockOtherTax, GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getPlayerId());
        if (player != null) {
            attackBlockOtherTax.clearPaid();
            if (attackBlockOtherTax.canPay(source, source, event.getPlayerId(), game)
                    && player.chooseUse(Outcome.Neutral,
                            attackBlockOtherTax.getText() + " to " + (event.getType() == GameEvent.EventType.DECLARE_ATTACKER ? "attack?" : "block?"), source, game)) {
                if (attackBlockOtherTax.pay(source, game, source, event.getPlayerId(), false, null)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public Cost getOtherCostToPay(GameEvent event, Ability source, Game game) {
        return cost;
    }

    @Override
    public ManaCosts getManaCostToPay(GameEvent event, Ability source, Game game) {
        return manaCosts;
    }

    @Override
    public boolean isCostless(GameEvent event, Ability source, Game game) {
        ManaCosts currentManaCosts = getManaCostToPay(event, source, game);
        if (currentManaCosts != null && currentManaCosts.manaValue() > 0) {
            return false;
        }
        return getOtherCostToPay(event, source, game) == null;
    }

}
