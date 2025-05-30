package mage.cards.i;

import mage.abilities.effects.common.SacrificeAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author jonubuu
 */
public final class InnocentBlood extends CardImpl {

    public InnocentBlood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}");

        // Each player sacrifices a creature.
        this.getSpellAbility().addEffect(new SacrificeAllEffect(StaticFilters.FILTER_PERMANENT_CREATURE));
    }

    private InnocentBlood(final InnocentBlood card) {
        super(card);
    }

    @Override
    public InnocentBlood copy() {
        return new InnocentBlood(this);
    }
}
