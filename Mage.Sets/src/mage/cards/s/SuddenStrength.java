
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class SuddenStrength extends CardImpl {

    public SuddenStrength(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{G}");


        // Target creature gets +3/+3 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(3,3,Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private SuddenStrength(final SuddenStrength card) {
        super(card);
    }

    @Override
    public SuddenStrength copy() {
        return new SuddenStrength(this);
    }
}
