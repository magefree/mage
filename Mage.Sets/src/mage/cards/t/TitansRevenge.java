
package mage.cards.t;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DoIfClashWonEffect;
import mage.abilities.effects.common.ReturnToHandSpellEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LevelX2
 */
public final class TitansRevenge extends CardImpl {

    public TitansRevenge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{R}{R}");


        // Titan's Revenge deals X damage to any target. Clash with an opponent. If you win, return Titan's Revenge to its owner's hand.
        this.getSpellAbility().addEffect(new DamageTargetEffect(ManacostVariableValue.REGULAR));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().addEffect(new DoIfClashWonEffect(ReturnToHandSpellEffect.getInstance()));
    }

    private TitansRevenge(final TitansRevenge card) {
        super(card);
    }

    @Override
    public TitansRevenge copy() {
        return new TitansRevenge(this);
    }
}
