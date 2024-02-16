package mage.cards.t;

import mage.abilities.common.SagaAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.BeastToken;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheHuntsmansRedemption extends CardImpl {

    private static final FilterCard filter = new FilterCard("a creature or basic land card");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                Predicates.and(
                        SuperType.BASIC.getPredicate(),
                        CardType.LAND.getPredicate()
                )
        ));
    }

    public TheHuntsmansRedemption(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Create a 3/3 green Beast creature token.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, new CreateTokenEffect(new BeastToken()));

        // II -- You may sacrifice a creature. If you do, search your library for a creature or basic land card, reveal it, put it into your hand, then shuffle.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II,
                new DoIfCostPaid(
                        new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true),
                        new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT)
                )
        );

        // III -- Up to two target creatures each get +2/+2 and gain trample until end of turn.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III,
                new Effects(
                        new BoostTargetEffect(2, 2)
                                .setText("up to two target creatures each get +2/+2"),
                        new GainAbilityTargetEffect(TrampleAbility.getInstance())
                                .setText("and gain trample until end of turn")
                ), new TargetCreaturePermanent(0, 2)
        );
        this.addAbility(sagaAbility);
    }

    private TheHuntsmansRedemption(final TheHuntsmansRedemption card) {
        super(card);
    }

    @Override
    public TheHuntsmansRedemption copy() {
        return new TheHuntsmansRedemption(this);
    }
}
