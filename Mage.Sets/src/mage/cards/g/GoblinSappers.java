package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DestroySourceEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author xenohedron
 */

public final class GoblinSappers extends CardImpl {

    public GoblinSappers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {R}{R}, {T}: Target creature you control can’t be blocked this turn. Destroy it and Goblin Sappers at end of combat.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CantBeBlockedTargetEffect(), new ManaCostsImpl<>("{R}{R}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetControlledCreaturePermanent());
        DelayedTriggeredAbility triggeredAbility = new AtTheEndOfCombatDelayedTriggeredAbility(new DestroyTargetEffect().setText("destroy that creature"));
        triggeredAbility.addEffect(new DestroySourceEffect().setText("and {this}"));
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(triggeredAbility).setText("Destroy it and {this} at end of combat."));
        this.addAbility(ability);

        // {R}{R}{R}{R}, {T}: Target creature you control can’t be blocked this turn. Destroy it at end of combat.
        Ability secondAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CantBeBlockedTargetEffect(), new ManaCostsImpl<>("{R}{R}{R}{R}"));
        secondAbility.addCost(new TapSourceCost());
        secondAbility.addTarget(new TargetControlledCreaturePermanent());
        DelayedTriggeredAbility secondTriggeredAbility = new AtTheEndOfCombatDelayedTriggeredAbility(new DestroyTargetEffect().setText("destroy that creature"));
        secondAbility.addEffect(new CreateDelayedTriggeredAbilityEffect(secondTriggeredAbility).setText("Destroy it at end of combat."));
        this.addAbility(secondAbility);

    }

    private GoblinSappers(final GoblinSappers card) {
        super(card);
    }

    @Override
    public GoblinSappers copy() {
        return new GoblinSappers(this);
    }

}
