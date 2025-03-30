package mage.cards.r;

import mage.abilities.Mode;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.PutOnTopOrBottomLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetSpell;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RiverwalkTechnique extends CardImpl {

    public RiverwalkTechnique(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}");

        // Choose one --
        // * The owner of target nonland permanent puts it on their choice of the top or bottom of their library.
        this.getSpellAbility().addEffect(new PutOnTopOrBottomLibraryTargetEffect(true));
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());

        // * Counter target noncreature spell.
        this.getSpellAbility().addMode(new Mode(new CounterTargetEffect())
                .addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_NON_CREATURE)));
    }

    private RiverwalkTechnique(final RiverwalkTechnique card) {
        super(card);
    }

    @Override
    public RiverwalkTechnique copy() {
        return new RiverwalkTechnique(this);
    }
}
