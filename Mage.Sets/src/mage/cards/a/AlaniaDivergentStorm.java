package mage.cards.a;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CopyTargetStackObjectEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.watchers.Watcher;
import org.apache.log4j.Logger;

/**
 *
 * @author jimga150
 */
public final class AlaniaDivergentStorm extends CardImpl {

    public AlaniaDivergentStorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{R}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.OTTER);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Whenever you cast a spell, if it's the first instant spell, the first sorcery spell, or the first Otter
        // spell other than Alania you've cast this turn, you may have target opponent draw a card. If you do, copy
        // that spell. You may choose new targets for the copy.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new SpellCastControllerTriggeredAbility(new DoIfCostPaid(
                        new CopyTargetStackObjectEffect(true),
                        new AlaniaDivergentStormCost()
                ), null, false, SetTargetPointer.SPELL)
                .setTriggerPhrase("Whenever you cast a spell, if it's the first instant spell, the first sorcery " +
                        "spell, or the first Otter spell other than Alania you've cast this turn, "),
                AlaniaDivergentStormCondition.instance, ""
        );
        ability.addWatcher(new AlaniaDivergentStormWatcher());
        this.addAbility(ability);

    }

    private AlaniaDivergentStorm(final AlaniaDivergentStorm card) {
        super(card);
    }

    @Override
    public AlaniaDivergentStorm copy() {
        return new AlaniaDivergentStorm(this);
    }
}

// Based on MarathWillOfTheWildRemoveCountersCost
class AlaniaDivergentStormCost extends CostImpl {

    AlaniaDivergentStormCost() {
        this.text = "have target opponent draw a card";
        this.addTarget(new TargetOpponent());
    }

    private AlaniaDivergentStormCost(AlaniaDivergentStormCost cost) {
        super(cost);
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        Player player = game.getPlayer(controllerId);
        if (player == null) {
            return false;
        }
        for (UUID opponentID : game.getOpponents(controllerId)){
            Player opponent = game.getPlayer(opponentID);
            if (opponent == null) {
                continue;
            }
            if (opponent.canBeTargetedBy(source.getSourceObject(game), controllerId, source, game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        this.getTargets().clearChosen();
        paid = false;
        if (this.getTargets().choose(Outcome.DrawCard, controllerId, source.getSourceId(), source, game)) {
            Player opponent = game.getPlayer(this.getTargets().get(0).getTargets().get(0));
            if (opponent == null || !opponent.canRespond()){
                return false;
            }
            paid = opponent.drawCards(1, source, game) != 0;
        }
        return paid;
    }

    @Override
    public AlaniaDivergentStormCost copy() {
        return new AlaniaDivergentStormCost(this);
    }
}

// Based on MastermindPlumCondition
enum AlaniaDivergentStormCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = (Spell) source.getEffects().get(0).getValue("spellCast");
        if (spell == null) {
            return false;
        }
        AlaniaDivergentStormWatcher watcher = game.getState().getWatcher(AlaniaDivergentStormWatcher.class);
        return spell.getId().equals(watcher.getIdOfFirstInstantCast(source.getControllerId())) ||
                spell.getId().equals(watcher.getIdOfFirstSorceryCast(source.getControllerId())) ||
                spell.getId().equals(watcher.getIdOfFirstOtterCast(source.getControllerId()));
    }
}

// Based on FirstSpellCastThisTurnWatcher
class AlaniaDivergentStormWatcher extends Watcher {

    private final Map<UUID, UUID> playerFirstInstantCast = new HashMap<>();
    private final Map<UUID, UUID> playerFirstSorceryCast = new HashMap<>();
    private final Map<UUID, UUID> playerFirstOtterCast = new HashMap<>();

    public AlaniaDivergentStormWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.SPELL_CAST) {
            return;
        }
        Spell spell = (Spell) game.getObject(event.getTargetId());
        if (spell == null) {
            return;
        }
        if (spell.getCardType(game).contains(CardType.INSTANT) &&
                !playerFirstInstantCast.containsKey(spell.getControllerId())) {
            playerFirstInstantCast.put(spell.getControllerId(), spell.getId());
        }
        if (spell.getCardType(game).contains(CardType.SORCERY) &&
                !playerFirstSorceryCast.containsKey(spell.getControllerId())) {
            playerFirstSorceryCast.put(spell.getControllerId(), spell.getId());
        }
        if (spell.getSubtype(game).contains(SubType.OTTER) &&
                !playerFirstOtterCast.containsKey(spell.getControllerId())) {
            playerFirstOtterCast.put(spell.getControllerId(), spell.getId());
        }

    }

    @Override
    public void reset() {
        super.reset();
        playerFirstInstantCast.clear();
        playerFirstSorceryCast.clear();
        playerFirstOtterCast.clear();
    }

    public UUID getIdOfFirstInstantCast(UUID playerId) {
        return playerFirstInstantCast.get(playerId);
    }

    public UUID getIdOfFirstSorceryCast(UUID playerId) {
        return playerFirstSorceryCast.get(playerId);
    }

    public UUID getIdOfFirstOtterCast(UUID playerId) {
        return playerFirstOtterCast.get(playerId);
    }
}
