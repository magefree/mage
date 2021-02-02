

package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.SaprolingToken;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class SproutingThrinax extends CardImpl {

    private static SaprolingToken saprolingToken = new SaprolingToken();

    public SproutingThrinax(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}{R}{G}");


        this.subtype.add(SubType.LIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(saprolingToken, 3), false));
    }

    private SproutingThrinax(final SproutingThrinax card) {
        super(card);
    }

    @Override
    public SproutingThrinax copy() {
        return new SproutingThrinax(this);
    }

}


