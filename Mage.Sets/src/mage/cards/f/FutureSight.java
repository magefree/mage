
package mage.cards.f;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.PlayTheTopCardEffect;
import mage.abilities.effects.common.continuous.PlayWithTheTopCardRevealedEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author Plopman
 */
public final class FutureSight extends CardImpl {

    public FutureSight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{U}{U}{U}");

        // Play with the top card of your library revealed.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PlayWithTheTopCardRevealedEffect()));
        // You may play the top card of your library.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PlayTheTopCardEffect()));
    }

    public FutureSight(final FutureSight card) {
        super(card);
    }

    @Override
    public FutureSight copy() {
        return new FutureSight(this);
    }
}
