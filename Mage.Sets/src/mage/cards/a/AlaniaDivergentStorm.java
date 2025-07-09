package mage.cards.a;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.common.CopyTargetStackObjectEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
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
        Ability ability = new SpellCastControllerTriggeredAbility(
                new DoIfCostPaid(new CopyTargetStackObjectEffect(true).setText("copy that spell. You may choose new targets for the copy")
                        , new AlaniaDivergentStormCost()),
                null, false, SetTargetPointer.SPELL
        ).withInterveningIf(AlaniaDivergentStormCondition.instance);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability, new AlaniaDivergentStormWatcher());
    }

    private AlaniaDivergentStorm(final AlaniaDivergentStorm card) {
        super(card);
    }

    @Override
    public AlaniaDivergentStorm copy() {
        return new AlaniaDivergentStorm(this);
    }
}

class AlaniaDivergentStormCost extends CostImpl {

    AlaniaDivergentStormCost() {
        this.text = "have target opponent draw a card";
    }

    private AlaniaDivergentStormCost(AlaniaDivergentStormCost cost) {
        super(cost);
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        Player player = game.getPlayer(controllerId);
        Player opponent = game.getPlayer(source.getFirstTarget());
        return player != null && opponent != null;
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        paid = false;
        Player opponent = game.getPlayer(source.getFirstTarget());
        if (opponent == null || !opponent.canRespond()) {
            return false;
        }
        paid = opponent.drawCards(1, source, game) > 0;
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
        Permanent sourcePermanent = source.getSourcePermanentOrLKI(game);
        SpellAbility sourceCastAbility = sourcePermanent.getSpellAbility();
        // Get source permanent MOR from when it was on the stack
        // The UUID of a spell on the stack is NOT the same as the card that produced it--it uses the UUID of the SpellAbility from that card instead (synced to the ZCC of the source Card).
        MageObjectReference sourceSpellMOR = new MageObjectReference(sourceCastAbility.getId(), sourcePermanent.getZoneChangeCounter(game) - 1, game);
        AlaniaDivergentStormWatcher watcher = game.getState().getWatcher(AlaniaDivergentStormWatcher.class);
        UUID spellControllerID = spell.getControllerId();
        MageObjectReference spellMOR = new MageObjectReference(spell, game);
        return watcher.spellIsFirstISOCast(spellControllerID, spellMOR, sourceSpellMOR);
    }

    @Override
    public String toString() {
        return "it's the first instant spell, the first sorcery spell, or the first Otter spell other than {this} you've cast this turn";
    }
}

// Based on FirstSpellCastThisTurnWatcher
class AlaniaDivergentStormWatcher extends Watcher {

    private final Map<UUID, MageObjectReference> playerFirstInstantCast = new HashMap<>();
    private final Map<UUID, MageObjectReference> playerFirstSorceryCast = new HashMap<>();
    private final Map<UUID, MageObjectReference> playerFirstOtterCast = new HashMap<>();
    private final Map<UUID, MageObjectReference> playerSecondOtterCast = new HashMap<>();

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
        UUID spellControllerID = spell.getControllerId();
        MageObjectReference spellMOR = new MageObjectReference(spell, game);
        if (spell.getCardType(game).contains(CardType.INSTANT)) {
            playerFirstInstantCast.putIfAbsent(spellControllerID, spellMOR);
        }
        if (spell.getCardType(game).contains(CardType.SORCERY)) {
            playerFirstSorceryCast.putIfAbsent(spellControllerID, spellMOR);
        }
        if (spell.hasSubtype(SubType.OTTER, game)) {
            if (playerFirstOtterCast.containsKey(spellControllerID)) {
                // We already cast an otter this turn, put it on the second otter list
                playerSecondOtterCast.putIfAbsent(spellControllerID, spellMOR);
            }
            // Will only put if we didnt cast an otter this turn yet
            playerFirstOtterCast.putIfAbsent(spellControllerID, spellMOR);
        }
    }

    @Override
    public void reset() {
        super.reset();
        playerFirstInstantCast.clear();
        playerFirstSorceryCast.clear();
        playerFirstOtterCast.clear();
        playerSecondOtterCast.clear();
    }

    public boolean spellIsFirstISOCast(UUID controllerID, MageObjectReference spell, MageObjectReference AlaniaMOR) {

        MageObjectReference firstOtterMOR = playerFirstOtterCast.get(controllerID);

        if (firstOtterMOR != null && firstOtterMOR.equals(AlaniaMOR)) {
            // The first otter we cast was the triggering Alania! check if the second otter matches instead
            firstOtterMOR = playerSecondOtterCast.get(controllerID);
        }

        return spell.equals(playerFirstInstantCast.get(controllerID)) ||
                spell.equals(playerFirstSorceryCast.get(controllerID)) ||
                (spell.equals(firstOtterMOR));
    }

}
