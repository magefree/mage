
package mage.cards.a;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.CreatureEntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.permanent.token.TokenImpl;
import mage.game.permanent.token.Token;
import mage.game.permanent.token.custom.CreatureToken;

/**
 * @author Loki
 */
public final class AngelsTomb extends CardImpl {

    public AngelsTomb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Whenever a creature enters the battlefield under your control, you may have Angel's Tomb become a 3/3 white Angel artifact creature with flying until end of turn.
        this.addAbility(new CreatureEntersBattlefieldTriggeredAbility(new BecomesCreatureSourceEffect(
                new CreatureToken(3, 3, "3/3 white Angel artifact creature with flying")
                        .withColor("W")
                        .withSubType(SubType.ANGEL)
                        .withAbility(FlyingAbility.getInstance()),
                "", Duration.EndOfTurn), true));
    }

    public AngelsTomb(final AngelsTomb card) {
        super(card);
    }

    @Override
    public AngelsTomb copy() {
        return new AngelsTomb(this);
    }
}