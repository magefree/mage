package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.ForetellAbility;
import mage.abilities.keyword.HasteAbility;
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
public final class DoomskarTitan extends CardImpl {

    public DoomskarTitan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");

        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Doomskar Titan enters the battlefield, creatures you control get +1/+0 and gain haste until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new BoostControlledEffect(1, 0, Duration.EndOfTurn)
                        .setText("creatures you control get +1/+0")
        );
        ability.addEffect(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURE
        ).setText("and gain haste until end of turn"));
        this.addAbility(ability);

        // Foretell {4}{R}
        this.addAbility(new ForetellAbility(this, "{4}{R}"));
    }

    private DoomskarTitan(final DoomskarTitan card) {
        super(card);
    }

    @Override
    public DoomskarTitan copy() {
        return new DoomskarTitan(this);
    }
}
