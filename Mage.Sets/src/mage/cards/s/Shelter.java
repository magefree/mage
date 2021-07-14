
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainProtectionFromColorTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author cbt33
 */
public final class Shelter extends CardImpl {

    public Shelter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");


        // Target creature you control gains protection from the color of your choice until end of turn.
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addEffect(new GainProtectionFromColorTargetEffect(Duration.EndOfTurn));
        // 
        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private Shelter(final Shelter card) {
        super(card);
    }

    @Override
    public Shelter copy() {
        return new Shelter(this);
    }
}
