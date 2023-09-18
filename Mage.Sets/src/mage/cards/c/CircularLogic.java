
package mage.cards.c;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;

/**
 *
 * @author magenoxx_at_gmail.com
 */
public final class CircularLogic extends CardImpl {

    public CircularLogic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}");

        // Counter target spell unless its controller pays {1} for each card in your graveyard.
        Effect effect = new CounterUnlessPaysEffect(new CardsInControllerGraveyardCount());
        effect.setText("Counter target spell unless its controller pays {1} for each card in your graveyard");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetSpell());

        // Madness {U}
        this.addAbility(new MadnessAbility(new ManaCostsImpl<>("{U}")));
    }

    private CircularLogic(final CircularLogic card) {
        super(card);
    }

    @Override
    public CircularLogic copy() {
        return new CircularLogic(this);
    }
}
