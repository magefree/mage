
package mage.cards.y;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.SaprolingToken;

/**
 *
 * @author TheElk801
 */
public final class YavimayaSapherd extends CardImpl {

    public YavimayaSapherd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.FUNGUS);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Yavimaya Sapherd enters the battlefield, create a 1/1 green Saproling creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new SaprolingToken(), 1), false));
    }

    private YavimayaSapherd(final YavimayaSapherd card) {
        super(card);
    }

    @Override
    public YavimayaSapherd copy() {
        return new YavimayaSapherd(this);
    }
}
