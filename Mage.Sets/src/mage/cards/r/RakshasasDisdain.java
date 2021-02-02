
package mage.cards.r;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;

/**
 *
 * @author emerald000
 */
public final class RakshasasDisdain extends CardImpl {

    public RakshasasDisdain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}");

        // Counter target spell unless its controller pays {1} for each card in your graveyard.
        Effect effect = new CounterUnlessPaysEffect(new CardsInControllerGraveyardCount());
        effect.setText("Counter target spell unless its controller pays {1} for each card in your graveyard");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private RakshasasDisdain(final RakshasasDisdain card) {
        super(card);
    }

    @Override
    public RakshasasDisdain copy() {
        return new RakshasasDisdain(this);
    }
}
