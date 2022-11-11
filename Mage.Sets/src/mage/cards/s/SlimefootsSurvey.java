package mage.cards.s;

import java.util.UUID;

import mage.abilities.dynamicvalue.common.DomainValue;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.filter.common.FilterLandCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author weirddan455
 */
public final class SlimefootsSurvey extends CardImpl {

    private static final FilterLandCard filter = new FilterLandCard("land cards that each have a basic land type");

    static {
        filter.add(Predicates.or(
                SubType.PLAINS.getPredicate(),
                SubType.ISLAND.getPredicate(),
                SubType.SWAMP.getPredicate(),
                SubType.MOUNTAIN.getPredicate(),
                SubType.FOREST.getPredicate()
        ));
    }

    public SlimefootsSurvey(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{G}");

        // Domain — Search your library for up to two land cards that each have a basic land type, put them into the battlefield tapped, then shuffle.
        // Look at the top X cards of your library, where X is the number of basic land types among lands you control. Put up to one of them on top of your library and the rest on the bottom of your library in a random order.
        this.getSpellAbility().setAbilityWord(AbilityWord.DOMAIN);
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(0, 2, filter), true));
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(
                DomainValue.REGULAR, 1, PutCards.TOP_ANY, PutCards.BOTTOM_RANDOM, true
        ));
    }

    private SlimefootsSurvey(final SlimefootsSurvey card) {
        super(card);
    }

    @Override
    public SlimefootsSurvey copy() {
        return new SlimefootsSurvey(this);
    }
}
