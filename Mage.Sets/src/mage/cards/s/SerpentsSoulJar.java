package mage.cards.s;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SerpentsSoulJar extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.ELF, "an Elf you control");

    public SerpentsSoulJar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{B}");

        // Whenever an Elf you control dies, exile it.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new SerpentsSoulJarExileEffect(), false, filter, true
        ));

        // {T}, Pay 2 life: Until end of turn, you may cast a creature spell from among cards exiled with Serpent's Soul-Jar.
        Ability ability = new SimpleActivatedAbility(new SerpentsSoulJarCastFromExileEffect(), new TapSourceCost());
        ability.addCost(new PayLifeCost(2));
        this.addAbility(ability, new SerpentsSoulJarWatcher());
    }

    private SerpentsSoulJar(final SerpentsSoulJar card) {
        super(card);
    }

    @Override
    public SerpentsSoulJar copy() {
        return new SerpentsSoulJar(this);
    }
}

class SerpentsSoulJarExileEffect extends OneShotEffect {

    SerpentsSoulJarExileEffect() {
        super(Outcome.Benefit);
        staticText = "exile it";
    }

    private SerpentsSoulJarExileEffect(final SerpentsSoulJarExileEffect effect) {
        super(effect);
    }

    @Override
    public SerpentsSoulJarExileEffect copy() {
        return new SerpentsSoulJarExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        Card card = game.getCard(targetPointer.getFirst(game, source));
        if (player == null || permanent == null || card == null) {
            return false;
        }
        player.moveCardsToExile(
                card, source, game, true,
                CardUtil.getExileZoneId(game, source),
                CardUtil.getSourceName(game, source)
        );
        return true;
    }
}

class SerpentsSoulJarCastFromExileEffect extends AsThoughEffectImpl {

    SerpentsSoulJarCastFromExileEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "until end of turn, you may cast a creature spell from among cards exiled with {this}";
    }

    private SerpentsSoulJarCastFromExileEffect(final SerpentsSoulJarCastFromExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public SerpentsSoulJarCastFromExileEffect copy() {
        return new SerpentsSoulJarCastFromExileEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        SerpentsSoulJarWatcher.addPlayable(source, game);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        if (!SerpentsSoulJarWatcher.checkPermission(affectedControllerId, source, game)) {
            return false;
        }
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source));
        if (exileZone == null || !exileZone.contains(sourceId)) {
            return false;
        }
        Card card = game.getCard(sourceId);
        return card != null && card.isCreature(game) && !card.isLand(game);
    }
}

class SerpentsSoulJarWatcher extends Watcher {

    private final Map<MageObjectReference, Map<UUID, Integer>> morMap = new HashMap<>();

    SerpentsSoulJarWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            if (event.getAdditionalReference() == null) {
                return;
            }
            morMap.computeIfAbsent(event.getAdditionalReference().getApprovingMageObjectReference(), m -> new HashMap<>())
                    .compute(event.getPlayerId(), (u, i) -> i == null ? 0 : Integer.sum(i, -1));
            return;
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
        SerpentsSoulJarWatcher watcher = game.getState().getWatcher(SerpentsSoulJarWatcher.class);
        return watcher
                .morMap
                .containsKey(mor)
                && watcher
                .morMap
                .get(mor)
                .getOrDefault(playerId, 0) > 0;
    }

    static void addPlayable(Ability source, Game game) {
        MageObjectReference mor = new MageObjectReference(source);
        game.getState()
                .getWatcher(SerpentsSoulJarWatcher.class)
                .morMap
                .computeIfAbsent(mor, m -> new HashMap<>())
                .compute(source.getControllerId(), CardUtil::setOrIncrementValue);
    }
}
