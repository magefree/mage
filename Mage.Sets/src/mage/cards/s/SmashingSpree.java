package mage.cards.s;

import java.util.UUID;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetAttackingCreature;

/**
 *
 * @author muz
 */
public final class SmashingSpree extends CardImpl {

    public SmashingSpree(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Target attacking creature gets +3/+3 and gains trample until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(3, 3, Duration.EndOfTurn).setText("Target attacking creature gets +3/+3"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn).setText("and gains trample until end of turn"));
        this.getSpellAbility().addTarget(new TargetAttackingCreature());
    }

    private SmashingSpree(final SmashingSpree card) {
        super(card);
    }

    @Override
    public SmashingSpree copy() {
        return new SmashingSpree(this);
    }
}
