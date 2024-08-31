package mage.cards.s;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.FoodToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Savor extends CardImpl {

    public Savor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Target creature gets -2/-2 until end of turn. Create a Food token.
        this.getSpellAbility().addEffect(new BoostTargetEffect(-2, -2));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new FoodToken()));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private Savor(final Savor card) {
        super(card);
    }

    @Override
    public Savor copy() {
        return new Savor(this);
    }
}
