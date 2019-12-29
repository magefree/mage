package mage.cards.s;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Sanguimancy extends CardImpl {

    private static final DynamicValue xValue = new DevotionCount(ColoredManaSymbol.B);

    public Sanguimancy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}");

        // You draw X cards and you lose X life, where X is your devotion to black.
        Effect effect = new DrawCardSourceControllerEffect(xValue);
        effect.setText("You draw X cards");
        this.getSpellAbility().addEffect(effect);
        effect = new LoseLifeSourceControllerEffect(xValue);
        effect.setText("and you lose X life, where X is your devotion to black");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addHint(new ValueHint("Devotion to black", xValue));
    }

    public Sanguimancy(final Sanguimancy card) {
        super(card);
    }

    @Override
    public Sanguimancy copy() {
        return new Sanguimancy(this);
    }
}
