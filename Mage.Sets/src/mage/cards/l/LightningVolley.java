
package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LevelX2
 */
public final class LightningVolley extends CardImpl {

    public LightningVolley(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{R}");


        // Until end of turn, creatures you control gain "{T}: This creature deals 1 damage to any target."        
        Ability grantedAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new TapSourceCost());
        grantedAbility.addTarget(new TargetAnyTarget());
        Effect effect = new GainAbilityControlledEffect(grantedAbility, Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES);
        effect.setText("Until end of turn, creatures you control gain \"{T}: This creature deals 1 damage to any target.\"");
        this.getSpellAbility().addEffect(effect);
    }

    private LightningVolley(final LightningVolley card) {
        super(card);
    }

    @Override
    public LightningVolley copy() {
        return new LightningVolley(this);
    }
}
