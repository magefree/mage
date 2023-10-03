package mage.cards.s;

import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.permanent.token.SwanSongBirdToken;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SongOfEarendil extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("creature you control without flying");

    static {
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public SongOfEarendil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}{U}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I-- Scry 2, then draw two cards.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, new ScryEffect(2, false),
                new DrawCardSourceControllerEffect(2).concatBy(", then")
        );

        // II-- Create a Treasure token and a 2/2 blue Bird creature token with flying.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II,
                new CreateTokenEffect(new TreasureToken()),
                new CreateTokenEffect(new SwanSongBirdToken())
                        .setText("and a 2/2 blue Bird creature token with flying")
        );

        // III-- Put a flying counter on each creature you control without flying.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III,
                new AddCountersAllEffect(CounterType.FLYING.createInstance(), filter)
        );

        this.addAbility(sagaAbility);
    }

    private SongOfEarendil(final SongOfEarendil card) {
        super(card);
    }

    @Override
    public SongOfEarendil copy() {
        return new SongOfEarendil(this);
    }
}
