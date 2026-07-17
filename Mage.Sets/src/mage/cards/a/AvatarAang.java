package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.*;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FirebendingAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AvatarAang extends TransformingDoubleFacedCard {

    private static final FilterCard filter = new FilterCard("spells");

    public AvatarAang(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.AVATAR, SubType.ALLY}, "{R}{G}{W}{U}",
                "Aang, Master of Elements",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.AVATAR, SubType.ALLY}, "");

        this.getLeftHalfCard().setPT(4, 4);
        this.getRightHalfCard().setPT(6, 6);

        // Flying
        this.getLeftHalfCard().addAbility(FlyingAbility.getInstance());

        // Firebending 2
        this.getLeftHalfCard().addAbility(new FirebendingAbility(2));

        // Whenever you waterbend, earthbend, firebend, or airbend, draw a card. Then if you've done all four this turn, transform Avatar Aang.
        this.getLeftHalfCard().addAbility(new AvatarAangTriggeredAbility());

        // Aang, Master of Elements
        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // Spells you cast cost {W}{U}{B}{R}{G} less to cast.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(
                filter, new ManaCostsImpl<>("{W}{U}{B}{R}{G}"), StaticValue.get(1), true
        )));

        // At the beginning of each upkeep, you may transform Aang, Master of Elements. If you do, you gain 4 life, draw four cards, put four +1/+1 counters on him, and he deals 4 damage to each opponent.
        this.getRightHalfCard().addAbility(new BeginningOfUpkeepTriggeredAbility(
                TargetController.ANY,
                new DoIfCostPaid(new GainLifeEffect(4), new AangMasterOfElementsCost())
                        .addEffect(new DrawCardSourceControllerEffect(4).concatBy(","))
                        .addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance(4))
                                .setText(", put four +1/+1 counters on him"))
                        .addEffect(new DamagePlayersEffect(4, TargetController.OPPONENT)
                                .setText(", and he deals 4 damage to each opponent")),
                false
        ));
    }

    private AvatarAang(final AvatarAang card) {
        super(card);
    }

    @Override
    public AvatarAang copy() {
        return new AvatarAang(this);
    }
}

class AvatarAangTriggeredAbility extends TriggeredAbilityImpl {

    private enum AvatarAangCondition implements Condition {
        instance;

        @Override
        public boolean apply(Game game, Ability source) {
            return AvatarAangWatcher.checkPlayer(game, source);
        }
    }

    AvatarAangTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1));
        this.addEffect(new ConditionalOneShotEffect(
                new TransformSourceEffect(), AvatarAangCondition.instance,
                "Then if you've done all four this turn, transform {this}"
        ));
        this.setTriggerPhrase("Whenever you waterbend, earthbend, firebend, or airbend, ");
        this.addWatcher(new AvatarAangWatcher());
    }

    private AvatarAangTriggeredAbility(final AvatarAangTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AvatarAangTriggeredAbility copy() {
        return new AvatarAangTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case EARTHBENDED:
            case AIRBENDED:
            case FIREBENDED:
            case WATERBENDED:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return isControlledBy(event.getPlayerId());
    }
}

class AvatarAangWatcher extends Watcher {

    private final Set<UUID> earthSet = new HashSet<>();
    private final Set<UUID> airSet = new HashSet<>();
    private final Set<UUID> fireSet = new HashSet<>();
    private final Set<UUID> waterSet = new HashSet<>();

    AvatarAangWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        switch (event.getType()) {
            case EARTHBENDED:
                earthSet.add(event.getPlayerId());
                return;
            case AIRBENDED:
                airSet.add(event.getPlayerId());
                return;
            case FIREBENDED:
                fireSet.add(event.getPlayerId());
                return;
            case WATERBENDED:
                waterSet.add(event.getPlayerId());
        }
    }

    @Override
    public void reset() {
        super.reset();
        earthSet.clear();
        airSet.clear();
        fireSet.clear();
        waterSet.clear();
    }

    private boolean checkPlayer(UUID playerId) {
        return earthSet.contains(playerId)
                && airSet.contains(playerId)
                && fireSet.contains(playerId)
                && waterSet.contains(playerId);
    }

    static boolean checkPlayer(Game game, Ability source) {
        return game.getState().getWatcher(AvatarAangWatcher.class).checkPlayer(source.getControllerId());
    }
}

class AangMasterOfElementsCost extends CostImpl {

    AangMasterOfElementsCost() {
        super();
        text = "transform {this}";
    }

    private AangMasterOfElementsCost(final AangMasterOfElementsCost cost) {
        super(cost);
    }

    @Override
    public AangMasterOfElementsCost copy() {
        return new AangMasterOfElementsCost(this);
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return Optional
                .ofNullable(source.getSourcePermanentIfItStillExists(game))
                .filter(Card::isTransformable)
                .isPresent();
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        paid = Optional
                .ofNullable(source.getSourcePermanentIfItStillExists(game))
                .filter(permanent -> permanent.transform(source, game))
                .isPresent();
        return paid;
    }
}
