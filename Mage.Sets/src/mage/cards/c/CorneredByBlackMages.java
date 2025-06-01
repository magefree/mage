package mage.cards.c;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.BlackWizardToken;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CorneredByBlackMages extends CardImpl {

    public CorneredByBlackMages(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}{B}");

        // Target opponent sacrifices a creature of their choice.
        this.getSpellAbility().addEffect(new SacrificeEffect(
                StaticFilters.FILTER_PERMANENT_CREATURE, 1, "target opponent"
        ));
        this.getSpellAbility().addTarget(new TargetOpponent());

        // Create a 0/1 black Wizard creature token with "Whenever you cast a noncreature spell, this token deals 1 damage to each opponent."
        this.getSpellAbility().addEffect(new CreateTokenEffect(new BlackWizardToken()).concatBy("<br>"));
    }

    private CorneredByBlackMages(final CorneredByBlackMages card) {
        super(card);
    }

    @Override
    public CorneredByBlackMages copy() {
        return new CorneredByBlackMages(this);
    }
}
