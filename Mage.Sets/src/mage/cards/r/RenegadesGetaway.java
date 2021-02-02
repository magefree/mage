
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.permanent.token.ServoToken;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public final class RenegadesGetaway extends CardImpl {

    public RenegadesGetaway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // Target permanent gains indestructible until end of turn. Create a 1/1 colorless Servo artifact creature token.
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetPermanent());
        this.getSpellAbility().addEffect(new CreateTokenEffect(new ServoToken()));
    }

    private RenegadesGetaway(final RenegadesGetaway card) {
        super(card);
    }

    @Override
    public RenegadesGetaway copy() {
        return new RenegadesGetaway(this);
    }
}
