package mage.cards.t;

import mage.MageIdentifier;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.abilities.effects.common.continuous.AddCreatureTypeAdditionEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Susucr
 */
public final class TheTombOfAclazotz extends CardImpl {

    public TheTombOfAclazotz(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CAVE);

        // (Transforms from Tarrian's Journal.)
        this.nightCard = true;

        // {T}: Add {B}.
        this.addAbility(new BlackManaAbility());

        // {T}: You may cast a creature spell from your graveyard this turn. If you do, it enters the battlefield with a finality counter on it and is a Vampire in addition to its other types.
        this.addAbility(
                new SimpleActivatedAbility(
                        new TheTombOfAclazotzEffect(),
                        new TapSourceCost()
                ).setIdentifier(MageIdentifier.TheTombOfAclazotzWatcher),
                new TheTombOfAclazotzWatcher()
        );
    }

    private TheTombOfAclazotz(final TheTombOfAclazotz card) {
        super(card);
    }

    @Override
    public TheTombOfAclazotz copy() {
        return new TheTombOfAclazotz(this);
    }
}

class TheTombOfAclazotzEffect extends OneShotEffect {

    TheTombOfAclazotzEffect() {
        super(Outcome.Benefit);
        staticText = "You may cast a creature spell from your graveyard this turn. "
                + "If you do, it enters the battlefield with a finality counter on it "
                + "and is a Vampire in addition to its other types.";
    }

    private TheTombOfAclazotzEffect(final TheTombOfAclazotzEffect effect) {
        super(effect);
    }

    @Override
    public TheTombOfAclazotzEffect copy() {
        return new TheTombOfAclazotzEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        TheTombOfAclazotzWatcher watcher = game.getState().getWatcher(TheTombOfAclazotzWatcher.class);
        if (watcher == null) {
            return false;
        }

        UUID playerId = source.getControllerId();
        // The continuous effects may need to be initialized.
        if (!watcher.isActiveThisTurnForPlayer(playerId)) {
            game.addEffect(new TheTombOfAclazotzCastFromGraveyardEffect(), source);
        }
        if (!watcher.isActiveThisTurn()) {
            game.addEffect(new TheTombOfAclazotzReplacementEffect(), source);
        }
        watcher.incrementLeftUsage(playerId);
        return true;
    }
}

class TheTombOfAclazotzCastFromGraveyardEffect extends AsThoughEffectImpl {

    TheTombOfAclazotzCastFromGraveyardEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
    }

    private TheTombOfAclazotzCastFromGraveyardEffect(final TheTombOfAclazotzCastFromGraveyardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public TheTombOfAclazotzCastFromGraveyardEffect copy() {
        return new TheTombOfAclazotzCastFromGraveyardEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        // Is it a cast from your graveyard?
        if (!source.isControlledBy(affectedControllerId)
                || !source.isControlledBy(game.getOwnerId(objectId))
                || !Zone.GRAVEYARD.equals(game.getState().getZone(objectId))) {
            return false;
        }

        MageObject mageObject = game.getObject(objectId);
        // Is the card attempted to be cast is a creature spell?
        if (mageObject == null || !mageObject.isCreature(game)) {
            return false;
        }

        TheTombOfAclazotzWatcher watcher = game.getState().getWatcher(TheTombOfAclazotzWatcher.class);
        // Do you still have usage left this turn?
        if (watcher == null || !watcher.hasUseLeft(source.getControllerId())) {
            return false;
        }

        return true;
    }
}

class TheTombOfAclazotzReplacementEffect extends ReplacementEffectImpl {

    TheTombOfAclazotzReplacementEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit);
    }

    private TheTombOfAclazotzReplacementEffect(final TheTombOfAclazotzReplacementEffect effect) {
        super(effect);
    }

    @Override
    public TheTombOfAclazotzReplacementEffect copy() {
        return new TheTombOfAclazotzReplacementEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return GameEvent.EventType.CAST_SPELL_LATE == event.getType();
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Spell spell = game.getStack().getSpell(event.getSourceId());
        if (spell == null) {
            return false;
        }
        Effect effect = new EntersBattlefieldWithXCountersEffect(CounterType.FINALITY.createInstance());
        effect.setValue("appliedEffects", event.getAppliedEffects());
        effect.apply(game, source);
        effect = new AddCreatureTypeAdditionEffect(SubType.VAMPIRE);
        effect.setValue("appliedEffects", event.getAppliedEffects());
        effect.setTargetPointer(new FixedTarget(spell.getCard().getId(), game));
        effect.apply(game, source);
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.hasApprovingIdentifier(MageIdentifier.TheTombOfAclazotzWatcher);
    }
}

class TheTombOfAclazotzWatcher extends Watcher {

    // playerId -> number of use left this turn
    // Of note, we do not clear a player's count on decrement to 0, in order
    // to keep track that the continuous effect are active or not this turn for that player.
    private final Map<UUID, Integer> usesLeft = new HashMap<>();

    TheTombOfAclazotzWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if ((GameEvent.EventType.SPELL_CAST.equals(event.getType()))
                && event.hasApprovingIdentifier(MageIdentifier.TheTombOfAclazotzWatcher)) {
            decrementLeftUsage(event.getPlayerId());
        }
    }

    @Override
    public void reset() {
        super.reset();
        usesLeft.clear();
    }

    boolean isActiveThisTurn() {
        return !usesLeft.isEmpty();
    }

    boolean isActiveThisTurnForPlayer(UUID playerId) {
        return usesLeft.containsKey(playerId);
    }

    boolean hasUseLeft(UUID playerId) {
        return usesLeft.getOrDefault(playerId, 0) > 0;
    }

    void incrementLeftUsage(UUID playerId) {
        usesLeft.put(playerId, usesLeft.getOrDefault(playerId, 0) + 1);
    }

    void decrementLeftUsage(UUID playerId) {
        usesLeft.put(playerId, usesLeft.getOrDefault(playerId, 0) - 1);
    }
}
