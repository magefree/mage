
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
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
public final class DeathbloomThallid extends CardImpl {

    public DeathbloomThallid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.FUNGUS);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Deathbloom Thallid dies, create a 1/1 green Saproling creature token.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new SaprolingToken()), false));
    }

    private DeathbloomThallid(final DeathbloomThallid card) {
        super(card);
    }

    @Override
    public DeathbloomThallid copy() {
        return new DeathbloomThallid(this);
    }
}
