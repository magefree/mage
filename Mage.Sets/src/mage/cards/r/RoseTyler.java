package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.DoctorsCompanionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterPermanentOrSuspendedCard;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RoseTyler extends CardImpl {

    private static final DynamicValue xValue = new CountersSourceCount(CounterType.TIME);

    public RoseTyler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Rose Tyler gets +1/+1 for each time counter on it.
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(xValue, xValue, Duration.WhileOnBattlefield)
                .setText("{this} gets +1/+1 for each time counter on it")));

        // Bad Wolf -- Whenever Rose Tyler attacks, put a time counter on it for each suspended card you own and each other permanent you control with a time counter on it.
        this.addAbility(new AttacksTriggeredAbility(new AddCountersSourceEffect(
                CounterType.TIME.createInstance(), RoseTylerValue.instance, true
        )).addHint(RoseTylerValue.getHint()).withFlavorWord("Bad Wolf"));

        // Doctor's companion
        this.addAbility(DoctorsCompanionAbility.getInstance());
    }

    private RoseTyler(final RoseTyler card) {
        super(card);
    }

    @Override
    public RoseTyler copy() {
        return new RoseTyler(this);
    }
}

enum RoseTylerValue implements DynamicValue {
    instance;
    private static final FilterPermanentOrSuspendedCard filter = new FilterPermanentOrSuspendedCard();

    static {
        filter.getPermanentFilter().add(AnotherPredicate.instance);
        filter.getPermanentFilter().add(CounterType.TIME.getPredicate());
        filter.getPermanentFilter().add(TargetController.YOU.getControllerPredicate());
    }

    private static final Hint hint = new ValueHint(
            "Suspended cards you own and other permanents you control with time counters on them", instance
    );

    public static Hint getHint() {
        return hint;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game
                .getBattlefield()
                .count(filter.getPermanentFilter(), sourceAbility.getControllerId(), sourceAbility, game)
                + game
                .getExile()
                .getAllCards(game, sourceAbility.getControllerId())
                .stream()
                .filter(card -> card.isOwnedBy(sourceAbility.getControllerId()))
                .mapToInt(card -> filter.getCardFilter().match(card, game) ? 1 : 0)
                .sum();
    }

    @Override
    public RoseTylerValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "suspended card you own and each other permanent you control with a time counter on it";
    }

    @Override
    public String toString() {
        return "";
    }
}
