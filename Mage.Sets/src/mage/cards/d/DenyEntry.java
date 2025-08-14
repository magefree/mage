package mage.cards.d;

import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DenyEntry extends CardImpl {

    public DenyEntry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Counter target creature spell. Draw a card, then discard a card.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_CREATURE));
        this.getSpellAbility().addEffect(new DrawDiscardControllerEffect(1, 1));
    }

    private DenyEntry(final DenyEntry card) {
        super(card);
    }

    @Override
    public DenyEntry copy() {
        return new DenyEntry(this);
    }
}
