package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BraskasFinalAeon extends CardImpl {

    public BraskasFinalAeon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SAGA);
        this.subtype.add(SubType.NIGHTMARE);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);
        this.nightCard = true;
        this.color.setBlack(true);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I, II -- Jecht Beam -- Each opponent discards a card and you draw a card.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II, ability -> {
            ability.addEffect(new DiscardEachPlayerEffect(TargetController.OPPONENT));
            ability.addEffect(new DrawCardSourceControllerEffect(1).concatBy("and you"));
            ability.withFlavorWord("Jecht Beam");
        });

        // III -- Ultimate Jecht Shot -- Each opponent sacrifices two creatures of their choice.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, ability -> {
            ability.addEffect(new SacrificeOpponentsEffect(2, StaticFilters.FILTER_PERMANENT_CREATURES));
            ability.withFlavorWord("Ultimate Jecht Shot");
        });
        this.addAbility(sagaAbility.withShowSacText(true));

        // Menace
        this.addAbility(new MenaceAbility());
    }

    private BraskasFinalAeon(final BraskasFinalAeon card) {
        super(card);
    }

    @Override
    public BraskasFinalAeon copy() {
        return new BraskasFinalAeon(this);
    }
}
