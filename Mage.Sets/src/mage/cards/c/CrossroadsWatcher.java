package mage.cards.c;

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
 * @author muz
 */
public final class CrossroadsWatcher extends CardImpl {

    public CrossroadsWatcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.KITHKIN);
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

    private CrossroadsWatcher(final CrossroadsWatcher card) {
        super(card);
    }

    @Override
    public CrossroadsWatcher copy() {
        return new CrossroadsWatcher(this);
    }
}
