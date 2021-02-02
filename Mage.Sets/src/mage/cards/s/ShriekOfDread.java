
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FearAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class ShriekOfDread extends CardImpl {

    public ShriekOfDread(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{B}");

        // Target creature gains fear until end of turn.
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(FearAbility.getInstance(), Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private ShriekOfDread(final ShriekOfDread card) {
        super(card);
    }

    @Override
    public ShriekOfDread copy() {
        return new ShriekOfDread(this);
    }
}
