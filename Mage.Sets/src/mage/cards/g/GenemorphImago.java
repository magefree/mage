package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessTargetEffect;
import mage.abilities.hint.common.LandsYouControlHint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GenemorphImago extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            StaticFilters.FILTER_LANDS, ComparisonType.MORE_THAN, 5, true
    );

    public GenemorphImago(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{U}");

        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Landfall -- Whenever a land you control enters, target creature has base power and toughness 3/3 until end of turn. If you control six or more lands, that creature has base power and toughness 6/6 until end of turn instead.
        Ability ability = new LandfallAbility(new ConditionalOneShotEffect(
                new AddContinuousEffectToGame(new SetBasePowerToughnessTargetEffect(6, 6, Duration.EndOfTurn)),
                new AddContinuousEffectToGame(new SetBasePowerToughnessTargetEffect(3, 3, Duration.EndOfTurn)),
                condition, "target creature has base power and toughness 3/3 until end of turn. " +
                "If you control six or more lands, that creature has base power and toughness 6/6 until end of turn instead"
        ));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability.addHint(LandsYouControlHint.instance));
    }

    private GenemorphImago(final GenemorphImago card) {
        super(card);
    }

    @Override
    public GenemorphImago copy() {
        return new GenemorphImago(this);
    }
}
