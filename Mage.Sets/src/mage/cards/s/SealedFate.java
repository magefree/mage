package mage.cards.s;

import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class SealedFate extends CardImpl {

    public SealedFate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{U}{B}");

        // Look at the top X cards of target opponent's library. Exile one of those cards and put the rest back on top of that player's library in any order.
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(
                ManacostVariableValue.REGULAR, false, StaticValue.get(1),
                StaticFilters.FILTER_CARD, Zone.LIBRARY, true, false,
                false, Zone.EXILED, false, true, true
        ).setText("look at the top X cards of target opponent's library. Exile one of those cards " +
                "and put the rest back on top of that player's library in any order"));
    }

    private SealedFate(final SealedFate card) {
        super(card);
    }

    @Override
    public SealedFate copy() {
        return new SealedFate(this);
    }
}
