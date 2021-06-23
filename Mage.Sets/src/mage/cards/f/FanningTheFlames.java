
package mage.cards.f;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.BuybackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author fireshoes
 */
public final class FanningTheFlames extends CardImpl {

    public FanningTheFlames(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{R}{R}");

        // Buyback {3}
        this.addAbility(new BuybackAbility("{3}"));
        
        // Fanning the Flames deals X damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(ManacostVariableValue.REGULAR));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private FanningTheFlames(final FanningTheFlames card) {
        super(card);
    }

    @Override
    public FanningTheFlames copy() {
        return new FanningTheFlames(this);
    }
}
