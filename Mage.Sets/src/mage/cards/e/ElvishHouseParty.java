
package mage.cards.e;

import java.time.LocalTime;
import java.time.ZoneId;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;

/**
 *
 * @author Ketsuban
 */
public final class ElvishHouseParty extends CardImpl {

    public ElvishHouseParty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] { CardType.CREATURE }, "{4}{G}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Elvish House Party's power and toughness are each equal to the current hour,
        // using the twelve-hour system.
        this.addAbility(new SimpleStaticAbility(Zone.ALL,
                new SetPowerToughnessSourceEffect(new CurrentHourCount(), Duration.WhileOnBattlefield)));
    }

    private ElvishHouseParty(final ElvishHouseParty card) {
        super(card);
    }

    @Override
    public ElvishHouseParty copy() {
        return new ElvishHouseParty(this);
    }
}

class CurrentHourCount implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int hour = LocalTime.now(ZoneId.of("UTC")).getHour();
        // convert 24-hour value to 12-hour
        if (hour > 12) {
            hour -= 12;
        }
        if (hour == 0) {
            hour = 12;
        }
        return hour;
    }

    @Override
    public CurrentHourCount copy() {
        return new CurrentHourCount();
    }

    @Override
    public String getMessage() {
        return "current hour, using the twelve-hour system";
    }
}
