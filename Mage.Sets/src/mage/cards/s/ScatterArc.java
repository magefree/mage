
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetSpell;

/**
 *
 * @author Plopman
 */
public final class ScatterArc extends CardImpl {

    public ScatterArc(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{U}");

        // Counter target noncreature spell.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_NON_CREATURE));

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private ScatterArc(final ScatterArc card) {
        super(card);
    }

    @Override
    public ScatterArc copy() {
        return new ScatterArc(this);
    }
}
