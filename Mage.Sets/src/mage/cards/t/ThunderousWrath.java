
package mage.cards.t;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.MiracleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author North
 */
public final class ThunderousWrath extends CardImpl {

    public ThunderousWrath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{4}{R}{R}");


        // Thunderous Wrath deals 5 damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(5));
        this.getSpellAbility().addTarget(new TargetAnyTarget());

        // Miracle {R}
        this.addAbility(new MiracleAbility(this, new ManaCostsImpl<>("{R}")));
    }

    private ThunderousWrath(final ThunderousWrath card) {
        super(card);
    }

    @Override
    public ThunderousWrath copy() {
        return new ThunderousWrath(this);
    }
}
