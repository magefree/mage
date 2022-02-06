package mage.cards.e;

import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.ExileGraveyardAllTargetPlayerEffect;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandCard;
import mage.filter.common.FilterOpponentsCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ElspethsNightmare extends CardImpl {

    private static final FilterPermanent filter
            = new FilterOpponentsCreaturePermanent("creature an opponent controls with power 2 or less");
    private static final FilterCard filter2 = new FilterNonlandCard("noncreature, nonland card");

    static {
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
        filter2.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public ElspethsNightmare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        this.subtype.add(SubType.SAGA);

        // <i>(As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)</i>
        SagaAbility sagaAbility = new SagaAbility(this);

        // I - Destroy target creature an opponent controls with power 2 or less.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_I,
                new DestroyTargetEffect(), new TargetPermanent(filter)
        );

        // II - Target opponent reveals their hand. You choose a noncreature, nonland card from it. That player discards that card.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_II,
                new DiscardCardYouChooseTargetEffect(filter2), new TargetOpponent()
        );

        // III - Exile target opponent's graveyard.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III, SagaChapter.CHAPTER_III,
                new ExileGraveyardAllTargetPlayerEffect()
                        .setText("exile target opponent's graveyard"),
                new TargetOpponent()
        );
        this.addAbility(sagaAbility);
    }

    private ElspethsNightmare(final ElspethsNightmare card) {
        super(card);
    }

    @Override
    public ElspethsNightmare copy() {
        return new ElspethsNightmare(this);
    }
}
