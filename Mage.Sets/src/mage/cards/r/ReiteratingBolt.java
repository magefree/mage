package mage.cards.r;

import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.ReplicateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ReiteratingBolt extends CardImpl {

    public ReiteratingBolt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // Replicate--Pay {E}{E}{E}.
        this.addAbility(new ReplicateAbility(new PayEnergyCost(3)));

        // Reiterating Bolt deals 3 damage to target creature or planeswalker.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
    }

    private ReiteratingBolt(final ReiteratingBolt card) {
        super(card);
    }

    @Override
    public ReiteratingBolt copy() {
        return new ReiteratingBolt(this);
    }
}
