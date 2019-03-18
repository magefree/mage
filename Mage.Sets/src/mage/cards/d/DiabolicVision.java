
package mage.cards.d;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;

/**
 *
 * @author TheElk801
 */
public final class DiabolicVision extends CardImpl {

    public DiabolicVision(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{U}{B}");

        // Look at the top five cards of your library. Put one of them into your hand and the rest on top of your library in any order.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(new StaticValue(5), false, new StaticValue(1), new FilterCard(), true, false));
    }

    public DiabolicVision(final DiabolicVision card) {
        super(card);
    }

    @Override
    public DiabolicVision copy() {
        return new DiabolicVision(this);
    }
}
