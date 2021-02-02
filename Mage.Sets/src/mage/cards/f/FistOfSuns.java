
package mage.cards.f;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.WUBRGInsteadEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

/**
 *
 * @author LevelX2
 */
public final class FistOfSuns extends CardImpl {

    public FistOfSuns(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // You may pay {W}{U}{B}{R}{G} rather than pay the mana cost for spells that you cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new WUBRGInsteadEffect()));
    }

    private FistOfSuns(final FistOfSuns card) {
        super(card);
    }

    @Override
    public FistOfSuns copy() {
        return new FistOfSuns(this);
    }
        
}
