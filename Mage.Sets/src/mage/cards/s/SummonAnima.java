package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SummonAnima extends CardImpl {

    public SummonAnima(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{4}{B}{B}");

        this.subtype.add(SubType.SAGA);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after IV.)
        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_IV);

        // I, II, III -- Pain -- You draw a card and you lose 1 life.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_III, ability -> {
            ability.addEffect(new DrawCardSourceControllerEffect(1, true));
            ability.addEffect(new LoseLifeSourceControllerEffect(1).concatBy("and"));
            ability.withFlavorWord("Pain");
        });

        // IV -- Oblivion -- Each opponent sacrifices a creature of their choice and loses 3 life.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_IV, ability -> {
            ability.addEffect(new SacrificeOpponentsEffect(StaticFilters.FILTER_PERMANENT_A_CREATURE));
            ability.addEffect(new LoseLifeOpponentsEffect(3).setText("and loses 3 life"));
            ability.withFlavorWord("Oblivion");
        });
        this.addAbility(sagaAbility);

        // Menace
        this.addAbility(new MenaceAbility());
    }

    private SummonAnima(final SummonAnima card) {
        super(card);
    }

    @Override
    public SummonAnima copy() {
        return new SummonAnima(this);
    }
}
