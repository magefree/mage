
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrDiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.EchoAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.GoblinToken;

/**
 *
 * @author Backfir3
 */
public final class GoblinMarshal extends CardImpl {

    public GoblinMarshal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(new EchoAbility("{4}{R}{R}"));
        // When Goblin Marshal enters the battlefield or dies, create two 1/1 red Goblin creature tokens.
        Ability enterAbility = new EntersBattlefieldOrDiesSourceTriggeredAbility(new CreateTokenEffect(new GoblinToken(), 2), false);
        this.addAbility(enterAbility);
    }

    private GoblinMarshal(final GoblinMarshal card) {
        super(card);
    }

    @Override
    public GoblinMarshal copy() {
        return new GoblinMarshal(this);
    }
}
