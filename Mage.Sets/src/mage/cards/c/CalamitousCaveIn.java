package mage.cards.c;

import mage.abilities.dynamicvalue.common.CavesControlledAndInGraveCount;
import mage.abilities.effects.common.DamageAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class CalamitousCaveIn extends CardImpl {

    public CalamitousCaveIn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // Calamitous Cave-In deals X damage to each creature and each planeswalker, where X is the number of Caves you control plus the number of Cave cards in your graveyard.
        this.getSpellAbility().addEffect(
                new DamageAllEffect(
                        CavesControlledAndInGraveCount.WHERE_X,
                        StaticFilters.FILTER_PERMANENT_CREATURE_OR_PLANESWALKER
                ).setText("{this} deals X damage to each creature and each planeswalker, "
                        + "where X is the number of Caves you control plus the number of "
                        + "Cave cards in your graveyard")
        );
        this.getSpellAbility().addHint(CavesControlledAndInGraveCount.getHint());
    }

    private CalamitousCaveIn(final CalamitousCaveIn card) {
        super(card);
    }

    @Override
    public CalamitousCaveIn copy() {
        return new CalamitousCaveIn(this);
    }
}
