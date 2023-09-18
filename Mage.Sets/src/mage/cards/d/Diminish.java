

package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class Diminish extends CardImpl {

    public Diminish(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");

        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new SetBasePowerToughnessTargetEffect(1, 1, Duration.EndOfTurn));
    }

    private Diminish(final Diminish card) {
        super(card);
    }

    @Override
    public Diminish copy() {
        return new Diminish(this);
    }

}
