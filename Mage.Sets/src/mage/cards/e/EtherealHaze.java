
package mage.cards.e;

import mage.abilities.effects.common.PreventAllDamageByAllPermanentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

import static mage.filter.StaticFilters.FILTER_PERMANENT_CREATURES;

/**
 *
 * @author LevelX
 */
public final class EtherealHaze extends CardImpl {

    public EtherealHaze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");
        this.subtype.add(SubType.ARCANE);

        // Prevent all damage that would be dealt by creatures this turn.
        this.getSpellAbility().addEffect(new PreventAllDamageByAllPermanentsEffect(FILTER_PERMANENT_CREATURES, Duration.EndOfTurn, false));

    }

    private EtherealHaze(final EtherealHaze card) {
        super(card);
    }

    @Override
    public EtherealHaze copy() {
        return new EtherealHaze(this);
    }

}
