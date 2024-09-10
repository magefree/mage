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
public final class SecondBreakfast extends CardImpl {

    public SecondBreakfast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Up to two target creatures each get +2/+1 until end of turn. Create a Food token.
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 1));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new FoodToken()));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 2));
    }

    private SecondBreakfast(final SecondBreakfast card) {
        super(card);
    }

    @Override
    public SecondBreakfast copy() {
        return new SecondBreakfast(this);
    }
}
