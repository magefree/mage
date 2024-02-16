
package mage.cards.b;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public final class BrokenConcentration extends CardImpl {

    public BrokenConcentration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}{U}");

        // Counter target spell.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
        // Madness {3}{U}
        this.addAbility(new MadnessAbility(new ManaCostsImpl<>("{3}{U}")));
    }

    private BrokenConcentration(final BrokenConcentration card) {
        super(card);
    }

    @Override
    public BrokenConcentration copy() {
        return new BrokenConcentration(this);
    }
}
