

package mage.cards.j;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class JacesIngenuity extends CardImpl {

    public JacesIngenuity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{U}{U}");

        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(3));
    }

    private JacesIngenuity(final JacesIngenuity card) {
        super(card);
    }

    @Override
    public JacesIngenuity copy() {
        return new JacesIngenuity(this);
    }
}

