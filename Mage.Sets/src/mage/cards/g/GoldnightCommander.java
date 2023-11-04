package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 * @author Loki
 */
public final class GoldnightCommander extends CardImpl {

    public GoldnightCommander(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever another creature enters the battlefield under your control, creatures you control get +1/+1 until end of turn.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD,
                new BoostControlledEffect(1, 1, Duration.EndOfTurn), StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE, false));
    }

    private GoldnightCommander(final GoldnightCommander card) {
        super(card);
    }

    @Override
    public GoldnightCommander copy() {
        return new GoldnightCommander(this);
    }
}
