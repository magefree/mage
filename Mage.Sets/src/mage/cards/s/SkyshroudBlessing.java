
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterLandPermanent;

/**
 *
 * @author LoneFox
 */
public final class SkyshroudBlessing extends CardImpl {

    public SkyshroudBlessing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}");

        // Lands gain shroud until end of turn.
        this.getSpellAbility().addEffect(new GainAbilityAllEffect(ShroudAbility.getInstance(),
            Duration.EndOfTurn, new FilterLandPermanent("all lands")));
        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private SkyshroudBlessing(final SkyshroudBlessing card) {
        super(card);
    }

    @Override
    public SkyshroudBlessing copy() {
        return new SkyshroudBlessing(this);
    }
}
