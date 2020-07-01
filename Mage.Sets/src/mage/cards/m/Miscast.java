package mage.cards.m;

import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Miscast extends CardImpl {

    public Miscast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Counter target instant or sorcery spell unless its controller pays {3}.
        this.getSpellAbility().addEffect(new CounterUnlessPaysEffect(new GenericManaCost(3)));
        this.getSpellAbility().addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_INSTANT_OR_SORCERY));
    }

    private Miscast(final Miscast card) {
        super(card);
    }

    @Override
    public Miscast copy() {
        return new Miscast(this);
    }
}
