package mage.cards.f;

import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ForgingTheTyriteSword extends CardImpl {

    private static final FilterCard filter
            = new FilterCard("card named Halvar, God of Battle or an Equipment card");

    static {
        filter.add(Predicates.or(
                new NamePredicate("Halvar, God of Battle"),
                SubType.EQUIPMENT.getPredicate()
        ));
    }

    public ForgingTheTyriteSword(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}{W}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I, II — Create a Treasure token.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II,
                new CreateTokenEffect(new TreasureToken())
        );

        // III — Search your library for a card named Halvar, God of Battle or an Equipment card, reveal it, put it into your hand, then shuffle your library.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III,
                new SearchLibraryPutInHandEffect(
                        new TargetCardInLibrary(filter), true
                )
        );

        this.addAbility(sagaAbility);
    }

    private ForgingTheTyriteSword(final ForgingTheTyriteSword card) {
        super(card);
    }

    @Override
    public ForgingTheTyriteSword copy() {
        return new ForgingTheTyriteSword(this);
    }
}
