package mage.cards.j;

import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileAndReturnSourceEffect;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JechtReluctantGuardian extends TransformingDoubleFacedCard {

    public JechtReluctantGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WARRIOR}, "{3}{B}",
                "Braska's Final Aeon",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, new SubType[]{SubType.SAGA, SubType.NIGHTMARE}, "B");

        // Jecht, Reluctant Guardian
        this.getLeftHalfCard().setPT(4, 3);

        // Menace
        this.getLeftHalfCard().addAbility(new MenaceAbility());

        // Whenever Jecht deals combat damage to a player, you may exile it, then return it to the battlefield transformed under its owner's control.
        this.getLeftHalfCard().addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new ExileAndReturnSourceEffect(PutCards.BATTLEFIELD_TRANSFORMED)
                        .setText("exile it, then return it to the battlefield transformed under its owner's control"), true
        ));

        // Braska's Final Aeon
        this.getRightHalfCard().setPT(7, 7);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this.getRightHalfCard());

        // I, II -- Jecht Beam -- Each opponent discards a card and you draw a card.
        sagaAbility.addChapterEffect(this.getRightHalfCard(), SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II, ability -> {
            ability.addEffect(new DiscardEachPlayerEffect(TargetController.OPPONENT));
            ability.addEffect(new DrawCardSourceControllerEffect(1).concatBy("and you"));
            ability.withFlavorWord("Jecht Beam");
        });

        // III -- Ultimate Jecht Shot -- Each opponent sacrifices two creatures of their choice.
        sagaAbility.addChapterEffect(this.getRightHalfCard(), SagaChapter.CHAPTER_III, ability -> {
            ability.addEffect(new SacrificeOpponentsEffect(2, StaticFilters.FILTER_PERMANENT_CREATURES));
            ability.withFlavorWord("Ultimate Jecht Shot");
        });
        this.getRightHalfCard().addAbility(sagaAbility.withShowSacText(true));

        // Menace
        this.getRightHalfCard().addAbility(new MenaceAbility());
    }

    private JechtReluctantGuardian(final JechtReluctantGuardian card) {
        super(card);
    }

    @Override
    public JechtReluctantGuardian copy() {
        return new JechtReluctantGuardian(this);
    }
}
