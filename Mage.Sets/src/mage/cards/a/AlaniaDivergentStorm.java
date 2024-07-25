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
import mage.game.permanent.Permanent;
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
        if (!game.isSimulation()){
            int x = 3;
        }
        Spell spell = (Spell) source.getEffects().get(0).getValue("spellCast");
        if (spell == null) {
            return false;
        }
        Permanent sourcePermanent = source.getSourcePermanentOrLKI(game);
        // Get source permanent MOR from when it was on the stack
        MageObjectReference sourceSpellMOR = new MageObjectReference(sourcePermanent, game, -1);
        AlaniaDivergentStormWatcher watcher = game.getState().getWatcher(AlaniaDivergentStormWatcher.class);
        UUID spellControllerID = spell.getControllerId();
        MageObjectReference spellMOR = new MageObjectReference(spell, game);
        return watcher.spellIsFirstISOCast(spellControllerID, spellMOR, sourceSpellMOR, game);
    }
}

// Based on FirstSpellCastThisTurnWatcher
class AlaniaDivergentStormWatcher extends Watcher {

    private final Map<UUID, MageObjectReference> playerFirstInstantCast = new HashMap<>();
    private final Map<UUID, MageObjectReference> playerFirstSorceryCast = new HashMap<>();
    private final Map<UUID, MageObjectReference> playerFirstOtterCast = new HashMap<>();
    private final Map<UUID, MageObjectReference> playerSecondOtterCast = new HashMap<>();

    private static final Logger logger = Logger.getLogger(AlaniaDivergentStormWatcher.class);

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
        if (!game.isSimulation()){
//            int x = 3;
            logger.info("Spell cast: " + spell.getName());
            logger.info("Spell ID: " + spell.getId());
        }
        UUID spellControllerID = spell.getControllerId();
        MageObjectReference spellMOR = new MageObjectReference(spell, game);
        if (spell.getCardType(game).contains(CardType.INSTANT) &&
                !playerFirstInstantCast.containsKey(spellControllerID)) {
            playerFirstInstantCast.put(spellControllerID, spellMOR);
        }
        if (spell.getCardType(game).contains(CardType.SORCERY) &&
                !playerFirstSorceryCast.containsKey(spellControllerID)) {
            playerFirstSorceryCast.put(spellControllerID, spellMOR);
        }
        if (spell.getSubtype(game).contains(SubType.OTTER)){
            if (!game.isSimulation()){
                logger.info("Is Otter!");
            }
            if (!playerFirstOtterCast.containsKey(spellControllerID)) {
                playerFirstOtterCast.put(spellControllerID, spellMOR);
                if (!game.isSimulation()){
                    logger.info("Is First");
                }
            } else if (!playerSecondOtterCast.containsKey(spellControllerID)) {
                playerSecondOtterCast.put(spellControllerID, spellMOR);
                if (!game.isSimulation()){
                    logger.info("Is Second");
                }
            }
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

    public boolean spellIsFirstISOCast(UUID controllerID, MageObjectReference spell, MageObjectReference AlaniaMOR, Game game) {

        MageObjectReference firstOtterMOR = playerFirstOtterCast.get(controllerID);

        if (!game.isSimulation()){
            logger.info("firstOtterMOR: " + firstOtterMOR.getSourceId());
            logger.info("spell: " + spell.getSourceId());
            logger.info("AlaniaMOR: " + AlaniaMOR.getSourceId());
            logger.info(AlaniaMOR.getCard(game).getName());
        }

        if (firstOtterMOR != null && firstOtterMOR.equals(AlaniaMOR)) {
            firstOtterMOR = playerSecondOtterCast.get(controllerID);
            if (!game.isSimulation()){
                logger.info("oops, that was Alania. ACTUAL firstOtterMOR: " + firstOtterMOR);
            }
        }

        return spell.equals(playerFirstInstantCast.get(controllerID)) ||
                spell.equals(playerFirstSorceryCast.get(controllerID)) ||
                (spell.equals(firstOtterMOR));
    }

}
