package mage.cards.b;

import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.AvacynToken;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.ForEachPlayerTargetsAdjuster;
import mage.target.targetpointer.EachTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BattleAtTheHelvault extends CardImpl {
    private static final FilterPermanent filter = new FilterPermanent("non-Saga, nonland permanent");
    static {
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
        filter.add(Predicates.not(SubType.SAGA.getPredicate()));
    }

    public BattleAtTheHelvault(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{W}{W}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I, II -- For each player, exile up to one target non-Saga, nonland permanent that player controls until Battle at the Helvault leaves the battlefield.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II,
                (ability) -> {
                    ability.addEffect(new ExileUntilSourceLeavesEffect()
                            .setText("for each player, exile up to one target non-Saga, " +
                                    "nonland permanent that player controls until {this} leaves the battlefield")
                            .setTargetPointer(new EachTargetPointer()));
                    ability.addTarget(new TargetPermanent(0, 1, filter));
                    ability.setTargetAdjuster(new ForEachPlayerTargetsAdjuster(false, false));
                });

        // III -- Create Avacyn, a legendary 8/8 white Angel creature token with flying, vigilance, and indestructible.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III,
                new CreateTokenEffect(new AvacynToken())
        );
        this.addAbility(sagaAbility);
    }

    private BattleAtTheHelvault(final BattleAtTheHelvault card) {
        super(card);
    }

    @Override
    public BattleAtTheHelvault copy() {
        return new BattleAtTheHelvault(this);
    }
}
