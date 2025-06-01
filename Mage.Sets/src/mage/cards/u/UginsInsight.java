
package mage.cards.u;

import mage.abilities.dynamicvalue.common.GreatestAmongPermanentsValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class UginsInsight extends CardImpl {

    public UginsInsight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}{U}");

        // Scry X, where X is the greatest mana value among permanents you control, then draw three cards.
        this.getSpellAbility().addEffect(
                new ScryEffect(GreatestAmongPermanentsValue.MANAVALUE_CONTROLLED_PERMANENTS)
        );
        this.getSpellAbility().addHint(GreatestAmongPermanentsValue.MANAVALUE_CONTROLLED_PERMANENTS.getHint());
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(3).concatBy(", then"));
    }

    private UginsInsight(final UginsInsight card) {
        super(card);
    }

    @Override
    public UginsInsight copy() {
        return new UginsInsight(this);
    }
}