package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.GreatestAmongPermanentsValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.token.Wurm44Token;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BaruWurmspeaker extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent(SubType.WURM, "Wurms you control");
    static final GreatestAmongPermanentsValue xValue = new GreatestAmongPermanentsValue(GreatestAmongPermanentsValue.Quality.Power, filter);
    private static final Hint hint = xValue.getHint();

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
                TrampleAbility.getInstance(), Duration.WhileOnBattlefield, filter
        ).setText("and have trample"));
        this.addAbility(ability);

        // {7}{G}, {T}: Create a 4/4 green Wurm creature token. This ability costs {X} less to activate, where X is the greatest power among Wurms you control.
        ability = new SimpleActivatedAbility(new CreateTokenEffect(new Wurm44Token()), new ManaCostsImpl<>("{7}{G}"));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new InfoEffect("this ability costs {X} less to activate, " +
                "where X is the greatest power among Wurms you control"));
        ability.setCostAdjuster(BaruWurmspeakerAdjuster.instance);
        ability.addHint(hint);
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

enum BaruWurmspeakerAdjuster implements CostAdjuster {
    instance;

    @Override
    public void reduceCost(Ability ability, Game game) {
        int value = BaruWurmspeaker.xValue.calculate(game, ability, null);
        if (value > 0) {
            CardUtil.reduceCost(ability, value);
        }
    }
}
