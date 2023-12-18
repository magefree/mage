package mage.cards.j;

import mage.MageObjectReference;
import mage.ObjectColor;
import mage.abilities.common.CastOnlyDuringPhaseStepSourceAbility;
import mage.abilities.condition.common.AfterCombatCondition;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.WatcherScope;
import mage.counters.CounterType;
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
 * @author Cguy7777
 */
public final class JabarisInfluence extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonartifact, nonblack creature that attacked you this turn");

    static {
        filter.add(Predicates.not(CardType.ARTIFACT.getPredicate()));
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
        filter.add(JabarisInfluencePredicate.instance);
    }

    public JabarisInfluence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{W}{W}");

        // Cast Jabari's Influence only after combat.
        this.addAbility(new CastOnlyDuringPhaseStepSourceAbility(
                null, null, AfterCombatCondition.instance, "Cast this spell only after combat"));

        // Gain control of target nonartifact, nonblack creature that attacked you this turn and put a -1/-0 counter on it.
        this.getSpellAbility().addEffect(new GainControlTargetEffect(Duration.Custom));
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.M1M0.createInstance()).setText("and put a -1/-0 counter on it"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
        this.getSpellAbility().addWatcher(new JabarisInfluenceWatcher());
    }

    private JabarisInfluence(final JabarisInfluence card) {
        super(card);
    }

    @Override
    public JabarisInfluence copy() {
        return new JabarisInfluence(this);
    }
}

enum JabarisInfluencePredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        Permanent permanent = input.getObject();

        JabarisInfluenceWatcher watcher = game.getState().getWatcher(JabarisInfluenceWatcher.class);
        return watcher.checkIfCreatureAttackedYouThisTurn(permanent, input.getPlayerId(), game);
    }
}

class JabarisInfluenceWatcher extends Watcher {

    private final Map<MageObjectReference, Set<UUID>> attackedThisTurnCreatures = new HashMap<>();

    public JabarisInfluenceWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ATTACKER_DECLARED) {
            Permanent permanent = game.getPermanent(event.getSourceId());
            if (permanent != null) {
                attackedThisTurnCreatures.computeIfAbsent(
                        new MageObjectReference(event.getSourceId(), game), u -> new HashSet<>()
                ).add(event.getTargetId());
            }
        }
    }

    boolean checkIfCreatureAttackedYouThisTurn(Permanent permanent, UUID playerId, Game game) {
        MageObjectReference mor = new MageObjectReference(permanent, game);
        return attackedThisTurnCreatures.containsKey(mor) && attackedThisTurnCreatures.get(mor).contains(playerId);
    }

    @Override
    public void reset() {
        super.reset();
        attackedThisTurnCreatures.clear();
    }
}
