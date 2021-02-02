package mage.cards.s;

import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Sanguimancy extends CardImpl {

    public Sanguimancy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}");

        // You draw X cards and you lose X life, where X is your devotion to black.
        Effect effect = new DrawCardSourceControllerEffect(DevotionCount.B);
        effect.setText("You draw X cards");
        this.getSpellAbility().addEffect(effect);
        effect = new LoseLifeSourceControllerEffect(DevotionCount.B);
        effect.setText("and you lose X life, where X is your devotion to black");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addHint(DevotionCount.B.getHint());
    }

    private Sanguimancy(final Sanguimancy card) {
        super(card);
    }

    @Override
    public Sanguimancy copy() {
        return new Sanguimancy(this);
    }
}
