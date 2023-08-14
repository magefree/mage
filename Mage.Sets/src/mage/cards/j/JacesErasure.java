

package mage.cards.j;

import java.util.UUID;
import mage.abilities.common.DrawCardControllerTriggeredAbility;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class JacesErasure extends CardImpl {

    public JacesErasure(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{U}");


        DrawCardControllerTriggeredAbility ability = new DrawCardControllerTriggeredAbility(new MillCardsTargetEffect(1), true);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private JacesErasure(final JacesErasure card) {
        super(card);
    }

    @Override
    public JacesErasure copy() {
        return new JacesErasure(this);
    }
}
