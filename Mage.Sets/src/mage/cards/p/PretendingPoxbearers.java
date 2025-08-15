package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.AllyToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PretendingPoxbearers extends CardImpl {

    public PretendingPoxbearers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W/B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CITIZEN);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When this creature dies, create a 1/1 white Ally creature token.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new AllyToken())));
    }

    private PretendingPoxbearers(final PretendingPoxbearers card) {
        super(card);
    }

    @Override
    public PretendingPoxbearers copy() {
        return new PretendingPoxbearers(this);
    }
}
