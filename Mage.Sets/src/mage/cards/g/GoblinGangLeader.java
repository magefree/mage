package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.GoblinToken;

import java.util.UUID;

/**
 *
 * @author JayDi85
 */
public final class GoblinGangLeader extends CardImpl {

    public GoblinGangLeader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        this.subtype.add(SubType.GOBLIN, SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Goblin Gang Leader enters the battlefield, create two 1/1 red Goblin creature tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new GoblinToken(), 2)));
    }

    private GoblinGangLeader(final GoblinGangLeader card) {
        super(card);
    }

    @Override
    public GoblinGangLeader copy() {
        return new GoblinGangLeader(this);
    }
}
