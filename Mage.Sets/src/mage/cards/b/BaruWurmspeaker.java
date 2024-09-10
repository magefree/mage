package mage.cards.b;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.token.Wurm44Token;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BaruWurmspeaker extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.WURM, "Wurms");
    private static final FilterPermanent filter2 = new FilterPermanent(SubType.WURM, "");
    private static final Hint hint = new ValueHint(
            "Highest power among Wurms you control", BaruWurmspeakerValue.instance
    );

    public BaruWurmspeaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Wurms you control get +2/+2 and have trample.
        Ability ability = new SimpleStaticAbility(new BoostControlledEffect(
                2, 2, Duration.WhileOnBattlefield, filter, false
        ));
        ability.addEffect(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.WhileOnBattlefield, filter2
        ).setText("and have trample"));
        this.addAbility(ability);

        // {7}{G}, {T}: Create a 4/4 green Wurm creature token. This ability costs {X} less to activate, whre X is the greatest power among Wurms you control.
        ability = new SimpleActivatedAbility(new CreateTokenEffect(new Wurm44Token()), new ManaCostsImpl<>("{7}{G}"));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new InfoEffect("this ability costs {X} less to activate, " +
                "where X is the greatest power among Wurms you control"));
        ability.setCostAdjuster(BaruWurmspeakerAdjuster.instance);
        this.addAbility(ability);
    }

    private BaruWurmspeaker(final BaruWurmspeaker card) {
        super(card);
    }

    @Override
    public BaruWurmspeaker copy() {
        return new BaruWurmspeaker(this);
    }
}

enum BaruWurmspeakerValue implements DynamicValue {
    instance;
    private static final FilterPermanent filter = new FilterControlledCreaturePermanent(SubType.WURM);

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game
                .getBattlefield()
                .getActivePermanents(filter, sourceAbility.getControllerId(), sourceAbility, game)
                .stream()
                .map(MageObject::getPower)
                .mapToInt(MageInt::getValue)
                .max()
                .orElse(0);
    }

    @Override
    public BaruWurmspeakerValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public String toString() {
        return "";
    }
}

enum BaruWurmspeakerAdjuster implements CostAdjuster {
    instance;

    @Override
    public void adjustCosts(Ability ability, Game game) {
        int value = BaruWurmspeakerValue.instance.calculate(game, ability, null);
        if (value > 0) {
            CardUtil.reduceCost(ability, value);
        }
    }
}
