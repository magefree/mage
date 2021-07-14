

package mage.cards.u;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author ayratn
 */
public final class UntamedMight extends CardImpl {

    public UntamedMight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{X}{G}");

        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new BoostTargetEffect(ManacostVariableValue.REGULAR, ManacostVariableValue.REGULAR, Duration.EndOfTurn));
    }

    private UntamedMight(final UntamedMight card) {
        super(card);
    }

    @Override
    public UntamedMight copy() {
        return new UntamedMight(this);
    }

}
