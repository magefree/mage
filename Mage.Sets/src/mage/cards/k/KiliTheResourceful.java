package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.EnduringStoryCondition;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.hint.common.EnduringStoryHint;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.StoriedAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author nandmp
 */
public final class KiliTheResourceful extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent("another Dwarf or Equipment you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.or(
                SubType.DWARF.getPredicate(),
                SubType.EQUIPMENT.getPredicate()
        ));
    }

    public KiliTheResourceful(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Storied
        this.addAbility(new StoriedAbility());

        // As long as you have an enduring story, you may pay {0} rather than pay the equip cost of the first equip ability you activate each turn.
        this.addAbility(new SimpleStaticAbility(new KiliTheResourcefulCostEffect())
                .addHint(EnduringStoryHint.instance), new KiliTheResourcefulWatcher());

        // Whenever another Dwarf or Equipment you control enters, draw a card. This ability triggers only once each turn.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                new DrawCardSourceControllerEffect(1), filter
        ).setTriggersLimitEachTurn(1));
    }

    private KiliTheResourceful(final KiliTheResourceful card) {
        super(card);
    }

    @Override
    public KiliTheResourceful copy() {
        return new KiliTheResourceful(this);
    }
}

class KiliTheResourcefulCostEffect extends CostModificationEffectImpl {

    KiliTheResourcefulCostEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.SET_COST);
        staticText = "as long as you have an enduring story, you may pay {0} rather than pay the equip cost of the first equip ability you activate each turn";
    }

    private KiliTheResourcefulCostEffect(final KiliTheResourcefulCostEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify instanceof EquipAbility
                && source.isControlledBy(abilityToModify.getControllerId())
                && EnduringStoryCondition.instance.apply(game, source)
                && !KiliTheResourcefulWatcher.checkPlayer(abilityToModify.getControllerId(), game);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        Player controller = game.getPlayer(abilityToModify.getControllerId());
        if (controller == null || (!game.inCheckPlayableState() && !controller.chooseUse(
                Outcome.PlayForFree,
                String.format("Pay {0} to equip instead of %s?", abilityToModify.getManaCostsToPay().getText()),
                source,
                game
        ))) {
            return false;
        }
        abilityToModify.clearCosts();
        abilityToModify.clearManaCostsToPay();
        return true;
    }

    @Override
    public KiliTheResourcefulCostEffect copy() {
        return new KiliTheResourcefulCostEffect(this);
    }
}

class KiliTheResourcefulWatcher extends Watcher {

    private final Set<UUID> playersThatEquippedThisTurn = new HashSet<>();

    KiliTheResourcefulWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ACTIVATED_ABILITY) {
            return;
        }
        StackObject stackObject = game.getStack().getStackObject(event.getSourceId());
        if (stackObject != null && stackObject.getStackAbility() instanceof EquipAbility) {
            playersThatEquippedThisTurn.add(event.getPlayerId());
        }
    }

    @Override
    public void reset() {
        super.reset();
        playersThatEquippedThisTurn.clear();
    }

    static boolean checkPlayer(UUID playerId, Game game) {
        KiliTheResourcefulWatcher watcher = game.getState().getWatcher(KiliTheResourcefulWatcher.class);
        return watcher != null && watcher.playersThatEquippedThisTurn.contains(playerId);
    }
}
