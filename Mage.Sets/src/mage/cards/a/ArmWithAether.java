
package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.DamagedPlayerControlsTargetAdjuster;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class ArmWithAether extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creature that player controls");

    public ArmWithAether(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");

        // Until end of turn, creatures you control gain "Whenever this creature deals damage to an opponent, you may return target creature that player controls to its owner's hand."
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new ReturnToHandTargetEffect(), false, true);
        ability.addTarget(new TargetPermanent(filter));
        ability.setTargetAdjuster(new DamagedPlayerControlsTargetAdjuster());

        Effect effect = new GainAbilityControlledEffect(ability, Duration.EndOfTurn, new FilterCreaturePermanent());
        effect.setText("Until end of turn, creatures you control gain \"Whenever this creature deals damage to an opponent, you may return target creature that player controls to its owner's hand.\"");
        this.getSpellAbility().addEffect(effect);
    }

    private ArmWithAether(final ArmWithAether card) {
        super(card);
    }

    @Override
    public ArmWithAether copy() {
        return new ArmWithAether(this);
    }
}
