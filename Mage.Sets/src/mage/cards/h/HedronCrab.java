
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;

/**
 *
 * @author North
 */
public final class HedronCrab extends CardImpl {

    public HedronCrab(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}");
        this.subtype.add(SubType.CRAB);

        this.power = new MageInt(0);
        this.toughness = new MageInt(2);
        // Landfall - Whenever a land enters the battlefield under your control, target player puts the top three cards of their library into their graveyard.
        LandfallAbility ability = new LandfallAbility(new MillCardsTargetEffect(3), false);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private HedronCrab(final HedronCrab card) {
        super(card);
    }

    @Override
    public HedronCrab copy() {
        return new HedronCrab(this);
    }
}
