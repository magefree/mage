
package mage.cards.h;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.PreventAllDamageByAllPermanentsEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author fireshoes
 */
public final class HazeOfPollen extends CardImpl {

    public HazeOfPollen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");


        // Prevent all combat damage that would be dealt this turn.
        Effect effect = new PreventAllDamageByAllPermanentsEffect(Duration.EndOfTurn, true);
        effect.setText("Prevent all combat damage that would be dealt this turn");
        getSpellAbility().addEffect(effect);

        // Cycling {3}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{3}")));

    }

    private HazeOfPollen(final HazeOfPollen card) {
        super(card);
    }

    @Override
    public HazeOfPollen copy() {
        return new HazeOfPollen(this);
    }
}
