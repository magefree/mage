
package mage.cards.r;

import mage.abilities.effects.common.ReturnToHandFromBattlefieldAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class ReduceToDreams extends CardImpl {

    public ReduceToDreams(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{U}{U}");

        // Return all artifacts and enchantments to their owners' hands.
        this.getSpellAbility().addEffect(new ReturnToHandFromBattlefieldAllEffect(StaticFilters.FILTER_PERMANENT_ARTIFACTS_AND_ENCHANTMENTS));
    }

    private ReduceToDreams(final ReduceToDreams card) {
        super(card);
    }

    @Override
    public ReduceToDreams copy() {
        return new ReduceToDreams(this);
    }
}
