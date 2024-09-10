package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CitysBlessingCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.hint.common.CitysBlessingHint;
import mage.abilities.keyword.AscendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.game.permanent.token.GnomeToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IllustriousWanderglyph extends CardImpl {

    public IllustriousWanderglyph(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Ascend
        this.addAbility(new AscendAbility());

        // Other artifact creatures you control get +2/+2 as long as you have the city's blessing.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostControlledEffect(
                        2, 2, Duration.WhileOnBattlefield,
                        StaticFilters.FILTER_PERMANENT_ARTIFACT_CREATURE, true
                ), CitysBlessingCondition.instance, "other artifact creatures you control " +
                "get +2/+2 as long as you have the city's blessing"
        )).addHint(CitysBlessingHint.instance));

        // At the beginning of each upkeep, create a 1/1 colorless Gnome artifact creature token.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new CreateTokenEffect(new GnomeToken()), TargetController.EACH_PLAYER, false
        ));
    }

    private IllustriousWanderglyph(final IllustriousWanderglyph card) {
        super(card);
    }

    @Override
    public IllustriousWanderglyph copy() {
        return new IllustriousWanderglyph(this);
    }
}
