
package mage.cards.r;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.PlayWithHandRevealedEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;

/**
 *
 * @author LoneFox
 */
public final class Revelation extends CardImpl {

    public Revelation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{G}");
        this.supertype.add(SuperType.WORLD);

        // Players play with their hands revealed.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PlayWithHandRevealedEffect(TargetController.ANY)));
    }

    private Revelation(final Revelation card) {
        super(card);
    }

    @Override
    public Revelation copy() {
        return new Revelation(this);
    }
}
