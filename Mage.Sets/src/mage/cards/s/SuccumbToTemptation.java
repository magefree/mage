package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author LevelX2
 */
public final class SuccumbToTemptation extends CardImpl {

    public SuccumbToTemptation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{B}{B}");

        // You draw two cards and you lose 2 life.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2, "you"));
        Effect effect = new LoseLifeSourceControllerEffect(2);
        effect.setText("and you lose 2 life");
        this.getSpellAbility().addEffect(effect);
    }

    private SuccumbToTemptation(final SuccumbToTemptation card) {
        super(card);
    }

    @Override
    public SuccumbToTemptation copy() {
        return new SuccumbToTemptation(this);
    }
}
