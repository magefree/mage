
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetSpell;

/**
 *
 * @author North
 */
public final class RemoveSoul extends CardImpl {

    public RemoveSoul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        this.getSpellAbility().addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_CREATURE));
        this.getSpellAbility().addEffect(new CounterTargetEffect());
    }

    private RemoveSoul(final RemoveSoul card) {
        super(card);
    }

    @Override
    public RemoveSoul copy() {
        return new RemoveSoul(this);
    }
}
