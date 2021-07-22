package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.constants.SubType;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.EvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author TheElk801
 */
public final class NightIncarnate extends CardImpl {

    public NightIncarnate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // When Night Incarnate leaves the battlefield, all creatures get -3/-3 until end of turn.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(
                new BoostAllEffect(-3, -3, Duration.EndOfTurn), false
        ));

        // Evoke {3}{B}
        this.addAbility(new EvokeAbility("{3}{B}"));

    }

    private NightIncarnate(final NightIncarnate card) {
        super(card);
    }

    @Override
    public NightIncarnate copy() {
        return new NightIncarnate(this);
    }
}
