
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BecomesColorTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class SwayOfIllusion extends CardImpl {

    public SwayOfIllusion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Any number of target creatures become the color of your choice until end of turn.
        Effect effect = new BecomesColorTargetEffect(Duration.EndOfTurn);
        effect.setText("Any number of target creatures become the color of your choice until end of turn");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, Integer.MAX_VALUE, StaticFilters.FILTER_PERMANENT_CREATURE, false));

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private SwayOfIllusion(final SwayOfIllusion card) {
        super(card);
    }

    @Override
    public SwayOfIllusion copy() {
        return new SwayOfIllusion(this);
    }

}
