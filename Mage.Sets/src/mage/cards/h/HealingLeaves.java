
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.TargetPlayer;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LoneFox
 */
public final class HealingLeaves extends CardImpl {

    public HealingLeaves(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{G}");

        // Choose one - Target player gains 3 life; or prevent the next 3 damage that would be dealt to any target this turn.
        this.getSpellAbility().addEffect(new GainLifeTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetPlayer());
        Mode mode = new Mode(new PreventDamageToTargetEffect(Duration.EndOfTurn, 3));
        mode.addTarget(new TargetAnyTarget());
        this.getSpellAbility().addMode(mode);
    }

    private HealingLeaves(final HealingLeaves card) {
        super(card);
    }

    @Override
    public HealingLeaves copy() {
        return new HealingLeaves(this);
    }
}
