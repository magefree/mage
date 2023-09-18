
package mage.cards.p;

import mage.abilities.effects.common.CreateTokenControllerTargetPermanentEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.ApeToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author dustinconrad
 */
public final class Pongify extends CardImpl {

    public Pongify(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Destroy target creature. It can't be regenerated. That creature's controller creates a 3/3 green Ape creature token.
        this.getSpellAbility().addEffect(new DestroyTargetEffect(true));
        this.getSpellAbility().addEffect(new CreateTokenControllerTargetPermanentEffect(new ApeToken()));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private Pongify(final Pongify card) {
        super(card);
    }

    @Override
    public Pongify copy() {
        return new Pongify(this);
    }
}