
package mage.cards.g;

import java.util.UUID;
import mage.abilities.effects.common.continuous.GainProtectionFromColorTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class GodsWilling extends CardImpl {

    public GodsWilling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}");

        // Target creature you control gains protection from the color of your choice until end of turn. Scry 1.
        this.getSpellAbility().addEffect(new GainProtectionFromColorTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addEffect(new ScryEffect(1, false).concatBy("<br>"));
    }

    private GodsWilling(final GodsWilling card) {
        super(card);
    }

    @Override
    public GodsWilling copy() {
        return new GodsWilling(this);
    }
}
