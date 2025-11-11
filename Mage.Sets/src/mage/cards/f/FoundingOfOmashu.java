package mage.cards.f;

import mage.abilities.common.SagaAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.game.permanent.token.AllyToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FoundingOfOmashu extends CardImpl {

    public FoundingOfOmashu(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Create two 1/1 white Ally creature tokens.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, new CreateTokenEffect(new AllyToken(), 2));

        // II -- You may discard a card. If you do, draw a card.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, new DoIfCostPaid(new DrawCardSourceControllerEffect(1), new DiscardCardCost()));

        // III -- Creatures you control get +1/+0 until end of turn.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new BoostControlledEffect(1, 0, Duration.EndOfTurn));
        this.addAbility(sagaAbility);
    }

    private FoundingOfOmashu(final FoundingOfOmashu card) {
        super(card);
    }

    @Override
    public FoundingOfOmashu copy() {
        return new FoundingOfOmashu(this);
    }
}
