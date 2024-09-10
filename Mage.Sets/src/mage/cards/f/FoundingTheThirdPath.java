package mage.cards.f;

import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.ExileTargetCardCopyAndCastEffect;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.effects.common.cost.CastFromHandForFreeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FoundingTheThirdPath extends CardImpl {

    private static final FilterCard filter
            = new FilterInstantOrSorceryCard("an instant or sorcery spell with mana value 1 or 2");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.MORE_THAN, 0));
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 3));
    }

    public FoundingTheThirdPath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        this.subtype.add(SubType.SAGA);

        // Read ahead
        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_III, true);

        // I -- You may cast an instant or sorcery spell with mana value 1 or 2 from your hand without paying its mana cost.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, new CastFromHandForFreeEffect(filter));

        // II -- Target player mills four cards.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_II,
                new MillCardsTargetEffect(4), new TargetPlayer()
        );

        // III -- Exile target instant or sorcery card from your graveyard. Copy it. You may cast the copy.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III, SagaChapter.CHAPTER_III,
                new ExileTargetCardCopyAndCastEffect(false).setText(
                        "exile target instant or sorcery card from your graveyard. Copy it. You may cast the copy"),
                new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY_FROM_YOUR_GRAVEYARD)
        );
        this.addAbility(sagaAbility);
    }

    private FoundingTheThirdPath(final FoundingTheThirdPath card) {
        super(card);
    }

    @Override
    public FoundingTheThirdPath copy() {
        return new FoundingTheThirdPath(this);
    }
}