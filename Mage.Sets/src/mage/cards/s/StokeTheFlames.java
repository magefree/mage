
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author Quercitron
 */
public final class StokeTheFlames extends CardImpl {

    public StokeTheFlames(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{R}{R}");


        // Convoke
        this.addAbility(new ConvokeAbility());
        
        // Stoke the Flames deals 4 damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    public StokeTheFlames(final StokeTheFlames card) {
        super(card);
    }

    @Override
    public StokeTheFlames copy() {
        return new StokeTheFlames(this);
    }
}
