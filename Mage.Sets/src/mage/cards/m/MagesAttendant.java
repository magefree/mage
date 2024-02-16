package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.MagesAttendantToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MagesAttendant extends CardImpl {

    public MagesAttendant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Mage's Attendant enters the battlefield, create a 1/1 blue Wizard creature token with "{1}, Sacrifice this creature: Counter target noncreature spell unless its controller pays {1}."
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new MagesAttendantToken())));
    }

    private MagesAttendant(final MagesAttendant card) {
        super(card);
    }

    @Override
    public MagesAttendant copy() {
        return new MagesAttendant(this);
    }
}
