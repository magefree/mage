package mage.cards.p;

import mage.abilities.dynamicvalue.common.ColorsOfManaSpentToCastCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class PainfulTruths extends CardImpl {

    public PainfulTruths(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // <i>Converge</i> &mdash; You draw X cards and lose X life, where X is the number of colors of mana spent to cast Painful Truths.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(ColorsOfManaSpentToCastCount.getInstance())
                .setText("you draw X cards"));
        this.getSpellAbility().addEffect(new LoseLifeSourceControllerEffect(ColorsOfManaSpentToCastCount.getInstance())
                .setText("and lose X life, where X is the number of colors of mana spent to cast this spell"));
        this.getSpellAbility().setAbilityWord(AbilityWord.CONVERGE);
    }

    private PainfulTruths(final PainfulTruths card) {
        super(card);
    }

    @Override
    public PainfulTruths copy() {
        return new PainfulTruths(this);
    }
}
