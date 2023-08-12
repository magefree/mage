
package mage.cards.e;

import mage.abilities.effects.common.PreventAllDamageByAllPermanentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 *
 * @author LevelX
 */
public final class EtherealHaze extends CardImpl {

    public EtherealHaze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");
        this.subtype.add(SubType.ARCANE);

        // Prevent all damage that would be dealt by creatures this turn.
        this.getSpellAbility().addEffect(new PreventAllDamageByAllPermanentsEffect(StaticFilters.FILTER_PERMANENT_CREATURES, Duration.EndOfTurn, false));

    }

    private EtherealHaze(final EtherealHaze card) {
        super(card);
    }

    @Override
    public EtherealHaze copy() {
        return new EtherealHaze(this);
    }

}
