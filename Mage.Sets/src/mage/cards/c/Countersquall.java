
package mage.cards.c;

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
 * @author North
 */
public final class Countersquall extends CardImpl {

    public Countersquall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}{B}");

        // Counter target noncreature spell. Its controller loses 2 life.
        this.getSpellAbility().addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_NON_CREATURE));
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addEffect(new LoseLifeTargetControllerEffect(2));
    }

    private Countersquall(final Countersquall card) {
        super(card);
    }

    @Override
    public Countersquall copy() {
        return new Countersquall(this);
    }
}
