package mage.cards.c;

import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.TreasureToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CityOfDeath extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("non-Saga token you control");

    static {
        filter.add(Predicates.not(SubType.SAGA.getPredicate()));
        filter.add(TokenPredicate.TRUE);
    }

    public CityOfDeath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after VI.)
        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_VI);

        // I -- Create a Treasure token.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, new CreateTokenEffect(new TreasureToken()));

        // II, II, III, IV, V, VI -- Create a token that's a copy of target non-Saga token you control.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_VI,
                new CreateTokenCopyTargetEffect(), new TargetPermanent(filter)
        );

        this.addAbility(sagaAbility);
    }

    private CityOfDeath(final CityOfDeath card) {
        super(card);
    }

    @Override
    public CityOfDeath copy() {
        return new CityOfDeath(this);
    }
}
