package mage.cards.n;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.BlitzAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NightClubber extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterOpponentsCreaturePermanent("creatures your opponents control");

    public NightClubber(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Night Clubber enters the battlefield, creatures your opponents control get -1/-1 until end of turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BoostAllEffect(
                -1, -1, Duration.EndOfTurn, filter, false
        )));

        // Blitz {2}{B}
        this.addAbility(new BlitzAbility(this, "{2}{B}"));
    }

    private NightClubber(final NightClubber card) {
        super(card);
    }

    @Override
    public NightClubber copy() {
        return new NightClubber(this);
    }
}
