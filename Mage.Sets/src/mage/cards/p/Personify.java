package mage.cards.p;

import java.util.UUID;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileThenReturnTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.ShapeshifterColorlessToken;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author muz
 */
public final class Personify extends CardImpl {

    public Personify(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Exile target creature you control, then return that card to the battlefield under its owner's control. Create a 1/1 colorless Shapeshifter creature token with changeling.
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addEffect(new ExileThenReturnTargetEffect(false, true));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new ShapeshifterColorlessToken());
    }

    private Personify(final Personify card) {
        super(card);
    }

    @Override
    public Personify copy() {
        return new Personify(this);
    }
}
