package mage.cards.f;

import mage.abilities.condition.common.ControlACommanderCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.hint.common.ControlACommanderHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FierceGuardianship extends CardImpl {

    public FierceGuardianship(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // If you control a commander, you may cast this spell without paying its mana cost.
        this.addAbility(new AlternativeCostSourceAbility(null, ControlACommanderCondition.instance)
                .addHint(ControlACommanderHint.instance)
        );

        // Counter target noncreature spell.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_NON_CREATURE));
    }

    private FierceGuardianship(final FierceGuardianship card) {
        super(card);
    }

    @Override
    public FierceGuardianship copy() {
        return new FierceGuardianship(this);
    }
}
