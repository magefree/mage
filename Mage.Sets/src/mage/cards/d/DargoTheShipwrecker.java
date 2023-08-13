package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeXTargetCost;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.PartnerAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DargoTheShipwrecker extends CardImpl {

    public DargoTheShipwrecker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(7);
        this.toughness = new MageInt(5);

        // As an additional cost to cast this spell, you may sacrifice any number of artifacts and/or creatures. This spell costs {2} less to cast for each permanent sacrificed this way and {2} less to cast for each other artifact or creature you've sacrificed this turn.
        Cost cost = new SacrificeXTargetCost(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_OR_CREATURE, true);
        cost.setText("you may sacrifice any number of artifacts and/or creatures. " +
                "This spell costs {2} less to cast for each permanent sacrificed this way " +
                "and {2} less to cast for each other artifact or creature you've sacrificed this turn");
        this.getSpellAbility().addCost(cost);
        Ability ability = new SimpleStaticAbility(Zone.ALL, new DargoTheShipwreckerEffect());
        ability.setRuleVisible(false);
        this.addAbility(ability, new DargoTheShipwreckerWatcher());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    private DargoTheShipwrecker(final DargoTheShipwrecker card) {
        super(card);
    }

    @Override
    public DargoTheShipwrecker copy() {
        return new DargoTheShipwrecker(this);
    }
}

class DargoTheShipwreckerEffect extends CostModificationEffectImpl {

    public DargoTheShipwreckerEffect() {
        super(Duration.WhileOnStack, Outcome.Benefit, CostModificationType.REDUCE_COST);
    }

    public DargoTheShipwreckerEffect(final DargoTheShipwreckerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        SpellAbility spellAbility = (SpellAbility) abilityToModify;
        int reduction = 0;
        for (Cost cost : spellAbility.getCosts()) {
            if (!(cost instanceof SacrificeXTargetCost)) {
                continue;
            }
            if (game.inCheckPlayableState()) {
                // allows to cast in getPlayable
                reduction += ((SacrificeXTargetCost) cost).getMaxValue(spellAbility, game);
            } else {
                // real cast
                reduction += ((SacrificeXTargetCost) cost).getAmount();
            }
            break;
        }
        DargoTheShipwreckerWatcher watcher = game.getState().getWatcher(DargoTheShipwreckerWatcher.class);
        if (watcher != null) {
            reduction += watcher.getSacCount(source.getControllerId());
        }
        CardUtil.adjustCost(spellAbility, reduction * 2);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify instanceof SpellAbility && abilityToModify.getSourceId().equals(source.getSourceId());
    }

    @Override
    public DargoTheShipwreckerEffect copy() {
        return new DargoTheShipwreckerEffect(this);
    }
}

class DargoTheShipwreckerWatcher extends Watcher {

    private static final Map<UUID, Integer> sacMap = new HashMap<>();

    DargoTheShipwreckerWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.SACRIFICED_PERMANENT) {
            return;
        }
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
        if (permanent != null && (permanent.isCreature(game) || permanent.isArtifact(game))) {
            sacMap.compute(event.getPlayerId(), CardUtil::setOrIncrementValue);
        }
    }

    @Override
    public void reset() {
        sacMap.clear();
        super.reset();
    }

    int getSacCount(UUID playerId) {
        return sacMap.getOrDefault(playerId, 0);
    }
}
