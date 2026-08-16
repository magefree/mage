package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author muz
 */
public final class SuperAdaptoid extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another target creature");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public SuperAdaptoid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ROBOT);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // Super-Adaptoid's power is equal to the number of legendary creatures you control.
        this.addAbility(new SimpleStaticAbility(
            Zone.ALL,
            new SetBasePowerSourceEffect(LegendaryCreaturesYouControlCount.instance)
        ));

        // Whenever Super-Adaptoid enters or attacks, choose another target creature. If that creature has haste and Super-Adaptoid doesn't, put a haste counter on Super-Adaptoid. Do the same for flying, first strike, double strike, deathtouch, indestructible, lifelink, menace, reach, trample, and vigilance.
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(new SuperAdaptoidEffect());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private SuperAdaptoid(final SuperAdaptoid card) {
        super(card);
    }

    @Override
    public SuperAdaptoid copy() {
        return new SuperAdaptoid(this);
    }
}

class SuperAdaptoidEffect extends OneShotEffect {

    private static final List<SuperAdaptoidCounterMapping> COUNTER_MAPPINGS = Arrays.asList(
        new SuperAdaptoidCounterMapping(HasteAbility.class, CounterType.HASTE),
        new SuperAdaptoidCounterMapping(FlyingAbility.class, CounterType.FLYING),
        new SuperAdaptoidCounterMapping(FirstStrikeAbility.class, CounterType.FIRST_STRIKE),
        new SuperAdaptoidCounterMapping(DoubleStrikeAbility.class, CounterType.DOUBLE_STRIKE),
        new SuperAdaptoidCounterMapping(DeathtouchAbility.class, CounterType.DEATHTOUCH),
        new SuperAdaptoidCounterMapping(IndestructibleAbility.class, CounterType.INDESTRUCTIBLE),
        new SuperAdaptoidCounterMapping(LifelinkAbility.class, CounterType.LIFELINK),
        new SuperAdaptoidCounterMapping(MenaceAbility.class, CounterType.MENACE),
        new SuperAdaptoidCounterMapping(ReachAbility.class, CounterType.REACH),
        new SuperAdaptoidCounterMapping(TrampleAbility.class, CounterType.TRAMPLE),
        new SuperAdaptoidCounterMapping(VigilanceAbility.class, CounterType.VIGILANCE)
    );

    SuperAdaptoidEffect() {
        super(Outcome.Benefit);
        staticText = "choose another target creature. If that creature has haste and {this} doesn't, "
                + "put a haste counter on {this}. Do the same for flying, first strike, double strike, "
                + "deathtouch, indestructible, lifelink, menace, reach, trample, and vigilance";
    }

    private SuperAdaptoidEffect(final SuperAdaptoidEffect effect) {
        super(effect);
    }

    @Override
    public SuperAdaptoidEffect copy() {
        return new SuperAdaptoidEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(getTargetPointer().getFirst(game, source));
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (target == null || sourcePermanent == null) {
            return false;
        }
        for (SuperAdaptoidCounterMapping mapping : COUNTER_MAPPINGS) {
            if (mapping.hasAbility(target, game) && !mapping.hasAbility(sourcePermanent, game)) {
                sourcePermanent.addCounters(
                    mapping.getCounterType().createInstance(), source.getControllerId(), source, game
                );
            }
        }
        return true;
    }
}

class SuperAdaptoidCounterMapping {

    private final Class<? extends Ability> abilityClass;
    private final CounterType counterType;

    SuperAdaptoidCounterMapping(Class<? extends Ability> abilityClass, CounterType counterType) {
        this.abilityClass = abilityClass;
        this.counterType = counterType;
    }

    boolean hasAbility(Permanent permanent, Game game) {
        return permanent
            .getAbilities(game)
            .stream()
            .anyMatch(abilityClass::isInstance);
    }

    CounterType getCounterType() {
        return counterType;
    }
}

enum LegendaryCreaturesYouControlCount implements DynamicValue {
    instance;

    private static final FilterControlledCreaturePermanent filter
        = new FilterControlledCreaturePermanent("legendary creatures you control");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game.getBattlefield().count(filter, sourceAbility.getControllerId(), sourceAbility, game);
    }

    @Override
    public LegendaryCreaturesYouControlCount copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "legendary creatures you control";
    }
}
