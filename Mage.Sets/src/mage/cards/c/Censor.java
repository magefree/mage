
package mage.cards.c;

import java.util.UUID;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;

/**
 *
 * @author fireshoes
 */
public final class Censor extends CardImpl {

    public Censor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Counter target spell unless its controller pays {1}.
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new CounterUnlessPaysEffect(new GenericManaCost(1)));

        // Cycling {U}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{U}")));

    }

    private Censor(final Censor card) {
        super(card);
    }

    @Override
    public Censor copy() {
        return new Censor(this);
    }
}
