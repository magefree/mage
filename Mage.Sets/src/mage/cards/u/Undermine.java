
package mage.cards.u;

import java.util.UUID;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public final class Undermine extends CardImpl {

    public Undermine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}{U}{B}");


        // Counter target spell. Its controller loses 3 life.
        this.getSpellAbility().addTarget(new TargetSpell(StaticFilters.FILTER_SPELL));
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addEffect(new LoseLifeTargetControllerEffect(3));

    }

    private Undermine(final Undermine card) {
        super(card);
    }

    @Override
    public Undermine copy() {
        return new Undermine(this);
    }
}
