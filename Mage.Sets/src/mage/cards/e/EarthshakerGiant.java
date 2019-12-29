package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
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
public final class EarthshakerGiant extends CardImpl {

    public EarthshakerGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");

        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Earthshaker Giant enters the battlefield, other creatures you control get +3/+3 and gain trample until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostControlledEffect(
                3, 3, Duration.EndOfTurn, true
        ).setText("other creatures you control get +3/+3"));
        ability.addEffect(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURE, true
        ).setText("and gain trample until end of turn"));
        this.addAbility(ability);
    }

    private EarthshakerGiant(final EarthshakerGiant card) {
        super(card);
    }

    @Override
    public EarthshakerGiant copy() {
        return new EarthshakerGiant(this);
    }
}
