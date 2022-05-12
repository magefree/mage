package mage.cards.w;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.AsThoughManaEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.ManaPoolItem;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WhispersteelDagger extends CardImpl {

    public WhispersteelDagger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{B}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+0.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(2, 0)));

        // Whenever equipped creature deals combat damage to a player, you may cast a creature spell from that player's graveyard this turn, and you may spend mana as though it were mana of any color to cast that spell.
        Ability ability = new DealsDamageToAPlayerAttachedTriggeredAbility(
                new WhispersteelDaggerCastFromExileEffect(), "equipped creature",
                false, true, true
        );
        ability.addEffect(new WhispersteelDaggerSpendAnyManaEffect());
        this.addAbility(ability, new WhispersteelDaggerWatcher());

        // Equip {3}
        this.addAbility(new EquipAbility(3, false));
    }

    private WhispersteelDagger(final WhispersteelDagger card) {
        super(card);
    }

    @Override
    public WhispersteelDagger copy() {
        return new WhispersteelDagger(this);
    }
}

class WhispersteelDaggerCastFromExileEffect extends AsThoughEffectImpl {

    WhispersteelDaggerCastFromExileEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "you may cast a creature spell from that player's graveyard this turn";
    }

    private WhispersteelDaggerCastFromExileEffect(final WhispersteelDaggerCastFromExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public WhispersteelDaggerCastFromExileEffect copy() {
        return new WhispersteelDaggerCastFromExileEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        WhispersteelDaggerWatcher watcher = game.getState().getWatcher(WhispersteelDaggerWatcher.class);
        if (watcher != null) {
            watcher.addPlayable(source, getTargetPointer().getFirst(game, source), game);
        }
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        return applyCheck(sourceId, source, affectedControllerId, game, getTargetPointer().getFirst(game, source));
    }

    static boolean applyCheck(UUID sourceId, Ability source, UUID affectedControllerId, Game game, UUID targetId) {
        WhispersteelDaggerWatcher watcher = game.getState().getWatcher(WhispersteelDaggerWatcher.class);
        if (watcher == null || !watcher.checkPermission(
                affectedControllerId, targetId, source, game
        ) || (game.getState().getZone(sourceId) != Zone.GRAVEYARD
                && game.getState().getZone(sourceId) != Zone.STACK)) {
            return false;
        }
        Card card = game.getCard(sourceId);
        return card != null
                && card.getOwnerId().equals(targetId)
                && card.isCreature(game)
                && !card.isLand(game);
    }
}

class WhispersteelDaggerSpendAnyManaEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

    WhispersteelDaggerSpendAnyManaEffect() {
        super(AsThoughEffectType.SPEND_OTHER_MANA, Duration.EndOfTurn, Outcome.Benefit);
        staticText = ", and you may spend mana as though it were mana of any color to cast that spell";
    }

    private WhispersteelDaggerSpendAnyManaEffect(final WhispersteelDaggerSpendAnyManaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public WhispersteelDaggerSpendAnyManaEffect copy() {
        return new WhispersteelDaggerSpendAnyManaEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        return WhispersteelDaggerCastFromExileEffect.applyCheck(
                objectId, source, affectedControllerId, game, getTargetPointer().getFirst(game, source)
        );
    }

    @Override
    public ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game) {
        return mana.getFirstAvailable();
    }
}

class WhispersteelDaggerWatcher extends Watcher {

    private final Map<MageObjectReference, Map<UUID, Map<UUID, Integer>>> morMap = new HashMap<>();

    WhispersteelDaggerWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            if (event.getAdditionalReference() == null) {
                return;
            }
            morMap.computeIfAbsent(event.getAdditionalReference().getApprovingMageObjectReference(), m -> new HashMap<>())
                    .computeIfAbsent(game.getOwnerId(event.getSourceId()), m -> new HashMap<>())
                    .compute(event.getPlayerId(), (u, i) -> i == null ? 0 : Integer.sum(i, -1));
            return;
        }
    }

    @Override
    public void reset() {
        morMap.clear();
        super.reset();
    }

    boolean checkPermission(UUID playerId, UUID ownerId, Ability source, Game game) {
        if (!playerId.equals(source.getControllerId())) {
            return false;
        }
        MageObjectReference mor = new MageObjectReference(
                source.getSourceId(), source.getSourceObjectZoneChangeCounter(), game
        );
        if (!morMap.containsKey(mor)) {
            return false;
        }
        return morMap.get(mor)
                .computeIfAbsent(ownerId, m -> new HashMap<>())
                .getOrDefault(playerId, 0) > 0;
    }

    void addPlayable(Ability source, UUID ownerId, Game game) {
        MageObjectReference mor = new MageObjectReference(
                source.getSourceId(), source.getSourceObjectZoneChangeCounter(), game
        );
        morMap.computeIfAbsent(mor, m -> new HashMap<>())
                .computeIfAbsent(ownerId, m -> new HashMap<>())
                .compute(source.getControllerId(), CardUtil::setOrIncrementValue);
    }
}
