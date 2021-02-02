
package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.DredgeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class Darkblast extends CardImpl {

    public Darkblast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{B}");


        // Target creature gets -1/-1 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(-1, -1, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        // Dredge 3
        this.addAbility(new DredgeAbility(3));
    }

    private Darkblast(final Darkblast card) {
        super(card);
    }

    @Override
    public Darkblast copy() {
        return new Darkblast(this);
    }
}
