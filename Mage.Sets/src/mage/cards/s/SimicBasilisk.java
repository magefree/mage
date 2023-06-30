
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToACreatureTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.GraftAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author JotaPeRL
 */
public final class SimicBasilisk extends CardImpl {

    public SimicBasilisk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{G}");
        this.subtype.add(SubType.BASILISK);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Graft 3
        this.addAbility(new GraftAbility(this, 3));
        
        // {1}{G}: Until end of turn, target creature with a +1/+1 counter on it gains "Whenever this creature deals combat damage to a creature, destroy that creature at end of combat."
        Effect effect = new CreateDelayedTriggeredAbilityEffect(
                new AtTheEndOfCombatDelayedTriggeredAbility(new DestroyTargetEffect()), true);
        effect.setText("destroy that creature at end of combat");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilityTargetEffect(
                new DealsDamageToACreatureTriggeredAbility(effect, true, false, true), Duration.EndOfTurn)
                .setText("Until end of turn, target creature with a +1/+1 counter on it gains \"Whenever this creature deals combat damage to a creature, destroy that creature at end of combat.\""),
                new ManaCostsImpl<>("{1}{G}"));
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_A_CREATURE_P1P1));
        this.addAbility(ability);
    }

    private SimicBasilisk(final SimicBasilisk card) {
        super(card);
    }

    @Override
    public SimicBasilisk copy() {
        return new SimicBasilisk(this);
    }
}
