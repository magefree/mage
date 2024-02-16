
package mage.cards.d;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class DjerusResolve extends CardImpl {

    public DjerusResolve(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Untap target creature.
        Effect effect = new UntapTargetEffect();
        effect.setText("Untap target creature");
        this.getSpellAbility().addEffect(effect);

        // Prevent all damage that would be dealt to it this turn.
        effect = new PreventDamageToTargetEffect(Duration.EndOfTurn, Integer.MAX_VALUE);
        effect.setText("Prevent all damage that would be dealt to it this turn");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));

    }

    private DjerusResolve(final DjerusResolve card) {
        super(card);
    }

    @Override
    public DjerusResolve copy() {
        return new DjerusResolve(this);
    }
}
