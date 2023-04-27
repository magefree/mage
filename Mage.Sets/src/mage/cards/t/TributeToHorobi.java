package mage.cards.t;

import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.CreateTokenAllEffect;
import mage.abilities.effects.common.ExileSagaAndReturnTransformedEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.game.permanent.token.RatRogueToken;

import java.util.UUID;

/**
 * @author weirddan455
 */
public final class TributeToHorobi extends CardImpl {

    public TributeToHorobi(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        this.subtype.add(SubType.SAGA);
        this.secondSideCardClazz = mage.cards.e.EchoOfDeathsWail.class;

        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I, II — Each opponent creates a 1/1 black Rat Rouge creature token.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II,
                new CreateTokenAllEffect(new RatRogueToken(), TargetController.OPPONENT)
        );

        // III — Exile this Saga, then return it to the battlefield transformed under your control.
        this.addAbility(new TransformAbility());
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new ExileSagaAndReturnTransformedEffect());

        this.addAbility(sagaAbility);
    }

    private TributeToHorobi(final TributeToHorobi card) {
        super(card);
    }

    @Override
    public TributeToHorobi copy() {
        return new TributeToHorobi(this);
    }
}
