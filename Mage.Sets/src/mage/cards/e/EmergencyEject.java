package mage.cards.e;

import mage.abilities.effects.common.CreateTokenControllerTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.LanderToken;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EmergencyEject extends CardImpl {

    public EmergencyEject(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Destroy target nonland permanent. Its controller creates a Lander token.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new CreateTokenControllerTargetEffect(new LanderToken()));
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
    }

    private EmergencyEject(final EmergencyEject card) {
        super(card);
    }

    @Override
    public EmergencyEject copy() {
        return new EmergencyEject(this);
    }
}
