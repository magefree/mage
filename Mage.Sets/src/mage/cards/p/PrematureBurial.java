package mage.cards.p;

import mage.MageObject;
import mage.MageObjectReference;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.WatcherScope;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.Watcher;

import java.util.*;

/**
 *
 * @author noahg
 */
public final class PrematureBurial extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonblack creature that entered the battlefield since your last turn ended");

    static {
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
    }

    public PrematureBurial(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");
        

        // Destroy target nonblack creature that entered the battlefield since your last turn ended.
        this.getSpellAbility().addEffect(new DestroyTargetEffect().setText("Destroy target nonblack creature that entered the battlefield since your last turn ended."));
        this.getSpellAbility().addTarget(new ETBSinceYourLastTurnTarget(filter));
        this.getSpellAbility().addWatcher(new ETBSinceYourLastTurnWatcher());
    }

    public PrematureBurial(final PrematureBurial card) {
        super(card);
    }

    @Override
    public PrematureBurial copy() {
        return new PrematureBurial(this);
    }
}

class ETBSinceYourLastTurnTarget extends TargetCreaturePermanent {

    public ETBSinceYourLastTurnTarget(FilterCreaturePermanent filter) {
        super(filter);
        this.targetName = "nonblack creature that entered the battlefield since your last turn ended";
    }

    public ETBSinceYourLastTurnTarget(ETBSinceYourLastTurnTarget target){
        super(target);
    }

    @Override
    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game) {
        System.out.println("canTarget called");
        ETBSinceYourLastTurnWatcher watcher = game.getState().getWatcher(ETBSinceYourLastTurnWatcher.class);
        if (watcher != null){
            if (watcher.enteredSinceLastTurn(controllerId, new MageObjectReference(id, game))){
                System.out.println(game.getPermanent(id).getIdName()+" entered since the last turn.");
                return super.canTarget(controllerId, id, source, game);
            }
        }
        return false;
    }

    @Override
    public boolean canChoose(UUID sourceId, UUID sourceControllerId, Game game) {
        MageObject targetSource = game.getObject(sourceId);
        ETBSinceYourLastTurnWatcher watcher = game.getState().getWatcher(ETBSinceYourLastTurnWatcher.class);
        if(targetSource != null) {
            for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, sourceControllerId, sourceId, game)) {
                if (permanent.canBeTargetedBy(targetSource, sourceControllerId, game)) {
                    if (watcher != null && watcher.enteredSinceLastTurn(sourceControllerId, new MageObjectReference(permanent.getId(), game))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public ETBSinceYourLastTurnTarget copy() {
        return new ETBSinceYourLastTurnTarget(this);
    }
}

class ETBSinceYourLastTurnWatcher extends Watcher {

    private final Map<UUID, Set<MageObjectReference>> playerToETBMap;

    public ETBSinceYourLastTurnWatcher() {
        super(WatcherScope.GAME);
        this.playerToETBMap = new HashMap<>();
    }

    public ETBSinceYourLastTurnWatcher(ETBSinceYourLastTurnWatcher watcher) {
        super(watcher);
        this.playerToETBMap = new HashMap<>();
        for (UUID player : watcher.playerToETBMap.keySet()){
            this.playerToETBMap.put(player, new HashSet<>(watcher.playerToETBMap.get(player)));
        }
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.END_TURN_STEP_POST){
            System.out.println("End of turn for "+game.getPlayer(event.getPlayerId()).getName());
            playerToETBMap.put(event.getPlayerId(), new HashSet<>());
        } else if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD){
            Permanent etbPermanent = game.getPermanent(event.getTargetId());
            if (etbPermanent != null){
                System.out.println("nonnull permanent entered: "+etbPermanent.getIdName());
                for (UUID player : game.getPlayerList()){
                    if (!playerToETBMap.containsKey(player)){
                        playerToETBMap.put(player, new HashSet<>());
                    }
                    playerToETBMap.get(player).add(new MageObjectReference(etbPermanent.getBasicMageObject(game), game));
                }
            }
        }
    }

    public boolean enteredSinceLastTurn(UUID player, MageObjectReference mor){
        return playerToETBMap.get(player).contains(mor);
    }

    @Override
    public ETBSinceYourLastTurnWatcher copy() {
        return new ETBSinceYourLastTurnWatcher(this);
    }
}