
package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.continuous.SwitchPowerToughnessTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class Transmutation extends CardImpl {

    public Transmutation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{B}");

        // Switch target creature's power and toughness until end of turn.
        this.getSpellAbility().addEffect(new SwitchPowerToughnessTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private Transmutation(final Transmutation card) {
        super(card);
    }

    @Override
    public Transmutation copy() {
        return new Transmutation(this);
    }
}
