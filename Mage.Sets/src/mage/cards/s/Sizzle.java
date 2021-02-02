
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;

/**
 *
 * @author dustinconrad
 */
public final class Sizzle extends CardImpl {

    public Sizzle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{R}");


        // Sizzle deals 3 damage to each opponent.
        this.getSpellAbility().addEffect(new DamagePlayersEffect(3, TargetController.OPPONENT));
    }

    private Sizzle(final Sizzle card) {
        super(card);
    }

    @Override
    public Sizzle copy() {
        return new Sizzle(this);
    }
}
