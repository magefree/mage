
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
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
 * @author jonubuu
 */
public final class MoggWarMarshal extends CardImpl {

    public MoggWarMarshal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Echo {1}{R}
        this.addAbility(new EchoAbility("{1}{R}"));
        // When Mogg War Marshal enters the battlefield or dies, create a 1/1 red Goblin creature token.
        this.addAbility(new EntersBattlefieldOrDiesSourceTriggeredAbility(new CreateTokenEffect(new GoblinToken(), 1), false));
    }

    private MoggWarMarshal(final MoggWarMarshal card) {
        super(card);
    }

    @Override
    public MoggWarMarshal copy() {
        return new MoggWarMarshal(this);
    }
}
