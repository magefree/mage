package mage.cards.h;

import java.util.UUID;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.permanent.token.VillainToken;

/**
 *
 * @author muz
 */
public final class HireACrew extends CardImpl {

    public HireACrew(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Create a 2/1 black Villain creature token with menace, then creatures you control get +1/+0 until end of turn.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new VillainToken()));
        this.getSpellAbility().addEffect(new BoostControlledEffect(1, 0, Duration.EndOfTurn)
            .concatBy("then"));
    }

    private HireACrew(final HireACrew card) {
        super(card);
    }

    @Override
    public HireACrew copy() {
        return new HireACrew(this);
    }
}
