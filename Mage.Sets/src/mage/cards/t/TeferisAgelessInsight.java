package mage.cards.t;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.replacement.DrawExceptFirstDrawTwoReplacementEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.watchers.common.CardsDrawnDuringDrawStepWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TeferisAgelessInsight extends CardImpl {

    public TeferisAgelessInsight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}{U}");
        this.supertype.add(SuperType.LEGENDARY);

        // If you would draw a card except the first one you draw in each of your draw steps, draw two cards instead.
        this.addAbility(new SimpleStaticAbility(new DrawExceptFirstDrawTwoReplacementEffect()), new CardsDrawnDuringDrawStepWatcher());
    }

    private TeferisAgelessInsight(final TeferisAgelessInsight card) {
        super(card);
    }

    @Override
    public TeferisAgelessInsight copy() {
        return new TeferisAgelessInsight(this);
    }
}
