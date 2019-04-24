
package mage.cards.t;

import java.util.UUID;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author LevelX2
 */
public final class TheEldestReborn extends CardImpl {

    private static final FilterCard filter = new FilterCard("creature or planeswalker card from a graveyard");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.PLANESWALKER)
        ));
    }

    private static final FilterControlledPermanent filterSacrifice = new FilterControlledPermanent("creature or planeswalker");
    static {
        filterSacrifice.add(Predicates.or(
                        new CardTypePredicate(CardType.CREATURE),
                        new CardTypePredicate(CardType.PLANESWALKER)
                ));

    }

    public TheEldestReborn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{B}");

        this.subtype.add(SubType.SAGA);

        // <i>(As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)</i>
        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_III);
        // I — Each opponent sacrifices a creature or planeswalker.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I,
                new SacrificeOpponentsEffect(filterSacrifice)
        );

        // II — Each opponent discards a card.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II,
                new DiscardEachPlayerEffect(TargetController.OPPONENT)
        );

        // III — Put target creature or planeswalker card from a graveyard onto the battlefield under your control.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III, SagaChapter.CHAPTER_III,
                new ReturnFromGraveyardToBattlefieldTargetEffect()
                        .setText("Put target creature or planeswalker card from a graveyard onto the battlefield under your control"),
                new TargetCardInGraveyard(filter)
        );
        this.addAbility(sagaAbility);

    }

    public TheEldestReborn(final TheEldestReborn card) {
        super(card);
    }

    @Override
    public TheEldestReborn copy() {
        return new TheEldestReborn(this);
    }
}
