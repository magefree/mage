
package mage.cards.c;

import java.util.UUID;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;

/**
 *
 * @author emerald000
 */
public final class CountervailingWinds extends CardImpl {

    public CountervailingWinds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Counter target spell unless its controller pays {1} for each card in your graveyard.
        Effect effect = new CounterUnlessPaysEffect(new CardsInControllerGraveyardCount());
        effect.setText("Counter target spell unless its controller pays {1} for each card in your graveyard");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetSpell());

        // Cycling {2}
        this.addAbility(new CyclingAbility(new GenericManaCost(2)));

    }

    private CountervailingWinds(final CountervailingWinds card) {
        super(card);
    }

    @Override
    public CountervailingWinds copy() {
        return new CountervailingWinds(this);
    }
}
