package mage.cards.k;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.TurnFaceUpTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.card.FaceDownPredicate;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KaustEyesOfTheGlade extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("a creature you control that was turned face up this turn");
    private static final FilterPermanent filter2
            = new FilterControlledCreaturePermanent("face-down attacking creature you control");

    static {
        filter.add(KaustEyesOfTheGladePredicate.instance);
        filter2.add(AttackingPredicate.instance);
        filter2.add(FaceDownPredicate.instance);
    }

    public KaustEyesOfTheGlade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R/W}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRYAD);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever a creature you control that was turned face up this turn deals combat damage to a player, draw a card.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
                new DrawCardSourceControllerEffect(1), filter,
                false, SetTargetPointer.NONE, true
        ), new KaustEyesOfTheGladeWatcher());

        // {T}: Turn target face-down attacking creature you control face up.
        Ability ability = new SimpleActivatedAbility(new TurnFaceUpTargetEffect(), new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter2));
        this.addAbility(ability);
    }

    private KaustEyesOfTheGlade(final KaustEyesOfTheGlade card) {
        super(card);
    }

    @Override
    public KaustEyesOfTheGlade copy() {
        return new KaustEyesOfTheGlade(this);
    }
}

enum KaustEyesOfTheGladePredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        return KaustEyesOfTheGladeWatcher.checkPermanent(input, game);
    }
}

class KaustEyesOfTheGladeWatcher extends Watcher {

    private final Set<MageObjectReference> set = new HashSet<>();

    KaustEyesOfTheGladeWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.TURNED_FACE_UP) {
            return;
        }
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null) {
            set.add(new MageObjectReference(permanent, game));
        }
    }

    @Override
    public void reset() {
        set.clear();
        super.reset();
    }

    static boolean checkPermanent(Permanent permanent, Game game) {
        return game
                .getState()
                .getWatcher(KaustEyesOfTheGladeWatcher.class)
                .set
                .contains(new MageObjectReference(permanent, game));
    }
}
