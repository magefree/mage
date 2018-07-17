
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.KickerAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;

/**
 *
 * @author emerald000
 */
public final class KangeeAerieKeeper extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Bird creatures");

    static {
        filter.add(new SubtypePredicate(SubType.BIRD));
        filter.add(new AnotherPredicate());
    }

    public KangeeAerieKeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{U}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Kicker {X}{2}
        this.addAbility(new KickerAbility("{2}{X}"));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Kangee, Aerie Keeper enters the battlefield, if it was kicked, put X feather counters on it.
        TriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new AddCountersSourceEffect(CounterType.FEATHER.createInstance(), new KangeeAerieKeeperGetKickerXValue(), true));
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, KickedCondition.instance, "When {this} enters the battlefield, if it was kicked, put X feather counters on it."));

        // Other Bird creatures get +1/+1 for each feather counter on Kangee, Aerie Keeper.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(new CountersSourceCount(CounterType.FEATHER), new CountersSourceCount(CounterType.FEATHER), Duration.WhileOnBattlefield, filter, true, "Other Bird creatures get +1/+1 for each feather counter on {this}.")));
    }

    public KangeeAerieKeeper(final KangeeAerieKeeper card) {
        super(card);
    }

    @Override
    public KangeeAerieKeeper copy() {
        return new KangeeAerieKeeper(this);
    }
}

class KangeeAerieKeeperGetKickerXValue implements DynamicValue {

    public KangeeAerieKeeperGetKickerXValue() {
    }

    @Override
    public int calculate(Game game, Ability source, Effect effect) {
        int count = 0;
        Card card = game.getCard(source.getSourceId());
        if (card != null) {
            for (Ability ability : card.getAbilities()) {
                if (ability instanceof KickerAbility) {
                    count += ((KickerAbility) ability).getXManaValue();
                }
            }
        }
        return count;
    }

    @Override
    public KangeeAerieKeeperGetKickerXValue copy() {
        return new KangeeAerieKeeperGetKickerXValue();
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "X";
    }
}
