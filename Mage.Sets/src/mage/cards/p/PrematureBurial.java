package mage.cards.p;

import mage.MageObjectReference;
import mage.ObjectColor;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.WatcherScope;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author noahg, xenohedron
 */
public final class PrematureBurial extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(
            "nonblack creature that entered the battlefield since your last turn ended");

    static {
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
        filter.add(PrematureBurialPredicate.instance);
    }

    public PrematureBurial(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Destroy target nonblack creature that entered the battlefield since your last turn ended.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
        this.getSpellAbility().addWatcher(new PrematureBurialWatcher());
    }

    private PrematureBurial(final PrematureBurial card) {
        super(card);
    }

    @Override
    public PrematureBurial copy() {
        return new PrematureBurial(this);
    }
}

enum PrematureBurialPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        return PrematureBurialWatcher.checkEnteredSinceLastTurn(input.getPlayerId(), new MageObjectReference(input.getObject().getId(), game), game);
    }
}

class PrematureBurialWatcher extends Watcher {

    private final Map<UUID, Set<MageObjectReference>> playerToETBMap;

    public PrematureBurialWatcher() {
        super(WatcherScope.GAME);
        this.playerToETBMap = new HashMap<>();
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.END_TURN_STEP_POST) {
            playerToETBMap.put(event.getPlayerId(), new HashSet<>());
        } else if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            Permanent etbPermanent = game.getPermanent(event.getTargetId());
            if (etbPermanent != null) {
                for (UUID player : game.getPlayerList()) {
                    if (!playerToETBMap.containsKey(player)) {
                        playerToETBMap.put(player, new HashSet<>());
                    }
                    playerToETBMap.get(player).add(new MageObjectReference(etbPermanent.getId(), game));
                }
            }
        }
    }

    static boolean checkEnteredSinceLastTurn(UUID player, MageObjectReference mor, Game game) {
        return game
                .getState()
                .getWatcher(PrematureBurialWatcher.class)
                .playerToETBMap
                .getOrDefault(player, Collections.emptySet())
                .contains(mor);
    }

}
