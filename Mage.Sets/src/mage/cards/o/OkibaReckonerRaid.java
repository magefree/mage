package mage.cards.o;

import mage.abilities.common.SagaAbility;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.ExileSagaAndReturnTransformedEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SagaChapter;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OkibaReckonerRaid extends CardImpl {

    public OkibaReckonerRaid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}");

        this.subtype.add(SubType.SAGA);
        this.secondSideCardClazz = mage.cards.n.NezumiRoadCaptain.class;

        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I, II — Each opponent loses 1 life and you gain 1 life.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II,
                new Effects(
                        new LoseLifeOpponentsEffect(1),
                        new GainLifeEffect(1).concatBy("and")
                )
        );

        // III — Exile this Saga, then return it to the battlefield transformed under your control.
        this.addAbility(new TransformAbility());
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new ExileSagaAndReturnTransformedEffect());

        this.addAbility(sagaAbility);
    }

    private OkibaReckonerRaid(final OkibaReckonerRaid card) {
        super(card);
    }

    @Override
    public OkibaReckonerRaid copy() {
        return new OkibaReckonerRaid(this);
    }
}
