package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class Dredge extends CardImpl {

    public Dredge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{B}");

        // Sacrifice a creature or land.
        this.getSpellAbility().addEffect(new SacrificeControllerEffect(StaticFilters.FILTER_PERMANENT_CREATURE_OR_LAND, 1, ""));

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private Dredge(final Dredge card) {
        super(card);
    }

    @Override
    public Dredge copy() {
        return new Dredge(this);
    }
}
