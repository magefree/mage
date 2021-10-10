package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.PlayLandAbility;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.ZoneChangeAllTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CommanderPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;
import mage.watchers.common.CommanderPlaysCountWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MythUnbound extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(CommanderPredicate.instance);
        filter.add(TargetController.YOU.getOwnerPredicate());
    }

    public MythUnbound(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        // Your commander costs {1} less to cast for each time it's been cast from the command zone this game.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new MythUnboundCostReductionEffect()
        ));

        // Whenever your commander is put into the command zone from anywhere, draw a card.
        this.addAbility(new ZoneChangeAllTriggeredAbility(
                Zone.BATTLEFIELD, Zone.ALL, Zone.COMMAND,
                new DrawCardSourceControllerEffect(1), filter,
                "Whenever your commander is put into "
                        + "the command zone from anywhere, ", false
        ));
    }

    private MythUnbound(final MythUnbound card) {
        super(card);
    }

    @Override
    public MythUnbound copy() {
        return new MythUnbound(this);
    }
}

class MythUnboundCostReductionEffect extends CostModificationEffectImpl {

    MythUnboundCostReductionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "your commander costs {1} less to cast for each time "
                + "it's been cast from the command zone this game";
    }

    MythUnboundCostReductionEffect(MythUnboundCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        Ability spellAbility = abilityToModify;
        if (spellAbility != null) {
            CommanderPlaysCountWatcher watcher = game.getState().getWatcher(CommanderPlaysCountWatcher.class);
            int castCount = watcher.getPlaysCount(abilityToModify.getSourceId());
            CardUtil.reduceCost(spellAbility, castCount);
            return true;
        }
        return false;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        if (abilityToModify instanceof SpellAbility || abilityToModify instanceof PlayLandAbility) {
            if (abilityToModify.isControlledBy(source.getControllerId())) {
                // must check all card parts (example: mdf commander)
                return game.getCommandersIds(player, CommanderCardType.COMMANDER_OR_OATHBREAKER, true).contains(abilityToModify.getSourceId());
            }
        }
        return false;
    }

    @Override
    public MythUnboundCostReductionEffect copy() {
        return new MythUnboundCostReductionEffect(this);
    }
}
