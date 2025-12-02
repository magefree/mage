package mage.cards.o;

import mage.abilities.common.SagaAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.ExileSagaAndReturnTransformedEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.filter.FilterPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OkibaReckonerRaid extends TransformingDoubleFacedCard {

    private static final FilterPermanent filter = new FilterPermanent(SubType.VEHICLE, "Vehicles");

    public OkibaReckonerRaid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.ENCHANTMENT}, new SubType[]{SubType.SAGA}, "{B}",
                "Nezumi Road Captain",
                new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, new SubType[]{SubType.RAT, SubType.ROGUE}, "B");

        // Okiba Reckoner Raid
        this.getRightHalfCard().setPT(2, 2);

        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this.getLeftHalfCard());

        // I, II — Each opponent loses 1 life and you gain 1 life.
        sagaAbility.addChapterEffect(
                this.getLeftHalfCard(), SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II,
                new Effects(
                        new LoseLifeOpponentsEffect(1),
                        new GainLifeEffect(1).concatBy("and")
                )
        );

        // III — Exile this Saga, then return it to the battlefield transformed under your control.
        sagaAbility.addChapterEffect(this.getLeftHalfCard(), SagaChapter.CHAPTER_III, new ExileSagaAndReturnTransformedEffect());

        this.getLeftHalfCard().addAbility(sagaAbility);

        // Nezumi Road Captain
        // Menace
        this.getRightHalfCard().addAbility(new MenaceAbility(false));

        // Vehicles you control have menace.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new MenaceAbility(true), Duration.WhileOnBattlefield, filter)
        ));
    }

    private OkibaReckonerRaid(final OkibaReckonerRaid card) {
        super(card);
    }

    @Override
    public OkibaReckonerRaid copy() {
        return new OkibaReckonerRaid(this);
    }
}
