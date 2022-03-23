package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BruenorBattlehammer extends CardImpl {

    public BruenorBattlehammer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Each creature you control gets +2/+0 for each Equipment attached to it.
        this.addAbility(new SimpleStaticAbility(new BruenorBattlehammerBoostEffect()));

        // You may pay {0} rather than pay the equip cost of the first equip ability you activate each turn.
        this.addAbility(new SimpleStaticAbility(new BruenorBattlehammerCostEffect()), new BruenorBattlehammerWatcher());
    }

    private BruenorBattlehammer(final BruenorBattlehammer card) {
        super(card);
    }

    @Override
    public BruenorBattlehammer copy() {
        return new BruenorBattlehammer(this);
    }
}

class BruenorBattlehammerBoostEffect extends ContinuousEffectImpl {

    BruenorBattlehammerBoostEffect() {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        staticText = "each creature you control gets +2/+0 for each Equipment attached to it";
    }

    private BruenorBattlehammerBoostEffect(final BruenorBattlehammerBoostEffect effect) {
        super(effect);
    }

    @Override
    public BruenorBattlehammerBoostEffect copy() {
        return new BruenorBattlehammerBoostEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_CREATURE,
                source.getControllerId(), source, game
        )) {
            int equipped = permanent
                    .getAttachments()
                    .stream()
                    .map(game::getPermanent)
                    .mapToInt(p -> p.hasSubtype(SubType.EQUIPMENT, game) ? 1 : 0)
                    .sum();
            permanent.addPower(2 * equipped);
        }
        return true;
    }
}

class BruenorBattlehammerCostEffect extends CostModificationEffectImpl {

    BruenorBattlehammerCostEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.SET_COST);
        this.staticText = "you may pay {0} rather than pay the equip cost " +
                "of the first equip ability you activate each turn.";
    }

    BruenorBattlehammerCostEffect(final BruenorBattlehammerCostEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify instanceof EquipAbility
                && source.isControlledBy(abilityToModify.getControllerId())
                && !BruenorBattlehammerWatcher.checkPlayer(abilityToModify.getControllerId(), game);
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
                    String.format("Pay {0} to equip instead %s?", abilityToModify.getManaCostsToPay().getText()), source, game)) {
                applyReduce = true;
            }
        }

        if (applyReduce) {
            abilityToModify.getCosts().clear();
            abilityToModify.getManaCostsToPay().clear();
            return true;
        }

        return false;
    }

    @Override
    public BruenorBattlehammerCostEffect copy() {
        return new BruenorBattlehammerCostEffect(this);
    }
}

class BruenorBattlehammerWatcher extends Watcher {

    private final Set<UUID> playerSet = new HashSet<>();

    BruenorBattlehammerWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ACTIVATED_ABILITY) {
            return;
        }
        StackObject object = game.getStack().getStackObject(event.getSourceId());
        if (object != null && object.getStackAbility() instanceof EquipAbility) {
            playerSet.add(event.getPlayerId());
        }
    }

    @Override
    public void reset() {
        super.reset();
        playerSet.clear();
    }

    static boolean checkPlayer(UUID playerId, Game game) {
        BruenorBattlehammerWatcher watcher = game.getState().getWatcher(BruenorBattlehammerWatcher.class);
        return watcher != null && watcher.playerSet.contains(playerId);
    }
}
