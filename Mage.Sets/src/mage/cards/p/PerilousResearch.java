package mage.cards.p;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class PerilousResearch extends CardImpl {

    public PerilousResearch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");


        // Draw two cards, then sacrifice a permanent.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
        this.getSpellAbility().addEffect(new SacrificeControllerEffect(StaticFilters.FILTER_PERMANENT_A, 1, ", then"));
    }

    private PerilousResearch(final PerilousResearch card) {
        super(card);
    }

    @Override
    public PerilousResearch copy() {
        return new PerilousResearch(this);
    }
}
