package mage.cards.s;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SuccumbToTemptation extends CardImpl {

    public SuccumbToTemptation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}{B}");

        // You draw two cards and you lose 2 life.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2, true));
        this.getSpellAbility().addEffect(new LoseLifeSourceControllerEffect(2).concatBy("and"));
    }

    private SuccumbToTemptation(final SuccumbToTemptation card) {
        super(card);
    }

    @Override
    public SuccumbToTemptation copy() {
        return new SuccumbToTemptation(this);
    }
}
