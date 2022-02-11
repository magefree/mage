package mage.cards.s;

import java.util.UUID;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */


public final class Showstopper extends CardImpl {

    public Showstopper (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{B}{R}");

        // Until end of turn, creatures you control gain "When this creature dies, it deals 2 damage to target creature an opponent controls."
        TriggeredAbility ability = new DiesSourceTriggeredAbility(new DamageTargetEffect(2, "it"), false);
        Target target = new TargetCreaturePermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE);
        ability.addTarget(target);
        Effect effect = new GainAbilityControlledEffect(ability, Duration.EndOfTurn, StaticFilters.FILTER_CONTROLLED_CREATURES);
        effect.setText("Until end of turn, creatures you control gain \"When this creature dies, it deals 2 damage to target creature an opponent controls.\"");
        this.getSpellAbility().addEffect(effect);
    }

    public Showstopper (final Showstopper card) {
        super(card);
    }

    @Override
    public Showstopper copy() {
        return new Showstopper(this);
    }

}
