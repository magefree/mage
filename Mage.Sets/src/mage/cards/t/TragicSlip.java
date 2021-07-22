
package mage.cards.t;

import java.util.UUID;
import mage.abilities.condition.LockedInCondition;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.hint.common.MorbidHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class TragicSlip extends CardImpl {

    public TragicSlip(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{B}");


        // Target creature gets -1/-1 until end of turn.
        // <i>Morbid</i> &mdash; That creature gets -13/-13 until end of turn instead if a creature died this turn.
        this.getSpellAbility().addEffect(new ConditionalContinuousEffect(
                new BoostTargetEffect(-13, -13, Duration.EndOfTurn),
                new BoostTargetEffect(-1, -1, Duration.EndOfTurn),
                new LockedInCondition(MorbidCondition.instance),
                "Target creature gets -1/-1 until end of turn. <br><i>Morbid</i> &mdash; That creature gets -13/-13 until end of turn instead if a creature died this turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addHint(MorbidHint.instance);
    }

    private TragicSlip(final TragicSlip card) {
        super(card);
    }

    @Override
    public TragicSlip copy() {
        return new TragicSlip(this);
    }
}
