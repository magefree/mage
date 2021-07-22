
package mage.cards.m;

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
 * @author Loki
 */
public final class MightOfAlara extends CardImpl {

    public MightOfAlara(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{G}");


        // Domain - Target creature gets +1/+1 until end of turn for each basic land type among lands you control.
        this.getSpellAbility().addEffect(new BoostTargetEffect(new DomainValue(), new DomainValue(), Duration.EndOfTurn, true));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addHint(DomainHint.instance);
    }

    private MightOfAlara(final MightOfAlara card) {
        super(card);
    }

    @Override
    public MightOfAlara copy() {
        return new MightOfAlara(this);
    }
}
