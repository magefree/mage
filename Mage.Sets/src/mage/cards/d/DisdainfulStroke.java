package mage.cards.d;

import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class DisdainfulStroke extends CardImpl {

    public DisdainfulStroke(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Counter target spell with converted mana cost 4 or greater.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_MV_4_OR_GREATER));
    }

    private DisdainfulStroke(final DisdainfulStroke card) {
        super(card);
    }

    @Override
    public DisdainfulStroke copy() {
        return new DisdainfulStroke(this);
    }
}
