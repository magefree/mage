package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.SubType;
import mage.game.permanent.token.MutagenToken;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class ZooEscapees extends CardImpl {

    public ZooEscapees(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.BOAR);
        this.subtype.add(SubType.RHINO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When this creature leaves the battlefield, create a Mutagen token.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new CreateTokenEffect(new MutagenToken())));
    }

    private ZooEscapees(final ZooEscapees card) {
        super(card);
    }

    @Override
    public ZooEscapees copy() {
        return new ZooEscapees(this);
    }
}
