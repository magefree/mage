package mage.cards.n;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldOrDiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NoggleRobber extends CardImpl {

    public NoggleRobber(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R/G}{R/G}");

        this.subtype.add(SubType.NOGGLE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When this creature enters or dies, create a Treasure token.
        this.addAbility(new EntersBattlefieldOrDiesSourceTriggeredAbility(new CreateTokenEffect(new TreasureToken()), false));
    }

    private NoggleRobber(final NoggleRobber card) {
        super(card);
    }

    @Override
    public NoggleRobber copy() {
        return new NoggleRobber(this);
    }
}
