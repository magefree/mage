package mage.cards.l;

import mage.MageIdentifier;
import mage.MageInt;
import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author TheElk801
 */
public final class LockeTreasureHunter extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures with greater power");

    static {
        filter.add(LockeTreasureHunterPredicate.instance);
    }

    public LockeTreasureHunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Locke can't be blocked by creatures with greater power.
        this.addAbility(new SimpleStaticAbility(new CantBeBlockedByCreaturesSourceEffect(filter, Duration.WhileOnBattlefield)));

        // Mug -- Whenever Locke attacks, each player mills a card. If a land card was milled this way, create a Treasure token. Until end of turn, you may cast a spell from among those cards.
        this.addAbility(new AttacksTriggeredAbility(new LockeTreasureHunterEffect())
                .withFlavorWord("Mug")
                .setIdentifier(MageIdentifier.LockeTreasureHunterWatcher), new LockeTreasureHunterWatcher());
    }

    private LockeTreasureHunter(final LockeTreasureHunter card) {
        super(card);
    }

    @Override
    public LockeTreasureHunter copy() {
        return new LockeTreasureHunter(this);
    }

    public static Ability makeTestAbility() {
        Ability ability = new SimpleActivatedAbility(
                new LockeTreasureHunterEffect(), new GenericManaCost(0)
        ).setIdentifier(MageIdentifier.LockeTreasureHunterWatcher);
        ability.addWatcher(new LockeTreasureHunterWatcher());
        return ability;
    }
}

enum LockeTreasureHunterPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        return Optional
                .ofNullable(input.getSource().getSourcePermanentIfItStillExists(game))
                .map(MageObject::getPower)
                .map(MageInt::getValue)
                .map(x -> x < input.getObject().getPower().getValue())
                .orElse(false);
    }
}

class LockeTreasureHunterEffect extends OneShotEffect {

    LockeTreasureHunterEffect() {
        super(Outcome.Benefit);
        staticText = "each player mills a card. If a land card was milled this way, " +
                "create a Treasure token. Until end of turn, you may cast a spell from among those cards";
    }

    private LockeTreasureHunterEffect(final LockeTreasureHunterEffect effect) {
        super(effect);
    }

    @Override
    public LockeTreasureHunterEffect copy() {
        return new LockeTreasureHunterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Cards cards = new CardsImpl();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                cards.addAllCards(player.millCards(1, source, game).getCards(game));
            }
        }
        if (cards.isEmpty()) {
            return false;
        }
        if (cards.count(StaticFilters.FILTER_CARD_LAND, game) > 0) {
            new TreasureToken().putOntoBattlefield(1, game, source);
        }
        LockeTreasureHunterWatcher.saveCards(cards, game, source);
        return true;
    }
}

class LockeTreasureHunterWatcher extends Watcher {

    private static class LockeTreasureHunterCondition implements Condition {
        private final UUID permissionId;

        LockeTreasureHunterCondition(UUID permissionId) {
            this.permissionId = permissionId;
        }

        @Override
        public boolean apply(Game game, Ability source) {
            return game
                    .getState()
                    .getWatcher(LockeTreasureHunterWatcher.class)
                    .checkPermission(permissionId, source, game);
        }
    }

    // Maps cards to the specific instance that gave them permission to be cast
    private final Map<MageObjectReference, UUID> morMap = new HashMap<>();

    // Maps permissions to the players who can use them
    private static final Map<UUID, UUID> playerPermissionMap = new HashMap<>();

    // Tracks permissions which have already been used
    private final Set<UUID> usedSet = new HashSet<>();

    LockeTreasureHunterWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.SPELL_CAST
                || !event.hasApprovingIdentifier(MageIdentifier.LockeTreasureHunterWatcher)) {
            return;
        }
        Optional.ofNullable(event)
                .map(GameEvent::getTargetId)
                .map(game::getSpell)
                .map(spell -> new MageObjectReference(spell.getMainCard(), game, -1))
                .map(mor -> morMap.getOrDefault(mor, null))
                .ifPresent(usedSet::add);
    }

    @Override
    public void reset() {
        super.reset();
        morMap.clear();
        playerPermissionMap.clear();
        usedSet.clear();
    }

    static void saveCards(Cards cards, Game game, Ability source) {
        game.getState()
                .getWatcher(LockeTreasureHunterWatcher.class)
                .handleSaveCards(cards, game, source);
    }

    private void handleSaveCards(Cards cards, Game game, Ability source) {
        UUID permissionId = UUID.randomUUID();
        playerPermissionMap.put(permissionId, source.getControllerId());
        Condition condition = new LockeTreasureHunterCondition(permissionId);
        for (Card card : cards.getCards(game)) {
            morMap.put(new MageObjectReference(card, game), permissionId);
            CardUtil.makeCardPlayable(
                    game, source, card, true, Duration.EndOfTurn,
                    false, source.getControllerId(), condition
            );
        }
    }

    private boolean checkPermission(UUID permissionId, Ability source, Game game) {
        return !usedSet.contains(permissionId)
                && source.isControlledBy(playerPermissionMap.getOrDefault(permissionId, null));
    }
}
