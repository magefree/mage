package mage.cards.c;

import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.keyword.CasualtyAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CutOfTheProfits extends CardImpl {

    public CutOfTheProfits(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{B}{B}");

        // Casualty 3
        this.addAbility(new CasualtyAbility(3));

        // You draw X cards and you lose X life.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(ManacostVariableValue.REGULAR, "you"));
        this.getSpellAbility().addEffect(new LoseLifeSourceControllerEffect(ManacostVariableValue.REGULAR).concatBy("and"));
    }

    private CutOfTheProfits(final CutOfTheProfits card) {
        super(card);
    }

    @Override
    public CutOfTheProfits copy() {
        return new CutOfTheProfits(this);
    }
}
