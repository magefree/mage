package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.PlotAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StagecoachSecurity extends CardImpl {

    public StagecoachSecurity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // When Stagecoach Security enters the battlefield, creatures you control get +1/+1 and gain vigilance until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostControlledEffect(
                1, 1, Duration.EndOfTurn
        ).setText("creatures you control get +1/+1"));
        ability.addEffect(new GainAbilityControlledEffect(
                VigilanceAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_CONTROLLED_CREATURE
        ).setText("and gain vigilance until end of turn"));
        this.addAbility(ability);

        // Plot {3}{W}
        this.addAbility(new PlotAbility("{3}{W}"));
    }

    private StagecoachSecurity(final StagecoachSecurity card) {
        super(card);
    }

    @Override
    public StagecoachSecurity copy() {
        return new StagecoachSecurity(this);
    }
}
