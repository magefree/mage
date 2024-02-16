
package mage.cards.t;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.keyword.BasicLandcyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;

/**
 *
 * @author Loki
 */
public final class TraumaticVisions extends CardImpl {

    public TraumaticVisions(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{U}{U}");


        // Counter target spell.
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.addAbility(new BasicLandcyclingAbility(new ManaCostsImpl<>("{1}{U}")));
    }

    private TraumaticVisions(final TraumaticVisions card) {
        super(card);
    }

    @Override
    public TraumaticVisions copy() {
        return new TraumaticVisions(this);
    }
}
