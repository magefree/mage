package mage.cards.d;

import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.CardTypesInGraveyardCount;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DemonicCounsel extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Demon card");

    static {
        filter.add(SubType.DEMON.getPredicate());
    }

    public DemonicCounsel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Search your library for a Demon card, reveal it, put it into your hand, then shuffle.
        // Delirium -- If there are four or more card types among cards in your graveyard, instead search your library for any card, put it into your hand, then shuffle.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(), false),
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true),
                DeliriumCondition.instance, "search your library for a Demon card, reveal it, " +
                "put it into your hand, then shuffle.<br>" + AbilityWord.DELIRIUM.formatWord() +
                "If there are four or more card types among cards in your graveyard, " +
                "instead search your library for any card, put it into your hand, then shuffle."
        ));
        this.getSpellAbility().addHint(CardTypesInGraveyardCount.YOU.getHint());
    }

    private DemonicCounsel(final DemonicCounsel card) {
        super(card);
    }

    @Override
    public DemonicCounsel copy() {
        return new DemonicCounsel(this);
    }
}
