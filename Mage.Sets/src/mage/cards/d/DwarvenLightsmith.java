
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.constants.SubType;
import mage.abilities.keyword.AssistAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterTeamCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class DwarvenLightsmith extends CardImpl {

    private static final FilterTeamCreaturePermanent filter = new FilterTeamCreaturePermanent("creatures your team controls");

    public DwarvenLightsmith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}");

        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Assist
        this.addAbility(new AssistAbility());

        // When Dwarven Lightsmith enters the battlefield, creatures your team controls get +1/+1 until end of turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BoostAllEffect(1, 1, Duration.EndOfTurn, filter, false)));
    }

    private DwarvenLightsmith(final DwarvenLightsmith card) {
        super(card);
    }

    @Override
    public DwarvenLightsmith copy() {
        return new DwarvenLightsmith(this);
    }
}
