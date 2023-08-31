package mage.cards.t;

import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.game.permanent.token.FoodToken;
import mage.game.permanent.token.TheEleventhHourToken;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheEleventhHour extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Doctor card");

    static {
        filter.add(SubType.DOCTOR.getPredicate());
    }

    public TheEleventhHour(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Search your library for a Doctor card, reveal it, put it into your hand, then shuffle.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I,
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true)
        );

        // II -- Create a Food token and a 1/1 white Human creature token with "Doctor spells you cast cost 1 less to cast."
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II,
                new CreateTokenEffect(new FoodToken()),
                new CreateTokenEffect(new TheEleventhHourToken())
                        .setText("and a 1/1 white Human creature token with " +
                                "\"Doctor spells you cast cost {1} less to cast.\"")
        );

        // III -- Create a token that's a copy of target creature, except it's a legendary Alien named Prisoner Zero.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III, SagaChapter.CHAPTER_III,
                new CreateTokenCopyTargetEffect()
                        .setPermanentModifier(token -> {
                            token.addSuperType(SuperType.LEGENDARY);
                            token.removeAllCreatureTypes();
                            token.addSubType(SubType.ALIEN);
                            token.setName("Prisoner Zero");
                        })
                        .setText("create a token that's a copy of target creature, " +
                                "except it's a legendary Alien named Prisoner Zero"),
                new TargetCreaturePermanent()
        );
        this.addAbility(sagaAbility);
    }

    private TheEleventhHour(final TheEleventhHour card) {
        super(card);
    }

    @Override
    public TheEleventhHour copy() {
        return new TheEleventhHour(this);
    }
}
