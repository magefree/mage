package mage.cards.t;

import java.util.UUID;

import mage.abilities.Mode;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetSpell;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author muz
 */
public final class TheorixCharm extends CardImpl {

    public TheorixCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}{B}");

        // Choose one --
        // * Counter target noncreature spell unless its controller pays {2}.
        this.getSpellAbility().addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_NON_CREATURE));
        this.getSpellAbility().addEffect(new CounterUnlessPaysEffect(new GenericManaCost(2)));

        // * Target creature gets -2/-2 until end of turn.
        Mode mode = new Mode(new BoostTargetEffect(-2, -2));
        mode.addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode);

        // * Mill three cards, then draw a card.
        Mode mode2 = new Mode(new MillCardsControllerEffect(3));
        mode2.addEffect(new DrawCardSourceControllerEffect(1).concatBy(", then"));
        this.getSpellAbility().addMode(mode2);
    }

    private TheorixCharm(final TheorixCharm card) {
        super(card);
    }

    @Override
    public TheorixCharm copy() {
        return new TheorixCharm(this);
    }
}
