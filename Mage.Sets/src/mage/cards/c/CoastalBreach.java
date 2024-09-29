package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.ReturnToHandFromBattlefieldAllEffect;
import mage.abilities.keyword.UndauntedAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class CoastalBreach extends CardImpl {

    public CoastalBreach(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{6}{U}");

        // Undaunted
        this.addAbility(new UndauntedAbility());
        // Return all nonland permanents to their owners' hands.
        this.getSpellAbility().addEffect(new ReturnToHandFromBattlefieldAllEffect(StaticFilters.FILTER_PERMANENTS_NON_LAND));
    }

    private CoastalBreach(final CoastalBreach card) {
        super(card);
    }

    @Override
    public CoastalBreach copy() {
        return new CoastalBreach(this);
    }
}
