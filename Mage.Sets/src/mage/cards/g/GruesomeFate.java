
package mage.cards.g;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class GruesomeFate extends CardImpl {

    public GruesomeFate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Each opponent loses 1 life for each creature you control.
        this.getSpellAbility().addEffect(new LoseLifeOpponentsEffect(new PermanentsOnBattlefieldCount(StaticFilters.FILTER_PERMANENT_CREATURE_CONTROLLED)));
    }

    private GruesomeFate(final GruesomeFate card) {
        super(card);
    }

    @Override
    public GruesomeFate copy() {
        return new GruesomeFate(this);
    }
}
