
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.HumanToken;

/**
 *
 * @author Loki
 */
public final class VoiceOfTheProvinces extends CardImpl {

    public VoiceOfTheProvinces(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}{W}");
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(FlyingAbility.getInstance());
        // When Voice of the Provinces enters the battlefield, create a 1/1 white Human creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new HumanToken())));
    }

    private VoiceOfTheProvinces(final VoiceOfTheProvinces card) {
        super(card);
    }

    @Override
    public VoiceOfTheProvinces copy() {
        return new VoiceOfTheProvinces(this);
    }
}
