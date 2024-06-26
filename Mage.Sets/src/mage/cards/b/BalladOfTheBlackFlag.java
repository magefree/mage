package mage.cards.b;

import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.MillThenPutInHandEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterHistoricCard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BalladOfTheBlackFlag extends CardImpl {

    private static final FilterCard filter = new FilterHistoricCard();

    public BalladOfTheBlackFlag(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}{U}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after IV.)
        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_IV);

        // I, II, III -- Mill three cards. You may put a historic card from among them into your hand.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_III,
                new MillThenPutInHandEffect(3, filter)
        );

        // IV - Historic spells you cast this turn cost {2} less to cast.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_IV,
                new SpellsCostReductionControllerEffect(filter, 2)
                        .setText("historic spells you cast this turn cost {2} less to cast")
        );

        this.addAbility(sagaAbility);
    }

    private BalladOfTheBlackFlag(final BalladOfTheBlackFlag card) {
        super(card);
    }

    @Override
    public BalladOfTheBlackFlag copy() {
        return new BalladOfTheBlackFlag(this);
    }
}
