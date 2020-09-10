
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class ForsakenDrifters extends CardImpl {

    public ForsakenDrifters(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // When Forsaken Drifters dies, put the top four cards of your library into your graveyard.
        this.addAbility(new DiesSourceTriggeredAbility(new MillCardsControllerEffect(4)));
    }

    public ForsakenDrifters(final ForsakenDrifters card) {
        super(card);
    }

    @Override
    public ForsakenDrifters copy() {
        return new ForsakenDrifters(this);
    }
}
