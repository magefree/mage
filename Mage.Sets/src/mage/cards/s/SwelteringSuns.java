
package mage.cards.s;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author Darkside-
 */
public final class SwelteringSuns extends CardImpl {

    public SwelteringSuns(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{R}{R}");


        // Anger of the Gods deals 3 damage to each creature. 
        this.getSpellAbility().addEffect(new DamageAllEffect(3, new FilterCreaturePermanent()));
        
        //Cycling {3}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{3}")));
    }

    private SwelteringSuns(final SwelteringSuns card) {
        super(card);
    }

    @Override
    public SwelteringSuns copy() {
        return new SwelteringSuns(this);
    }
}

