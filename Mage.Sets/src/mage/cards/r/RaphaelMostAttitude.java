package mage.cards.r;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.WatcherScope;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.util.CardUtil;
import mage.watchers.Watcher;
import mage.abilities.Ability;
import mage.abilities.common.AllianceAbility;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.ExileCardsFromTopOfLibraryControllerEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class RaphaelMostAttitude extends CardImpl {

    public RaphaelMostAttitude(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.NINJA);
        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility());

        // Alliance -- Whenever another creature you control enters, you may exile the top card of your library.
        this.addAbility(new AllianceAbility(new ExileCardsFromTopOfLibraryControllerEffect(1, true)));

        // Whenever Raphael attacks, until end of turn, you may play a card exiled with Raphael.
        this.addAbility(new AttacksTriggeredAbility(new RaphaelMostAttitudeEffect(), false));
    }

    private RaphaelMostAttitude(final RaphaelMostAttitude card) {
        super(card);
    }

    @Override
    public RaphaelMostAttitude copy() {
        return new RaphaelMostAttitude(this);
    }
}

class RaphaelMostAttitudeEffect extends AsThoughEffectImpl {

    RaphaelMostAttitudeEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "until end of turn, you may play a card exiled with {this}.";
    }

    private RaphaelMostAttitudeEffect(final RaphaelMostAttitudeEffect effect) {
        super(effect);
    }

    @Override
    public RaphaelMostAttitudeEffect copy() {
        return new RaphaelMostAttitudeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        RaphaelMostAttitudeWatcher.addPlayable(source, game);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        if (!RaphaelMostAttitudeWatcher.checkPermission(affectedControllerId, source, game)) {
            return false;
        }

        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source));
        if (exileZone == null || !exileZone.contains(sourceId)) {
            return false;
        }
        CardUtil.makeCardPlayable(game, source, exileZone.get(sourceId, game), false, Duration.EndOfTurn, false);
        return true;
    }
}

class RaphaelMostAttitudeWatcher extends Watcher {

    private final Map<MageObjectReference, Map<UUID, Integer>> morMap = new HashMap<>();

    RaphaelMostAttitudeWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST || event.getType() == GameEvent.EventType.PLAY_LAND) {
            if (event.getApprovingObject() == null) {
                return;
            }
            morMap.computeIfAbsent(event.getApprovingObject().getApprovingMageObjectReference(), m -> new HashMap<>())
                    .compute(event.getPlayerId(), (u, i) -> i == null ? 0 : Integer.sum(i, -1));
        }
    }

    @Override
    public void reset() {
        morMap.clear();
        super.reset();
    }

    static boolean checkPermission(UUID playerId, Ability source, Game game) {
        if (!playerId.equals(source.getControllerId())) {
            return false;
        }
        MageObjectReference mor = new MageObjectReference(source);
        RaphaelMostAttitudeWatcher watcher = game.getState().getWatcher(RaphaelMostAttitudeWatcher.class);
        return watcher.morMap.containsKey(mor)
                && watcher.morMap.get(mor).getOrDefault(playerId, 0) > 0;
    }

    static void addPlayable(Ability source, Game game) {
        MageObjectReference mor = new MageObjectReference(source);
        game.getState()
                .getWatcher(RaphaelMostAttitudeWatcher.class)
                .morMap
                .computeIfAbsent(mor, m -> new HashMap<>())
                .compute(source.getControllerId(), CardUtil::setOrIncrementValue);
    }
}
