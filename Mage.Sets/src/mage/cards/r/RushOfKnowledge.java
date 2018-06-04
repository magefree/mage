
package mage.cards.r;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.HighestConvertedManaCostValue;
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
        DrawCardSourceControllerEffect effect = new DrawCardSourceControllerEffect(new HighestConvertedManaCostValue());
        effect.setText("Draw cards equal to the highest converted mana cost among permanents you control");
        this.getSpellAbility().addEffect(effect);
    }

    public RushOfKnowledge(final RushOfKnowledge card) {
        super(card);
    }

    @Override
    public RushOfKnowledge copy() {
        return new RushOfKnowledge(this);
    }
}
