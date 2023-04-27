
package mage.cards.i;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetOpponentOrPlaneswalker;

/**
 *
 * @author fireshoes
 */
public final class InfernoJet extends CardImpl {

    public InfernoJet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{R}");

        // Inferno Jet deals 6 damage to target opponent.
        getSpellAbility().addEffect(new DamageTargetEffect(6));
        getSpellAbility().addTarget(new TargetOpponentOrPlaneswalker());

        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));

    }

    private InfernoJet(final InfernoJet card) {
        super(card);
    }

    @Override
    public InfernoJet copy() {
        return new InfernoJet(this);
    }
}
