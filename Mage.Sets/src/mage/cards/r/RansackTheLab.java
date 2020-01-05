package mage.cards.r;

import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RansackTheLab extends CardImpl {

    public RansackTheLab(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Look at the top three cards of your library. Put one of them into your hand and the rest into your graveyard.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(StaticValue.get(3), false, StaticValue.get(1),
                StaticFilters.FILTER_CARD, Zone.GRAVEYARD, false, false, false, Zone.HAND, false));
    }

    private RansackTheLab(final RansackTheLab card) {
        super(card);
    }

    @Override
    public RansackTheLab copy() {
        return new RansackTheLab(this);
    }
}
