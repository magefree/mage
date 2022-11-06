package mage.cards.m;

import mage.abilities.Mode;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.permanent.token.SoldierArtifactToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MishrasOnslaught extends CardImpl {

    public MishrasOnslaught(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{R}");

        // Choose one --
        // * Create two 1/1 colorless Soldier artifact creature tokens.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new SoldierArtifactToken(), 2));

        // * Creatures you control get +2/+0 until end of turn.
        this.getSpellAbility().addMode(new Mode(new BoostControlledEffect(2, 0, Duration.EndOfTurn)));
    }

    private MishrasOnslaught(final MishrasOnslaught card) {
        super(card);
    }

    @Override
    public MishrasOnslaught copy() {
        return new MishrasOnslaught(this);
    }
}
