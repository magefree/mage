package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
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
public final class BeastKinRanger extends CardImpl {

    public BeastKinRanger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.RANGER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever another creature you control enters, this creature gets +1/+0 until end of turn.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                new BoostSourceEffect(1, 0, Duration.EndOfTurn),
                StaticFilters.FILTER_ANOTHER_CREATURE_YOU_CONTROL
        ));
    }

    private BeastKinRanger(final BeastKinRanger card) {
        super(card);
    }

    @Override
    public BeastKinRanger copy() {
        return new BeastKinRanger(this);
    }
}
