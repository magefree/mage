

package mage.cards.r;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Backfir3
 */
public final class Rejuvenate extends CardImpl {

    public Rejuvenate (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{G}");

        this.getSpellAbility().addEffect(new GainLifeEffect(6));
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    public Rejuvenate (final Rejuvenate card) {
        super(card);
    }

    @Override
    public Rejuvenate copy() {
        return new Rejuvenate(this);
    }

}
