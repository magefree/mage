package mage.cards.e;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.PowerstoneToken;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ExcavationExplosion extends CardImpl {

    public ExcavationExplosion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Excavation Explosion deals 3 damage to any target. Create a tapped Powerstone token.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().addEffect(new CreateTokenEffect(new PowerstoneToken(), 1, true));
    }

    private ExcavationExplosion(final ExcavationExplosion card) {
        super(card);
    }

    @Override
    public ExcavationExplosion copy() {
        return new ExcavationExplosion(this);
    }
}
