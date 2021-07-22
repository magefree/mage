package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

/**
 *
 * @author weirddan455
 */
public final class DevotedPaladin extends CardImpl {

    public DevotedPaladin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Beacon of Hope — When Devoted Paladin enters the battlefield, creatures you control get +1/+1 and gain vigilance until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new BoostControlledEffect(1, 1, Duration.EndOfTurn)
                .setText("creatures you control get +1/+1")
        );
        ability.addEffect(new GainAbilityControlledEffect(
                VigilanceAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES)
                .setText("and gain vigilance until end of turn")
        );
        this.addAbility(ability.withFlavorWord("Beacon of Hope"));
    }

    private DevotedPaladin(final DevotedPaladin card) {
        super(card);
    }

    @Override
    public DevotedPaladin copy() {
        return new DevotedPaladin(this);
    }
}
