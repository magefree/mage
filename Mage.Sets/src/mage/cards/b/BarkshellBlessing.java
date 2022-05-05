
package mage.cards.b;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.ConspireAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class BarkshellBlessing extends CardImpl {

    public BarkshellBlessing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{G/W}");

        // Target creature gets +2/+2 until end of turn.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 2, Duration.EndOfTurn));

        // Conspire
        this.addAbility(new ConspireAbility(ConspireAbility.ConspireTargets.ONE));
    }

    private BarkshellBlessing(final BarkshellBlessing card) {
        super(card);
    }

    @Override
    public BarkshellBlessing copy() {
        return new BarkshellBlessing(this);
    }
}
