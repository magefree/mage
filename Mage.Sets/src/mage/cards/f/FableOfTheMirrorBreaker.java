package mage.cards.f;

import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileSagaAndReturnTransformedEffect;
import mage.abilities.effects.common.discard.DiscardAndDrawThatManyEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.game.permanent.token.FableOfTheMirrorBreakerToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FableOfTheMirrorBreaker extends CardImpl {

    public FableOfTheMirrorBreaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        this.subtype.add(SubType.SAGA);
        this.secondSideCardClazz = mage.cards.r.ReflectionOfKikiJiki.class;

        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I — Create a 2/2 red Goblin Shaman creature token with "Whenever this creature attacks, create a Treasure token."
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, new CreateTokenEffect(new FableOfTheMirrorBreakerToken()));

        // II — You may discard up to two cards. If you do, draw that many cards.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, new DiscardAndDrawThatManyEffect(2)
                .setText("you may discard up to two cards. If you do, draw that many cards"));

        // III — Exile this Saga, then return it to the battlefield transformed under your control.
        this.addAbility(new TransformAbility());
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new ExileSagaAndReturnTransformedEffect());

        this.addAbility(sagaAbility);
    }

    private FableOfTheMirrorBreaker(final FableOfTheMirrorBreaker card) {
        super(card);
    }

    @Override
    public FableOfTheMirrorBreaker copy() {
        return new FableOfTheMirrorBreaker(this);
    }
}
