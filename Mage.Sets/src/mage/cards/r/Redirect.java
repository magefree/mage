

package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.ChooseNewTargetsTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class Redirect extends CardImpl {

    public Redirect(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}{U}");


        // You may choose new targets for target spell.
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new ChooseNewTargetsTargetEffect());
    }

    private Redirect(final Redirect card) {
        super(card);
    }

    @Override
    public Redirect copy() {
        return new Redirect(this);
    }
}
