package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
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
public final class PerimeterPatrol extends CardImpl {

    public PerimeterPatrol(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever an artifact enters the battlefield under your control, Perimeter Patrol gets +1/+0 until end of turn.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new BoostSourceEffect(1, 0, Duration.EndOfTurn),
                StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_AN
        ));
    }

    private PerimeterPatrol(final PerimeterPatrol card) {
        super(card);
    }

    @Override
    public PerimeterPatrol copy() {
        return new PerimeterPatrol(this);
    }
}
