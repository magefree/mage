
package mage.cards.p;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.ColorsOfManaSpentToCastCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;

/**
 *
 * @author fireshoes
 */
public final class PainfulTruths extends CardImpl {

    public PainfulTruths(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}");

        // <i>Converge</i> &mdash; You draw X cards and lose X life, where X is the number of colors of mana spent to cast Painful Truths.
        getSpellAbility().setAbilityWord(AbilityWord.CONVERGE);
        Effect effect = new DrawCardSourceControllerEffect(ColorsOfManaSpentToCastCount.getInstance());
        effect.setText("You draw X cards");
        getSpellAbility().addEffect(effect);
        effect = new LoseLifeSourceControllerEffect(ColorsOfManaSpentToCastCount.getInstance());
        effect.setText("and you lose X life, where X is the number of colors of mana spent to cast this spell");
        getSpellAbility().addEffect(effect);
    }

    private PainfulTruths(final PainfulTruths card) {
        super(card);
    }

    @Override
    public PainfulTruths copy() {
        return new PainfulTruths(this);
    }
}
