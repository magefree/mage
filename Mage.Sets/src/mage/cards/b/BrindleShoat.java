
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.BoarToken;

/**
 *
 * @author LevelX2
 */
public final class BrindleShoat extends CardImpl {

    public BrindleShoat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.BOAR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Brindle Shoat dies, create a 3/3 green Boar creature token.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new BoarToken())));
    }

    private BrindleShoat(final BrindleShoat card) {
        super(card);
    }

    @Override
    public BrindleShoat copy() {
        return new BrindleShoat(this);
    }
}
