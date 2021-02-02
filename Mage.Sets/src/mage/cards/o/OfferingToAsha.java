
package mage.cards.o;

import java.util.UUID;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;

/**
 *
 * @author North
 */
public final class OfferingToAsha extends CardImpl {

    public OfferingToAsha(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{W}{U}");




        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new CounterUnlessPaysEffect(new GenericManaCost(4)));
        this.getSpellAbility().addEffect(new GainLifeEffect(4));
    }

    private OfferingToAsha(final OfferingToAsha card) {
        super(card);
    }

    @Override
    public OfferingToAsha copy() {
        return new OfferingToAsha(this);
    }
}
