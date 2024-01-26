package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.VoiceOfResurgenceToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VernalSovereign extends CardImpl {

    public VernalSovereign(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{W}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.ELK);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Vernal Sovereign enters the battlefield or attacks, create a green and white Elemental creature token with "This creature's power and toughness are each equal to the number of creatures you control."
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new CreateTokenEffect(new VoiceOfResurgenceToken())));
    }

    private VernalSovereign(final VernalSovereign card) {
        super(card);
    }

    @Override
    public VernalSovereign copy() {
        return new VernalSovereign(this);
    }
}
