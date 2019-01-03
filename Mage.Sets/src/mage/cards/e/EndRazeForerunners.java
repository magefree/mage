package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
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
public final class EndRazeForerunners extends CardImpl {

    public EndRazeForerunners(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{G}{G}");

        this.subtype.add(SubType.BOAR);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When End-Raze Forerunners enters the battlefield, other creatures you control get +2/+2 and gain vigilance and trample until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostControlledEffect(
                2, 2, Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURE, true
        ).setText("other creatures you control get +2/+2"));
        ability.addEffect(new GainAbilityControlledEffect(
                VigilanceAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURE, true
        ).setText("and gain vigilance"));
        ability.addEffect(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURE, true
        ).setText("and trample until end of turn"));
        this.addAbility(ability);
    }

    private EndRazeForerunners(final EndRazeForerunners card) {
        super(card);
    }

    @Override
    public EndRazeForerunners copy() {
        return new EndRazeForerunners(this);
    }
}
