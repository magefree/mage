package mage.cards.a;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.PowerUpAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.watchers.Watcher;

/**
 *
 * @author muz
 */
public final class AdvancingTheSpirit extends CardImpl {

    public AdvancingTheSpirit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        // When this enchantment enters, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1)));

        // You may pay {0} rather than pay the power-up cost of the first power-up ability you activate during each of your turns.
        this.addAbility(new SimpleStaticAbility(new AdvancingTheSpiritCostEffect()), new AdvancingTheSpiritWatcher());
    }

    private AdvancingTheSpirit(final AdvancingTheSpirit card) {
        super(card);
    }

    @Override
    public AdvancingTheSpirit copy() {
        return new AdvancingTheSpirit(this);
    }
}

class AdvancingTheSpiritCostEffect extends CostModificationEffectImpl {

    AdvancingTheSpiritCostEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.SET_COST);
        this.staticText = "you may pay {0} rather than pay the power-up cost " +
            "of the first power-up ability you activate during each of your turns.";
    }

    private AdvancingTheSpiritCostEffect(final AdvancingTheSpiritCostEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify instanceof PowerUpAbility
            && source.isControlledBy(abilityToModify.getControllerId())
            && game.isActivePlayer(abilityToModify.getControllerId())
            && !AdvancingTheSpiritWatcher.checkPlayer(abilityToModify.getControllerId(), game);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        boolean applyReduce = false;
        if (game.inCheckPlayableState()) {
            // getPlayable use - apply all the time
            applyReduce = true;
        } else {
            // real use - ask the player
            Player controller = game.getPlayer(abilityToModify.getControllerId());
            if (controller != null
                    && controller.chooseUse(Outcome.PlayForFree,
                    String.format("Pay {0} to power-up instead %s?", abilityToModify.getManaCostsToPay().getText()), source, game)) {
                applyReduce = true;
            }
        }

        if (applyReduce) {
            abilityToModify.clearCosts();
            abilityToModify.clearManaCostsToPay();
            return true;
        }

        return false;
    }

    @Override
    public AdvancingTheSpiritCostEffect copy() {
        return new AdvancingTheSpiritCostEffect(this);
    }
}

class AdvancingTheSpiritWatcher extends Watcher {

    private final Set<UUID> playerSet = new HashSet<>();

    AdvancingTheSpiritWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ACTIVATED_ABILITY) {
            return;
        }
        StackObject object = game.getStack().getStackObject(event.getSourceId());
        if (object != null && object.getStackAbility() instanceof PowerUpAbility) {
            playerSet.add(event.getPlayerId());
        }
    }

    @Override
    public void reset() {
        super.reset();
        playerSet.clear();
    }

    static boolean checkPlayer(UUID playerId, Game game) {
        AdvancingTheSpiritWatcher watcher = game.getState().getWatcher(AdvancingTheSpiritWatcher.class);
        return watcher != null && watcher.playerSet.contains(playerId);
    }
}
