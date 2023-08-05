
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.keyword.UndauntedAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class SublimeExhalation extends CardImpl {

    public SublimeExhalation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{6}{W}");

        // Undaunted (This spell costs {1} less to cast for each opponent.)
        this.addAbility(new UndauntedAbility());

        // Destroy all creatures.
        getSpellAbility().addEffect(new DestroyAllEffect(StaticFilters.FILTER_PERMANENT_CREATURES));
    }

    private SublimeExhalation(final SublimeExhalation card) {
        super(card);
    }

    @Override
    public SublimeExhalation copy() {
        return new SublimeExhalation(this);
    }
}
