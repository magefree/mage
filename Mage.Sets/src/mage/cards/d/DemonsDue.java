package mage.cards.d;

import java.util.UUID;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author weirddan455
 */
public final class DemonsDue extends CardImpl {

    public DemonsDue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{B}");

        // Scry 2, then draw 2 cards. You lose 2 life.
        this.getSpellAbility().addEffect(new ScryEffect(2, false));
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2).concatBy(", then"));
        this.getSpellAbility().addEffect(new LoseLifeSourceControllerEffect(2));
    }

    private DemonsDue(final DemonsDue card) {
        super(card);
    }

    @Override
    public DemonsDue copy() {
        return new DemonsDue(this);
    }
}
