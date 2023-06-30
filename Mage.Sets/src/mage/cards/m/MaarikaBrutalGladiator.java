package mage.cards.m;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToACreatureTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.effects.common.combat.MustBeBlockedByAtLeastOneSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MaarikaBrutalGladiator extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a creature");

    static {
        filter.add(MaarikaBrutalGladiatorWatcher::checkPermanent);
    }

    public MaarikaBrutalGladiator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(7);
        this.toughness = new MageInt(4);

        // Zangief, the Red Cyclone must be blocked if able.
        this.addAbility(new SimpleStaticAbility(new MustBeBlockedByAtLeastOneSourceEffect(Duration.WhileOnBattlefield)));

        // Iron Muscle—As long as it's your turn, Zangief has indestructible.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(IndestructibleAbility.getInstance()),
                MyTurnCondition.instance, "as long as it's your turn, {this} has indestructible"
        )).withFlavorWord("Iron Muscle"));

        // Spinning Piledriver—Whenever Zangief deals damage to a creature, if that creature was dealt excess damage this turn, that creature's controller sacrifices a noncreature, nonland permanent.
        this.addAbility(new DealsDamageToACreatureTriggeredAbility(
                new MaarikaBrutalGladiatorEffect(), false, false, true, filter
        ).withFlavorWord("Spinning Piledriver"), new MaarikaBrutalGladiatorWatcher());
    }

    private MaarikaBrutalGladiator(final MaarikaBrutalGladiator card) {
        super(card);
    }

    @Override
    public MaarikaBrutalGladiator copy() {
        return new MaarikaBrutalGladiator(this);
    }
}

class MaarikaBrutalGladiatorEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterNonlandPermanent("noncreature, nonland permanent");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    MaarikaBrutalGladiatorEffect() {
        super(Outcome.Benefit);
        staticText = "if that creature was dealt excess damage this turn, " +
                "that creature's controller sacrifices a noncreature, nonland permanent";
    }

    private MaarikaBrutalGladiatorEffect(final MaarikaBrutalGladiatorEffect effect) {
        super(effect);
    }

    @Override
    public MaarikaBrutalGladiatorEffect copy() {
        return new MaarikaBrutalGladiatorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        return new SacrificeEffect(filter, 1, "")
                .setTargetPointer(new FixedTarget(permanent.getControllerId(), game))
                .apply(game, source);
    }
}

class MaarikaBrutalGladiatorWatcher extends Watcher {

    private final Set<MageObjectReference> morSet = new HashSet<>();

    MaarikaBrutalGladiatorWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_PERMANENT
                && ((DamagedEvent) event).getExcess() >= 1) {
            morSet.add(new MageObjectReference(event.getTargetId(), game));
        }
    }

    @Override
    public void reset() {
        super.reset();
        morSet.clear();
    }

    static boolean checkPermanent(Permanent input, Game game) {
        return game
                .getState()
                .getWatcher(MaarikaBrutalGladiatorWatcher.class)
                .morSet
                .stream()
                .anyMatch(mor -> mor.refersTo(input, game));
    }
}
