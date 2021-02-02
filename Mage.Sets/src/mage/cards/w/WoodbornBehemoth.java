
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class WoodbornBehemoth extends CardImpl {

    public WoodbornBehemoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // As long as you control eight or more lands, Woodborn Behemoth gets +4/+4 and has trample.
        PermanentsOnTheBattlefieldCondition eightOrMoreLandCondition = new PermanentsOnTheBattlefieldCondition(StaticFilters.FILTER_LANDS, ComparisonType.MORE_THAN,7);
        ConditionalContinuousEffect effect1 = new ConditionalContinuousEffect(
                new BoostSourceEffect(4,4, Duration.WhileOnBattlefield), eightOrMoreLandCondition,
                "As long as you control eight or more lands, {this} gets +4/+4");
        ConditionalContinuousEffect effect2 = new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(TrampleAbility.getInstance(), Duration.WhileOnBattlefield),
                eightOrMoreLandCondition, " and has trample");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect1);
        ability.addEffect(effect2);
        this.addAbility(ability);
    }

    private WoodbornBehemoth(final WoodbornBehemoth card) {
        super(card);
    }

    @Override
    public WoodbornBehemoth copy() {
        return new WoodbornBehemoth(this);
    }
}
