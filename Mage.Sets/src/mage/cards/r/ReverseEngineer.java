
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.ImproviseAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author fireshoes
 */
public final class ReverseEngineer extends CardImpl {

    public ReverseEngineer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}{U}");

        // Improvise
        this.addAbility(new ImproviseAbility());

        // Draw three cards.
        getSpellAbility().addEffect(new DrawCardSourceControllerEffect(3));
    }

    private ReverseEngineer(final ReverseEngineer card) {
        super(card);
    }

    @Override
    public ReverseEngineer copy() {
        return new ReverseEngineer(this);
    }
}
