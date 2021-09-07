package mage.cards.s;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.ZombieDecayedToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Startle extends CardImpl {

    public Startle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Target creature gets -2/-0 until end of turn. Create a 2/2 black Zombie creature token with decayed.
        this.getSpellAbility().addEffect(new BoostTargetEffect(-2, 0));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new CreateTokenEffect(new ZombieDecayedToken()));

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
    }

    private Startle(final Startle card) {
        super(card);
    }

    @Override
    public Startle copy() {
        return new Startle(this);
    }
}
