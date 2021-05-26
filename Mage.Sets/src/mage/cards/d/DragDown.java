
package mage.cards.d;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.DomainValue;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.hint.common.DomainHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class DragDown extends CardImpl {

    public DragDown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{B}");


        // Domain - Target creature gets -1/-1 until end of turn for each basic land type among lands you control.
        this.getSpellAbility().addEffect(new BoostTargetEffect(new DomainValue(-1), new DomainValue(-1), Duration.EndOfTurn, true));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addHint(DomainHint.instance);
    }

    private DragDown(final DragDown card) {
        super(card);
    }

    @Override
    public DragDown copy() {
        return new DragDown(this);
    }
}
