
package mage.cards.b;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author LevelX2
 */
public final class BrilliantPlan extends CardImpl {

    public BrilliantPlan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{U}");


        // Draw three cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(3));
    }

    private BrilliantPlan(final BrilliantPlan card) {
        super(card);
    }

    @Override
    public BrilliantPlan copy() {
        return new BrilliantPlan(this);
    }
}
