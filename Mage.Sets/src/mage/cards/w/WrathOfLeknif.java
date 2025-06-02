package mage.cards.w;

import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.UntapLandsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class WrathOfLeknif extends CardImpl {

    public WrathOfLeknif(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}{W}{U}");

        // Destroy all creatures. They can't be regenerated. Untap up to four lands you control.
        this.getSpellAbility().addEffect(new DestroyAllEffect(StaticFilters.FILTER_PERMANENT_CREATURES, true));
        this.getSpellAbility().addEffect(new UntapLandsEffect(4, true, true));
    }

    private WrathOfLeknif(final WrathOfLeknif card) {
        super(card);
    }

    @Override
    public WrathOfLeknif copy() {
        return new WrathOfLeknif(this);
    }
}
