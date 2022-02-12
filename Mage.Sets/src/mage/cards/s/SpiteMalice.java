package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.filter.StaticFilters;
import mage.target.TargetSpell;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author FenrisulfrX
 */
public final class SpiteMalice extends SplitCard {

    public SpiteMalice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}", "{3}{B}", SpellAbilityType.SPLIT);

        // Spite
        // Counter target noncreature spell.
        this.getLeftHalfCard().getSpellAbility().addEffect(new CounterTargetEffect());
        this.getLeftHalfCard().getSpellAbility().addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_NON_CREATURE));

        // Malice
        // Destroy target nonblack creature. It can't be regenerated.
        this.getRightHalfCard().getSpellAbility().addEffect(new DestroyTargetEffect(true));
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_PERMANENT_CREATURE_NON_BLACK));
    }

    private SpiteMalice(final SpiteMalice card) {
        super(card);
    }

    @Override
    public SpiteMalice copy() {
        return new SpiteMalice(this);
    }
}
