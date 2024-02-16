
package mage.cards.s;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class Starstorm extends CardImpl {

    public Starstorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{X}{R}{R}");


        // Starstorm deals X damage to each creature.
        this.getSpellAbility().addEffect(new DamageAllEffect(ManacostVariableValue.REGULAR, new FilterCreaturePermanent()));
        // Cycling {3}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{3}")));
    }

    private Starstorm(final Starstorm card) {
        super(card);
    }

    @Override
    public Starstorm copy() {
        return new Starstorm(this);
    }
}
