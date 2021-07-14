
package mage.cards.h;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author KholdFuzion

 */
public final class HowlFromBeyond extends CardImpl {

    public HowlFromBeyond(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{X}{B}");


        // Target creature gets +X/+0 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(ManacostVariableValue.REGULAR, StaticValue.get(0), Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private HowlFromBeyond(final HowlFromBeyond card) {
        super(card);
    }

    @Override
    public HowlFromBeyond copy() {
        return new HowlFromBeyond(this);
    }
}
