package mage.cards.t;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.MageIdentifier;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.counter.AddCounterEnteringCreatureEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;
import mage.watchers.Watcher;

/**
 *
 * @author jeffwadsworth
 */
public class TheTombOfAclazotz extends CardImpl {

    public TheTombOfAclazotz(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, null);
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CAVE);

        // this is the second face of Tarrian's Journal
        this.nightCard = true;

        // {T}: Add {B}.
        this.addAbility(new BlackManaAbility());

        // You may cast a creature spell from your graveyard this turn. If you do, it enters the battlefield with a finality counter on it and is a Vampire in addition to its other types.
        Ability castSpellAbility = new SimpleActivatedAbility(new TheTombOfAclazotzEffect(), new TapSourceCost());
        castSpellAbility.setIdentifier(MageIdentifier.TheTombOfAclazotzWatcher);
        castSpellAbility.addWatcher(new TheTombOfAclazotzWatcher());
        this.addAbility(castSpellAbility);

    }

    private TheTombOfAclazotz(final TheTombOfAclazotz card) {
        super(card);
    }

    @Override
    public TheTombOfAclazotz copy() {
        return new TheTombOfAclazotz(this);
    }
}

class TheTombOfAclazotzEffect extends AsThoughEffectImpl {

    TheTombOfAclazotzEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "You may cast a creature spell from your graveyard this turn. If you do, it enters the battlefield with a finality counter on it and is a Vampire in addition to its other types. (If a creature with a finality counter on it would die, exile it instead.)";
    }

    private TheTombOfAclazotzEffect(final TheTombOfAclazotzEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public TheTombOfAclazotzEffect copy() {
        return new TheTombOfAclazotzEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        TheTombOfAclazotzWatcher watcher = game.getState().getWatcher(TheTombOfAclazotzWatcher.class);
        if (watcher != null) {
            watcher.addPlayable(source, game);
            watcher.addPlayFromAnywhereEffect(this.getId());
        }
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        throw new IllegalArgumentException("Wrong code usage: can't call applies method on empty affectedAbility");
    }

    @Override
    public boolean applies(UUID objectId, Ability affectedAbility, Ability source, Game game, UUID playerId) {
        TheTombOfAclazotzWatcher watcher = game.getState().getWatcher(TheTombOfAclazotzWatcher.class);
        if (watcher == null
                || !watcher.checkPermission(playerId, source, game)
                || game.getState().getZone(objectId) != Zone.GRAVEYARD) {
            return false;
        }
        Card card = game.getCard(objectId);
        if (card != null
                && affectedAbility instanceof SpellAbility
                && card.getOwnerId().equals(playerId)
                && card.isCreature(game)) {
            return true;
        }
        return false;
    }
}

class TheTombOfAclazotzWatcher extends Watcher {

    private final Map<MageObjectReference, Map<UUID, Integer>> morMap = new HashMap<>();
    private MageObjectReference mor;
    private UUID playFromAnywhereEffectId;

    TheTombOfAclazotzWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (GameEvent.EventType.SPELL_CAST.equals(event.getType())
                && event.hasApprovingIdentifier(MageIdentifier.TheTombOfAclazotzWatcher)) {
            Spell target = game.getSpell(event.getTargetId());
            Card card = target.getCard();
            if (card != null) {
                mor = new MageObjectReference(card.getId(), card.getZoneChangeCounter(game), game);
                if (mor != null) {
                    game.getState().addEffect(new AddCounterEnteringCreatureEffect(new MageObjectReference(target.getCard(), game),
                            CounterType.FINALITY.createInstance(), Outcome.Neutral),
                            target.getSpellAbility());
                    game.getState().addEffect(new AddSubtypeEnteringCreatureEffect(new MageObjectReference(target.getCard(), game), SubType.VAMPIRE, Outcome.Benefit), target.getSpellAbility());
                    // Rule 728.2 we must insure the effect is used (creature is cast successfully) before discarding the play effect
                    UUID playEffectId = this.getPlayFromAnywhereEffect();
                    if (playEffectId != null
                            && game.getContinuousEffects().getApplicableAsThoughEffects(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, game).listIterator().next().getId().equals(playEffectId)) {
                        // discard the play effect
                        game.getContinuousEffects().getApplicableAsThoughEffects(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, game).listIterator().next().discard();
                    }
                }
            }
        }
    }

    boolean checkPermission(UUID playerId, Ability source, Game game) {
        if (!playerId.equals(source.getControllerId())) {
            return false;
        }
        MageObjectReference mor = new MageObjectReference(
                source.getSourceId(), source.getSourceObjectZoneChangeCounter(), game
        );
        return morMap.computeIfAbsent(mor, m -> new HashMap<>()).getOrDefault(playerId, 0) > 0;
    }

    void addPlayable(Ability source, Game game) {
        MageObjectReference mor = new MageObjectReference(
                source.getSourceId(), source.getSourceObjectZoneChangeCounter(), game
        );
        morMap.computeIfAbsent(mor, m -> new HashMap<>())
                .compute(source.getControllerId(), CardUtil::setOrIncrementValue);
    }

    void addPlayFromAnywhereEffect(UUID uuid) {
        playFromAnywhereEffectId = uuid;
    }

    UUID getPlayFromAnywhereEffect() {
        return playFromAnywhereEffectId;
    }

    @Override
    public void reset() {
        morMap.clear();
        super.reset();
    }

}

class AddSubtypeEnteringCreatureEffect extends ReplacementEffectImpl {

    private final MageObjectReference mor;
    private final SubType subType;

    public AddSubtypeEnteringCreatureEffect(MageObjectReference mor, SubType subType, Outcome outcome) {
        super(Duration.WhileOnBattlefield, outcome);
        this.mor = mor;
        this.subType = subType;
    }

    private AddSubtypeEnteringCreatureEffect(final AddSubtypeEnteringCreatureEffect effect) {
        super(effect);
        this.mor = effect.mor;
        this.subType = effect.subType;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
        return permanent != null
                && mor.refersTo(permanent, game);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent target = ((EntersTheBattlefieldEvent) event).getTarget();
        if (target != null) {
            AddCardSubTypeEnteringTargetEffect effect = new AddCardSubTypeEnteringTargetEffect(subType, Duration.WhileOnBattlefield);
            effect.setTargetPointer(new FixedTarget(target, game));
            game.addEffect(effect, source);

        }
        return false;
    }

    @Override
    public AddSubtypeEnteringCreatureEffect copy() {
        return new AddSubtypeEnteringCreatureEffect(this);
    }
}

class AddCardSubTypeEnteringTargetEffect extends ContinuousEffectImpl {

    private final SubType addedSubType;

    public AddCardSubTypeEnteringTargetEffect(SubType addedSubType, Duration duration) {
        super(duration, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
        this.addedSubType = addedSubType;
    }

    protected AddCardSubTypeEnteringTargetEffect(final AddCardSubTypeEnteringTargetEffect effect) {
        super(effect);
        this.addedSubType = effect.addedSubType;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(game.getObject(targetPointer.getFixedTarget(game, source).getTarget()).getId());
        if (target != null) {
            target.addSubType(game, addedSubType);
        }
        if (target == null) {
            discard();
        }
        return false;
    }

    @Override
    public AddCardSubTypeEnteringTargetEffect copy() {
        return new AddCardSubTypeEnteringTargetEffect(this);
    }
}
