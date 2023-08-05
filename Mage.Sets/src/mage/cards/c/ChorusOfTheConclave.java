package mage.cards.c;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.ForestwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.ManaUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class ChorusOfTheConclave extends CardImpl {

    public ChorusOfTheConclave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}{W}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRYAD);

        this.power = new MageInt(3);
        this.toughness = new MageInt(8);

        // Forestwalk
        this.addAbility(new ForestwalkAbility());

        // As an additional cost to cast creature spells, you may pay any amount of mana. If you do, that creature enters the battlefield with that many additional +1/+1 counters on it.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ChorusOfTheConclaveReplacementEffect()));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ChorusOfTheConclaveReplacementEffect2()));

    }

    private ChorusOfTheConclave(final ChorusOfTheConclave card) {
        super(card);
    }

    @Override
    public ChorusOfTheConclave copy() {
        return new ChorusOfTheConclave(this);
    }
}

class ChorusOfTheConclaveReplacementEffect extends ReplacementEffectImpl {

    public ChorusOfTheConclaveReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "As an additional cost to cast creature spells, you may pay any amount of mana";
    }

    public ChorusOfTheConclaveReplacementEffect(final ChorusOfTheConclaveReplacementEffect effect) {
        super(effect);
    }

    @Override
    public ChorusOfTheConclaveReplacementEffect copy() {
        return new ChorusOfTheConclaveReplacementEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getPlayerId().equals(source.getControllerId())) {
            MageObject spellObject = game.getObject(event.getSourceId());
            if (spellObject != null) {
                return spellObject.isCreature(game);
            }
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        int xCost = 0;
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (controller.chooseUse(Outcome.Benefit, "Pay the additonal cost to add +1/+1 counters to the creature you cast?", source, game)) {
                xCost += ManaUtil.playerPaysXGenericMana(false, "Chorus of the Conclave", controller, source, game);
                // save the x value to be available for ETB replacement effect
                Object object = game.getState().getValue("spellX" + source.getSourceId());
                Map<String, Integer> spellX;
                if (object instanceof Map) {
                    spellX = (Map<String, Integer>) object;
                } else {
                    spellX = new HashMap<>();
                }
                spellX.put(event.getSourceId().toString() + game.getState().getZoneChangeCounter(event.getSourceId()), xCost);
                game.getState().setValue("spellX" + source.getSourceId(), spellX);
            }
        }
        return false;
    }
}

class ChorusOfTheConclaveReplacementEffect2 extends ReplacementEffectImpl {

    public ChorusOfTheConclaveReplacementEffect2() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If you do, that creature enters the battlefield with that many additional +1/+1 counters on it";
    }

    public ChorusOfTheConclaveReplacementEffect2(final ChorusOfTheConclaveReplacementEffect2 effect) {
        super(effect);
    }

    @Override
    public ChorusOfTheConclaveReplacementEffect2 copy() {
        return new ChorusOfTheConclaveReplacementEffect2(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Map<String, Integer> spellX = (Map<String, Integer>) game.getState().getValue("spellX" + source.getSourceId());
        return spellX != null
                && event.getSourceId() != null
                && spellX.containsKey(event.getSourceId().toString() + (game.getState().getZoneChangeCounter(event.getSourceId()) - 1));
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        Map<String, Integer> spellX = (Map<String, Integer>) game.getState().getValue("spellX" + source.getSourceId());
        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject != null && creature != null && spellX != null) {
            String key = event.getSourceId().toString() + (game.getState().getZoneChangeCounter(event.getSourceId()) - 1);
            int xValue = spellX.get(key);
            if (xValue > 0) {
                creature.addCounters(CounterType.P1P1.createInstance(xValue), source.getControllerId(), source, game, event.getAppliedEffects());
                game.informPlayers(sourceObject.getLogName() + ": " + creature.getLogName() + " enters the battlefield with " + xValue + " +1/+1 counter" + (xValue > 1 ? "s" : "") + " on it");
            }
            spellX.remove(key);
        }
        return false;
    }

}
