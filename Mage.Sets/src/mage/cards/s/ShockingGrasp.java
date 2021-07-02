package mage.cards.s;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShockingGrasp extends CardImpl {

    public ShockingGrasp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Target creature gets -2/-0 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(-2, 0));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private ShockingGrasp(final ShockingGrasp card) {
        super(card);
    }

    @Override
    public ShockingGrasp copy() {
        return new ShockingGrasp(this);
    }
}
