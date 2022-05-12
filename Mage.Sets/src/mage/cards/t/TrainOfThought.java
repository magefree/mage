
package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.ReplicateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author LevelX2
 */
public final class TrainOfThought extends CardImpl {

    public TrainOfThought(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{U}");


        // Replicate {1}{U}
        this.addAbility(new ReplicateAbility("{1}{U}"));

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
    }

    private TrainOfThought(final TrainOfThought card) {
        super(card);
    }

    @Override
    public TrainOfThought copy() {
        return new TrainOfThought(this);
    }
}
