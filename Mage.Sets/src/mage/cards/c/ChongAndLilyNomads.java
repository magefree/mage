package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChongAndLilyNomads extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.BARD, "Bards you control");

    public ChongAndLilyNomads(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BARD);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever one or more Bards you control attack, choose one --
        // * Put a lore counter on each of any number of target Sagas you control.
        Ability ability = new AttacksWithCreaturesTriggeredAbility(
                new AddCountersTargetEffect(CounterType.LORE.createInstance()), 1, filter
        );
        ability.addTarget(new TargetPermanent(0, Integer.MAX_VALUE, ChongAndLilyNomadsValue.getFilter()));

        // * Creatures you control get +1/+0 until end of turn for each lore counter among Sagas you control.
        ability.addMode(new Mode(new BoostControlledEffect(
                ChongAndLilyNomadsValue.instance, StaticValue.get(0), Duration.WhileOnBattlefield
        )));
        this.addAbility(ability.addHint(ChongAndLilyNomadsValue.getHint()));
    }

    private ChongAndLilyNomads(final ChongAndLilyNomads card) {
        super(card);
    }

    @Override
    public ChongAndLilyNomads copy() {
        return new ChongAndLilyNomads(this);
    }
}

enum ChongAndLilyNomadsValue implements DynamicValue {
    instance;
    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.SAGA, "Sagas you control");

    public static FilterPermanent getFilter() {
        return filter;
    }

    private static final Hint hint = new ValueHint("Lore counters among Sagas you control", instance);

    public static Hint getHint() {
        return hint;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game
                .getBattlefield()
                .getActivePermanents(filter, sourceAbility.getControllerId(), sourceAbility, game)
                .stream()
                .mapToInt(permanent -> permanent.getCounters(game).getCount(CounterType.LORE))
                .sum();
    }

    @Override
    public ChongAndLilyNomadsValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "lore counter among Sagas you control";
    }

    @Override
    public String toString() {
        return "1";
    }
}
