

package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.CascadeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Loki
 */
public final class CapturedSunlight extends CardImpl {

    public CapturedSunlight (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{G}{W}");


        this.getSpellAbility().addEffect(new GainLifeEffect(4));
        this.addAbility(new CascadeAbility());
    }

    private CapturedSunlight(final CapturedSunlight card) {
        super(card);
    }

    @Override
    public CapturedSunlight copy() {
        return new CapturedSunlight(this);
    }

}
