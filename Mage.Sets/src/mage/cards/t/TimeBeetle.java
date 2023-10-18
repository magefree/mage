package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.counter.TimeTravelEffect;
import mage.abilities.keyword.SkulkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TimeBeetle extends CardImpl {

    public TimeBeetle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.ALIEN);
        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Skulk
        this.addAbility(new SkulkAbility());

        // Parallel Universe -- Whenever Time Beetle deals combat damage to a player, time travel.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new TimeTravelEffect(), false
        ).withFlavorWord("Parallel Universe"));
    }

    private TimeBeetle(final TimeBeetle card) {
        super(card);
    }

    @Override
    public TimeBeetle copy() {
        return new TimeBeetle(this);
    }
}
