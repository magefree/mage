
package mage.cards.f;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.PlayWithTheTopCardRevealedEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.Zone;

/**
 *
 * @author LoneFox
 */
public final class FieldOfDreams extends CardImpl {

    public FieldOfDreams(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{U}");
        this.supertype.add(SuperType.WORLD);

        // Players play with the top card of their libraries revealed.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PlayWithTheTopCardRevealedEffect(true)));
    }

    private FieldOfDreams(final FieldOfDreams card) {
        super(card);
    }

    @Override
    public FieldOfDreams copy() {
        return new FieldOfDreams(this);
    }
}
