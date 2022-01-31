
package mage.cards.h;

import java.util.UUID;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.KnightToken;

/**
 *
 * @author LevelX2
 */
public final class HistoryOfBenalia extends CardImpl {

    public HistoryOfBenalia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}{W}");

        this.subtype.add(SubType.SAGA);

        // <i>(As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)</i>
        SagaAbility sagaAbility = new SagaAbility(this);
        // I, II — Create a 2/2 white Knight creature token with vigilance.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II, new CreateTokenEffect(new KnightToken()));
        // III — Knights you control get +2/+1 until end of turn.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III,
                new BoostControlledEffect(2, 1, Duration.EndOfTurn, new FilterCreaturePermanent(SubType.KNIGHT, "Knights")));
        this.addAbility(sagaAbility);
    }

    private HistoryOfBenalia(final HistoryOfBenalia card) {
        super(card);
    }

    @Override
    public HistoryOfBenalia copy() {
        return new HistoryOfBenalia(this);
    }
}
