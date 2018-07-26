package mage.cards.p;

import java.util.UUID;

import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;

/**
 *
 * @author NinthWorld
 */
public final class PsionicFeedback extends CardImpl {

    public PsionicFeedback(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");
        

        // Counter target spell unless its controller pays {3}.
        this.getSpellAbility().addEffect(new CounterUnlessPaysEffect(new GenericManaCost(3)));
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    public PsionicFeedback(final PsionicFeedback card) {
        super(card);
    }

    @Override
    public PsionicFeedback copy() {
        return new PsionicFeedback(this);
    }
}
