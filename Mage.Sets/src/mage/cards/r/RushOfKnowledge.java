
package mage.cards.r;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.HighestManaValueCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author LevelX2
 */
public final class RushOfKnowledge extends CardImpl {

    public RushOfKnowledge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{U}");

        // Draw cards equal to the highest converted mana cost among permanents you control.
        DrawCardSourceControllerEffect effect = new DrawCardSourceControllerEffect(new HighestManaValueCount());
        effect.setText("Draw cards equal to the highest mana value among permanents you control");
        this.getSpellAbility().addEffect(effect);
    }

    private RushOfKnowledge(final RushOfKnowledge card) {
        super(card);
    }

    @Override
    public RushOfKnowledge copy() {
        return new RushOfKnowledge(this);
    }
}
