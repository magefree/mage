
package mage.cards.h;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author fireshoes
 */
public final class HieroglyphicIllumination extends CardImpl {

    public HieroglyphicIllumination(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}");

        // Draw two cards.
        getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));

        // Cycling {U}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{U}")));

    }

    private HieroglyphicIllumination(final HieroglyphicIllumination card) {
        super(card);
    }

    @Override
    public HieroglyphicIllumination copy() {
        return new HieroglyphicIllumination(this);
    }
}
