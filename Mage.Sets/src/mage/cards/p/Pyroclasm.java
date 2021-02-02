

package mage.cards.p;

import java.util.UUID;
import mage.abilities.effects.common.DamageAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class Pyroclasm extends CardImpl {

    public Pyroclasm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{R}");

        this.getSpellAbility().addEffect(new DamageAllEffect(2, new FilterCreaturePermanent()));
    }

    private Pyroclasm(final Pyroclasm card) {
        super(card);
    }

    @Override
    public Pyroclasm copy() {
        return new Pyroclasm(this);
    }
}
