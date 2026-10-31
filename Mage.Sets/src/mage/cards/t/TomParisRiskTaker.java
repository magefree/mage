package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author muz
 */
public final class TomParisRiskTaker extends CardImpl {

    public TomParisRiskTaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PILOT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Seek out -- Whenever you draw your second card each turn, creatures you control gain flying, double strike, vigilance, and haste until end of turn.
        Ability ability = new DrawNthCardTriggeredAbility(new GainAbilityControlledEffect(
            FlyingAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES
        ).setText("creatures you control gain flying"));
        ability.addEffect(new GainAbilityControlledEffect(
            DoubleStrikeAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES
        ).setText(", double strike"));
        ability.addEffect(new GainAbilityControlledEffect(
            VigilanceAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES
        ).setText(", vigilance"));
        ability.addEffect(new GainAbilityControlledEffect(
            HasteAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES
        ).setText("and haste until end of turn"));
        this.addAbility(ability.withFlavorWord("Seek out"));
    }

    private TomParisRiskTaker(final TomParisRiskTaker card) {
        super(card);
    }

    @Override
    public TomParisRiskTaker copy() {
        return new TomParisRiskTaker(this);
    }
}
