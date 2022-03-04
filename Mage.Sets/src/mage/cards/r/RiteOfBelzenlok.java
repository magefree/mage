
package mage.cards.r;

import java.util.UUID;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SagaChapter;
import mage.game.permanent.token.BelzenlokClericToken;
import mage.game.permanent.token.BelzenlokDemonToken;

/**
 *
 * @author TheElk801
 */
public final class RiteOfBelzenlok extends CardImpl {

    public RiteOfBelzenlok(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{B}");

        this.subtype.add(SubType.SAGA);

        // <i>(As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)</i>
        SagaAbility sagaAbility = new SagaAbility(this);
        // I, II — Create two 0/1 black Cleric creature tokens.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II, new CreateTokenEffect(new BelzenlokClericToken(), 2));
        // III — Create a 6/6 black Demon creature token with flying, trample, and "At the beginning of your upkeep, sacrifice another creature.  If you can't, this creature deals 6 damage to you."
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new CreateTokenEffect(new BelzenlokDemonToken()));
        this.addAbility(sagaAbility);
    }

    private RiteOfBelzenlok(final RiteOfBelzenlok card) {
        super(card);
    }

    @Override
    public RiteOfBelzenlok copy() {
        return new RiteOfBelzenlok(this);
    }
}
