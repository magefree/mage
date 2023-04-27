
package mage.cards.r;

import java.util.UUID;
import mage.abilities.common.CycleTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author LevelX2
 */
public final class RenewedFaith extends CardImpl {

    public RenewedFaith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{W}");


        // You gain 6 life.
        this.getSpellAbility().addEffect(new GainLifeEffect(6));
        // Cycling {1}{W}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{1}{W}")));
        // When you cycle Renewed Faith, you may gain 2 life.
        this.addAbility(new CycleTriggeredAbility(new GainLifeEffect(2), true));
    }

    private RenewedFaith(final RenewedFaith card) {
        super(card);
    }

    @Override
    public RenewedFaith copy() {
        return new RenewedFaith(this);
    }
}
