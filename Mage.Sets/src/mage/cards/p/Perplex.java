
package mage.cards.p;

import java.util.UUID;
import mage.abilities.costs.common.DiscardHandCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.keyword.TransmuteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;

/**
 *
 * @author Quercitron
 */
public final class Perplex extends CardImpl {

    public Perplex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}{B}");


        // Counter target spell unless its controller discards their hand.
        Effect effect = new CounterUnlessPaysEffect(new DiscardHandCost());
        effect.setText("Counter target spell unless its controller discards their hand");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetSpell());
        // Transmute {1}{U}{B}
        this.addAbility(new TransmuteAbility("{1}{U}{B}"));
    }

    private Perplex(final Perplex card) {
        super(card);
    }

    @Override
    public Perplex copy() {
        return new Perplex(this);
    }
}
