
package mage.cards.a;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.StormAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Plopman
 */
public final class AstralSteel extends CardImpl {

    public AstralSteel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{W}");


        // Target creature gets +1/+2 until end of turn.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 2, Duration.EndOfTurn));
        // Storm
        this.addAbility(new StormAbility());
    }

    private AstralSteel(final AstralSteel card) {
        super(card);
    }

    @Override
    public AstralSteel copy() {
        return new AstralSteel(this);
    }
}
