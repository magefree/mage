
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.SpiritWhiteToken;

/**
 *
 * @author Loki
 */
public final class ElgaudInquisitor extends CardImpl {

    public ElgaudInquisitor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(LifelinkAbility.getInstance());
        // When Elgaud Inquisitor dies, create a 1/1 white Spirit creature token with flying.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new SpiritWhiteToken())));
    }

    private ElgaudInquisitor(final ElgaudInquisitor card) {
        super(card);
    }

    @Override
    public ElgaudInquisitor copy() {
        return new ElgaudInquisitor(this);
    }
}
