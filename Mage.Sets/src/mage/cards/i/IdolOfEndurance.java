package mage.cards.i;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
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
public final class IdolOfEndurance extends CardImpl {

    public IdolOfEndurance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{W}");

        // When Idol of Endurance enters the battlefield, exile all creature cards with converted mana cost 3 or less from your graveyard until Idol of Endurance leaves the battlefield.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new IdolOfEnduranceExileEffect()));

        // {1}{W}, {T}: Until end of turn, you may cast a creature spell from among the cards exiled with Idol of Endurance without paying its mana cost.
        Ability ability = new SimpleActivatedAbility(
                new IdolOfEnduranceCastFromExileEffect(), new ManaCostsImpl<>("{1}{W}")
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability, new IdolOfEnduranceWatcher());
    }

    private IdolOfEndurance(final IdolOfEndurance card) {
        super(card);
    }

    @Override
    public IdolOfEndurance copy() {
        return new IdolOfEndurance(this);
    }
}

class IdolOfEnduranceExileEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCreatureCard();

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    IdolOfEnduranceExileEffect() {
        super(Outcome.Benefit);
        staticText = "exile all creature cards with mana value 3 or less from your graveyard until {this} leaves the battlefield";
    }

    private IdolOfEnduranceExileEffect(final IdolOfEnduranceExileEffect effect) {
        super(effect);
    }

    @Override
    public IdolOfEnduranceExileEffect copy() {
        return new IdolOfEnduranceExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (player == null || permanent == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getGraveyard().getCards(filter, game));
        player.moveCardsToExile(cards.getCards(game), source, game, true, CardUtil.getExileZoneId(game, source), CardUtil.getSourceName(game, source));
        game.addDelayedTriggeredAbility(new IdolOfEnduranceDelayedTrigger(), source);
        return true;
    }
}

class IdolOfEnduranceDelayedTrigger extends DelayedTriggeredAbility {

    IdolOfEnduranceDelayedTrigger() {
        super(new IdolOfEnduranceLeaveEffect(), Duration.Custom, true, false);
        this.usesStack = false;
        this.setRuleVisible(false);
    }

    private IdolOfEnduranceDelayedTrigger(final IdolOfEnduranceDelayedTrigger ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(this.getSourceId())) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.getFromZone() == Zone.BATTLEFIELD) {
                return true;
            }
        }
        return false;
    }

    @Override
    public IdolOfEnduranceDelayedTrigger copy() {
        return new IdolOfEnduranceDelayedTrigger(this);
    }
}

class IdolOfEnduranceLeaveEffect extends OneShotEffect {

    IdolOfEnduranceLeaveEffect() {
        super(Outcome.Benefit);
    }

    private IdolOfEnduranceLeaveEffect(final IdolOfEnduranceLeaveEffect effect) {
        super(effect);
    }

    @Override
    public IdolOfEnduranceLeaveEffect copy() {
        return new IdolOfEnduranceLeaveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source));
        return exileZone != null
                && !exileZone.isEmpty()
                && player.moveCards(
                exileZone, Zone.GRAVEYARD, source, game
        );
    }
}

class IdolOfEnduranceCastFromExileEffect extends AsThoughEffectImpl {

    IdolOfEnduranceCastFromExileEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "until end of turn, you may cast a creature spell from among cards exiled with {this} without paying its mana cost";
    }

    private IdolOfEnduranceCastFromExileEffect(final IdolOfEnduranceCastFromExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public IdolOfEnduranceCastFromExileEffect copy() {
        return new IdolOfEnduranceCastFromExileEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        IdolOfEnduranceWatcher.addPlayable(source, game);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        if (!IdolOfEnduranceWatcher.checkPermission(affectedControllerId, source, game)) {
            return false;
        }
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source));
        if (exileZone == null || !exileZone.contains(sourceId)) {
            return false;
        }
        Card card = game.getCard(sourceId);
        if (card == null || !card.isCreature(game) || card.isLand(game)) {
            return false;
        }
        return allowCardToPlayWithoutMana(sourceId, source, affectedControllerId, game);
    }
}

class IdolOfEnduranceWatcher extends Watcher {

    private final Map<MageObjectReference, Map<UUID, Integer>> morMap = new HashMap<>();

    IdolOfEnduranceWatcher() {
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
        IdolOfEnduranceWatcher watcher = game.getState().getWatcher(IdolOfEnduranceWatcher.class);
        MageObjectReference mor = new MageObjectReference(source);
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
                .getWatcher(IdolOfEnduranceWatcher.class)
                .morMap
                .computeIfAbsent(mor, m -> new HashMap<>())
                .compute(source.getControllerId(), CardUtil::setOrIncrementValue);
    }
}
