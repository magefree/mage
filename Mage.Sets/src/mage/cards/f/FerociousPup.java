package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.WolfToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FerociousPup extends CardImpl {

    public FerociousPup(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.WOLF);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // When Ferocious Pup enters the battlefield, create a 2/2 green Wolf creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new WolfToken())));
    }

    private FerociousPup(final FerociousPup card) {
        super(card);
    }

    @Override
    public FerociousPup copy() {
        return new FerociousPup(this);
    }
}
