
package mage.cards.r;

import mage.abilities.dynamicvalue.common.GreatestAmongPermanentsValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class RushOfKnowledge extends CardImpl {

    public RushOfKnowledge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}");

        // Draw cards equal to the greatest mana value among permanents you control.
        DrawCardSourceControllerEffect effect = new DrawCardSourceControllerEffect(GreatestAmongPermanentsValue.MANAVALUE_CONTROLLED_PERMANENTS);
        effect.setText("Draw cards equal to the greatest mana value among permanents you control");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addHint(GreatestAmongPermanentsValue.MANAVALUE_CONTROLLED_PERMANENTS.getHint());
    }

    private RushOfKnowledge(final RushOfKnowledge card) {
        super(card);
    }

    @Override
    public RushOfKnowledge copy() {
        return new RushOfKnowledge(this);
    }
}
