package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
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
public final class ThunderingCeratok extends CardImpl {

    public ThunderingCeratok(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.RHINO);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Thundering Ceratok enters the battlefield, other creatures you control gain trample until end of turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURES, true
        )));
    }

    private ThunderingCeratok(final ThunderingCeratok card) {
        super(card);
    }

    @Override
    public ThunderingCeratok copy() {
        return new ThunderingCeratok(this);
    }
}
