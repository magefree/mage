package mage.cards.a;

import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.keyword.CasualtyAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ALittleChat extends CardImpl {

    public ALittleChat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Casualty 1
        this.addAbility(new CasualtyAbility(this, 1));

        // Look at the top two cards of your library. Put one of them into your hand and the other on the bottom of your library.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(
                StaticValue.get(2), false, StaticValue.get(1),
                StaticFilters.FILTER_CARD, Zone.LIBRARY, false, false
        ));
    }

    private ALittleChat(final ALittleChat card) {
        super(card);
    }

    @Override
    public ALittleChat copy() {
        return new ALittleChat(this);
    }
}
