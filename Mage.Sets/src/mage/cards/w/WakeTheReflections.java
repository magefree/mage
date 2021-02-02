

package mage.cards.w;

import java.util.UUID;
import mage.abilities.effects.common.PopulateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author LevelX2
 */


public final class WakeTheReflections extends CardImpl {

    public WakeTheReflections(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{W}");


        // Populate.
        this.getSpellAbility().addEffect(new PopulateEffect());
    }

    private WakeTheReflections(final WakeTheReflections card) {
        super(card);
    }

    @Override
    public WakeTheReflections copy() {
        return new WakeTheReflections(this);
    }
}
