
package mage.cards.h;

import java.util.UUID;
import mage.abilities.effects.common.continuous.LoseAllAbilitiesTargetEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 *
 */
public final class Humble extends CardImpl {

    public Humble(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Target creature loses all abilities and becomes 0/1 until end of turn.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new LoseAllAbilitiesTargetEffect(Duration.EndOfTurn)
                .setText("Target creature loses all abilities"));
        this.getSpellAbility().addEffect(new SetBasePowerToughnessTargetEffect(0, 1, Duration.EndOfTurn)
                .setText("and becomes 0/1 until end of turn"));
    }

    private Humble(final Humble card) {
        super(card);
    }

    @Override
    public Humble copy() {
        return new Humble(this);
    }
}
