
package mage.cards.r;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetAttackingCreature;

import java.util.UUID;

/**
 *
 * @author rscoates
 */
public final class RunAmok extends CardImpl {

    public RunAmok(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R}");

        // Target attacking creature gets +3/+3 and gains trample until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(3, 3, Duration.EndOfTurn).setText("Target attacking creature gets +3/+3"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn).setText("and gains trample until end of turn"));
        this.getSpellAbility().addTarget(new TargetAttackingCreature());
    }

    private RunAmok(final RunAmok card) {
        super(card);
    }

    @Override
    public RunAmok copy() {
        return new RunAmok(this);
    }
}
