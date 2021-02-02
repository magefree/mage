
package mage.cards.b;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.DelveAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class BecomeImmense extends CardImpl {

    public BecomeImmense(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{5}{G}");

        // Delve (Each card you exile from your graveyard while casting this spell pays for {1}.)
        this.addAbility(new DelveAbility());
        // Target creature gets +6/+6 until end of turn
        this.getSpellAbility().addEffect(new BoostTargetEffect(6, 6, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private BecomeImmense(final BecomeImmense card) {
        super(card);
    }

    @Override
    public BecomeImmense copy() {
        return new BecomeImmense(this);
    }
}
