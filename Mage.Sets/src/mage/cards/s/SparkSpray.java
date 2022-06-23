
package mage.cards.s;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LevelX2
 */
public final class SparkSpray extends CardImpl {

    public SparkSpray(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{R}");


        // Spark Spray deals 1 damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(1));
        this.getSpellAbility().addTarget(new TargetAnyTarget());

        // Cycling {R}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{R}")));
    }

    private SparkSpray(final SparkSpray card) {
        super(card);
    }

    @Override
    public SparkSpray copy() {
        return new SparkSpray(this);
    }
}
