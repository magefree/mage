
package mage.cards.a;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.AddCardTypeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.TargetPermanent;

/**
 *
 * @author North
 */
public final class ArgentMutation extends CardImpl {

    public ArgentMutation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}");


        this.getSpellAbility().addEffect(new AddCardTypeTargetEffect(Duration.EndOfTurn, CardType.ARTIFACT));
        this.getSpellAbility().addTarget(new TargetPermanent());
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private ArgentMutation(final ArgentMutation card) {
        super(card);
    }

    @Override
    public ArgentMutation copy() {
        return new ArgentMutation(this);
    }
}
