package mage.cards.t;

import mage.MageIdentifier;
import mage.MageItem;
import mage.MageObject;
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
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;
import mage.util.SubTypes;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
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

        // You may cast a creature spell from your graveyard this turn. If you do, it enters with a finality counter on it and is a Vampire in addition to its other types.
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
        super(AsThoughEffectType.CAST_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "You may cast a creature spell from your graveyard this turn. If you do, it enters with a finality counter on it and is a Vampire in addition to its other types. <i>(If a creature with a finality counter on it would die, exile it instead.)</i>";
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
        return card != null
                && affectedAbility instanceof SpellAbility
                && card.getOwnerId().equals(playerId)
                && card.isCreature(game);
    }
}

class TheTombOfAclazotzWatcher extends Watcher {

    private final Map<MageObjectReference, Map<UUID, Integer>> morMap = new HashMap<>();
    private UUID playFromAnywhereEffectId;

    TheTombOfAclazotzWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (GameEvent.EventType.CAST_SPELL.equals(event.getType())
                && event.hasApprovingIdentifier(MageIdentifier.TheTombOfAclazotzWatcher)) {
            Spell target = game.getSpell(event.getTargetId());
            Card card = target.getCard();
            if (card != null) {
                game.getState().addEffect(new AddCounterEnteringCreatureEffect(new MageObjectReference(target.getCard(), game),
                                CounterType.FINALITY.createInstance(), Outcome.Neutral),
                        target.getSpellAbility());
                game.getState().addEffect(new AddSubtypeEnteringCreatureEffect(new MageObjectReference(target.getCard(), game), SubType.VAMPIRE, Outcome.Benefit), card.getSpellAbility());
                // Rule 728.2 we must insure the effect is used (creature is cast successfully) before discarding the play effect
                UUID playEffectId = this.getPlayFromAnywhereEffect();
                if (playEffectId != null
                        && game.getContinuousEffects().getApplicableAsThoughEffects(AsThoughEffectType.CAST_FROM_NOT_OWN_HAND_ZONE, game).listIterator().next().getId().equals(playEffectId)) {
                    // discard the play effect
                    game.getContinuousEffects().getApplicableAsThoughEffects(AsThoughEffectType.CAST_FROM_NOT_OWN_HAND_ZONE, game).listIterator().next().discard();
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

    AddSubtypeEnteringCreatureEffect(MageObjectReference mor, SubType subType, Outcome outcome) {
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
        return event.getType() == GameEvent.EventType.CAST_SPELL_LATE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        MageObject spell = game.getObject(event.getSourceId());
        return spell != null && mor.refersTo(spell, game);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Spell target = game.getSpell(event.getSourceId());
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

    AddCardSubTypeEnteringTargetEffect(SubType addedSubType, Duration duration) {
        super(duration, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
        this.addedSubType = addedSubType;
    }

    protected AddCardSubTypeEnteringTargetEffect(final AddCardSubTypeEnteringTargetEffect effect) {
        super(effect);
        this.addedSubType = effect.addedSubType;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (getAffectedObjectsSet()) {
            Spell spell = game.getSpell(getTargetPointer().getFirst(game, source));
            if (spell != null) {
                Card card = spell.getCard();
                // set a mage object reference for the permanent on the battlefield
                // target pointer will be used to the find the spell on the stack
                MageObjectReference mor = new MageObjectReference(card, game, 1);
                affectedObjectList.add(mor);
            }
        }
    }

    @Override
    public void applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> affectedObjects) {
        for (MageItem object : affectedObjects) {
            if (object instanceof Spell) {
                Spell spell = (Spell) object;
                setCreatureSubtype(spell, addedSubType, game);
                setCreatureSubtype(spell.getCard(), addedSubType, game);
            } else {
                Permanent permanent = (Permanent) object;
                setCreatureSubtype(permanent, addedSubType, game);
            }
        }
    }

    @Override
    public boolean queryAffectedObjects(Layer layer, Ability source, Game game, List<MageItem> affectedObjects) {
        MageObject target = game.getSpell(getTargetPointer().getFirst(game, source));
        if (target == null) {
            for (MageObjectReference mor : affectedObjectList) {
                target = mor.getPermanent(game);
                if (target != null) {
                    break;
                }
            }
        }
        if (target != null) {
            affectedObjects.add(target);
            return true;
        }
        this.discard();
        return false;
    }

    private void setCreatureSubtype(MageObject object, SubType subtype, Game game) {
        SubTypes subTypes = game.getState().getCreateMageObjectAttribute(object, game).getSubtype();
        if (!subTypes.contains(subtype)) {
            subTypes.add(subtype);
        }
    }

    @Override
    public AddCardSubTypeEnteringTargetEffect copy() {
        return new AddCardSubTypeEnteringTargetEffect(this);
    }
}
