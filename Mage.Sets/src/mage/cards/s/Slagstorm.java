

package mage.cards.s;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class Slagstorm extends CardImpl {

    public Slagstorm (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{R}{R}");

        this.getSpellAbility().addEffect(new DamageAllEffect(3, new FilterCreaturePermanent()));
        Mode mode = new Mode(new DamagePlayersEffect(3));
        this.getSpellAbility().addMode(mode);
    }

    public Slagstorm (final Slagstorm card) {
        super(card);
    }

    @Override
    public Slagstorm copy() {
        return new Slagstorm(this);
    }

}
