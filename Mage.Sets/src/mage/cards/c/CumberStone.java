
package mage.cards.c;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author Loki
 */
public final class CumberStone extends CardImpl {

    public CumberStone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}{U}");


        // Creatures your opponents control get -1/-0.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostOpponentsEffect(-1, 0, Duration.WhileOnBattlefield)));
    }

    private CumberStone(final CumberStone card) {
        super(card);
    }

    @Override
    public CumberStone copy() {
        return new CumberStone(this);
    }
}
