package mage.cards.m;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.PhyrexianMiteToken;

/**
 * @author TheElk801
 */
public final class MiteOverseer extends CardImpl {

    public MiteOverseer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // As long as it's your turn, creature tokens you control get +1/+0 and have first strike.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(new BoostControlledEffect(
                1, 0, Duration.WhileOnBattlefield, StaticFilters.FILTER_CREATURE_TOKEN
        ), MyTurnCondition.instance, "as long as it's your turn, creature tokens you control get +1/+0"));
        ability.addEffect(new ConditionalContinuousEffect(new GainAbilityControlledEffect(
                FirstStrikeAbility.getInstance(), Duration.WhileOnBattlefield, StaticFilters.FILTER_CREATURE_TOKEN
        ), MyTurnCondition.instance, "and have first strike"));
        this.addAbility(ability.addHint(MyTurnHint.instance));

        // {3}{W/P}: Create a 1/1 colorless Phyrexian Mite artifact creature token with toxic 1 and "This creature can't block."
        this.addAbility(new SimpleActivatedAbility(
                new CreateTokenEffect(new PhyrexianMiteToken()), new ManaCostsImpl<>("{3}{W/P}")
        ));
    }

    private MiteOverseer(final MiteOverseer card) {
        super(card);
    }

    @Override
    public MiteOverseer copy() {
        return new MiteOverseer(this);
    }
}
