package mage.cards.s;

import mage.MageInt;
import mage.Mana;
import mage.abilities.common.SagaAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SagaChapter;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SummonGFIfrit extends CardImpl {

    public SummonGFIfrit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.SAGA);
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after IV.)
        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_IV);

        // I, II -- You may discard a card. If you do, draw a card.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II,
                new DoIfCostPaid(new DrawCardSourceControllerEffect(1), new DiscardCardCost())
        );

        // III, IV -- Add {R}.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III, SagaChapter.CHAPTER_IV,
                new BasicManaEffect(Mana.RedMana(1))
        );
        this.addAbility(sagaAbility);
    }

    private SummonGFIfrit(final SummonGFIfrit card) {
        super(card);
    }

    @Override
    public SummonGFIfrit copy() {
        return new SummonGFIfrit(this);
    }
}
