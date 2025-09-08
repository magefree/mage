package mage.cards.n;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.HumanCitizenToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NewsHelicopter extends CardImpl {

    public NewsHelicopter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When this creature enters, create a 1/1 green and white Human Citizen creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new HumanCitizenToken())));
    }

    private NewsHelicopter(final NewsHelicopter card) {
        super(card);
    }

    @Override
    public NewsHelicopter copy() {
        return new NewsHelicopter(this);
    }
}
