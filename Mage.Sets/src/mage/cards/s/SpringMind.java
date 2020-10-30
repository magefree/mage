
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.AftermathAbility;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author fireshoes
 */
public final class SpringMind extends SplitCard {

    public SpringMind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, new CardType[]{CardType.INSTANT}, "{2}{G}", "{4}{U}{U}", SpellAbilityType.SPLIT_AFTERMATH);

        // Spring
        // Search your library for a basic land card, put it onto the battlefield tapped, then shuffle your library.
        getLeftHalfCard().getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true));

        // Mind
        // Aftermath
        // Draw two cards.
        getRightHalfCard().addAbility(new AftermathAbility().setRuleAtTheTop(true));
        getRightHalfCard().getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
    }

    public SpringMind(final SpringMind card) {
        super(card);
    }

    @Override
    public SpringMind copy() {
        return new SpringMind(this);
    }
}
