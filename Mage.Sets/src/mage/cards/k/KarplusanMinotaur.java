
package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.CoinFlippedEvent;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetOpponent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author L_J
 */
public final class KarplusanMinotaur extends CardImpl {

    public KarplusanMinotaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");
        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Cumulative upkeep-Flip a coin.
        this.addAbility(new CumulativeUpkeepAbility(new KarplusanMinotaurCost()));

        // Whenever you win a coin flip, Karplusan Minotaur deals 1 damage to any target.
        this.addAbility(new KarplusanMinotaurFlipWinTriggeredAbility());

        // Whenever you lose a coin flip, Karplusan Minotaur deals 1 damage to any target of an opponent's choice.
        this.addAbility(new KarplusanMinotaurFlipLoseTriggeredAbility());
    }

    private KarplusanMinotaur(final KarplusanMinotaur card) {
        super(card);
    }

    @Override
    public KarplusanMinotaur copy() {
        return new KarplusanMinotaur(this);
    }
}

class KarplusanMinotaurFlipWinTriggeredAbility extends TriggeredAbilityImpl {

    public KarplusanMinotaurFlipWinTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(1), false);
        this.addTarget(new TargetAnyTarget());
    }

    public KarplusanMinotaurFlipWinTriggeredAbility(final KarplusanMinotaurFlipWinTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public KarplusanMinotaurFlipWinTriggeredAbility copy() {
        return new KarplusanMinotaurFlipWinTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COIN_FLIPPED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        CoinFlippedEvent flipEvent = (CoinFlippedEvent) event;
        return flipEvent.getPlayerId().equals(controllerId)
                && flipEvent.isWinnable()
                && (flipEvent.getChosen() == flipEvent.getResult());
    }

    @Override
    public String getRule() {
        return "Whenever you win a coin flip, {this} deals 1 damage to any target.";
    }
}

class KarplusanMinotaurFlipLoseTriggeredAbility extends TriggeredAbilityImpl {

    public KarplusanMinotaurFlipLoseTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(1), false);
        this.addTarget(new TargetAnyTarget());
        targetAdjuster = KarplusanMinotaurAdjuster.instance;
    }

    public KarplusanMinotaurFlipLoseTriggeredAbility(final KarplusanMinotaurFlipLoseTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public KarplusanMinotaurFlipLoseTriggeredAbility copy() {
        return new KarplusanMinotaurFlipLoseTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COIN_FLIPPED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        CoinFlippedEvent flipEvent = (CoinFlippedEvent) event;
        return flipEvent.getPlayerId().equals(controllerId)
                && flipEvent.isWinnable()
                && (flipEvent.getChosen() != flipEvent.getResult());
    }

    @Override
    public String getRule() {
        return "Whenever you lose a coin flip, {this} deals 1 damage to any target of an opponent's choice.";
    }
}

class KarplusanMinotaurCost extends CostImpl {

    KarplusanMinotaurCost() {
        this.text = "Flip a coin";
    }

    private KarplusanMinotaurCost(final KarplusanMinotaurCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            controller.flipCoin(ability, game, true);
            this.paid = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        Player controller = game.getPlayer(controllerId);
        return controller != null && (!game.getOpponents(controllerId).isEmpty());
    }

    @Override
    public KarplusanMinotaurCost copy() {
        return new KarplusanMinotaurCost(this);
    }
}

enum KarplusanMinotaurAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        Player controller = game.getPlayer(ability.getControllerId());
        if (controller == null) {
            return;
        }
        UUID opponentId = null;
        if (game.getOpponents(controller.getId()).size() > 1) {
            Target target = new TargetOpponent(true);
            if (controller.chooseTarget(Outcome.Neutral, target, ability, game)) {
                opponentId = target.getFirstTarget();
            }
        } else {
            opponentId = game.getOpponents(controller.getId()).iterator().next();
        }
        if (opponentId != null) {
            ability.getTargets().get(0).setTargetController(opponentId);
        }
    }
}
