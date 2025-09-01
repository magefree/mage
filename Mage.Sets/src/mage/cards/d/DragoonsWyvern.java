package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.HeroToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DragoonsWyvern extends CardImpl {

    public DragoonsWyvern(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.DRAKE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When this creature enters, create a 1/1 colorless Hero creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new HeroToken())));
    }

    private DragoonsWyvern(final DragoonsWyvern card) {
        super(card);
    }

    @Override
    public DragoonsWyvern copy() {
        return new DragoonsWyvern(this);
    }
}
