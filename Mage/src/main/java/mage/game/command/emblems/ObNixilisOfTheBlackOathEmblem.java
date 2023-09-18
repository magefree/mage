package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SacrificeCostCreaturesPower;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.constants.Zone;
import mage.game.command.Emblem;
import mage.target.common.TargetControlledCreaturePermanent;

import mage.filter.StaticFilters;

/**
 * @author spjspj
 */
public final class ObNixilisOfTheBlackOathEmblem extends Emblem {

    // You get an emblem with "{1}{B}, Sacrifice a creature: You gain X life and draw X cards, where X is the sacrificed creature's power."
    public ObNixilisOfTheBlackOathEmblem() {
        super("Emblem Nixilis");
        DynamicValue xValue = SacrificeCostCreaturesPower.instance;
        Effect effect = new GainLifeEffect(xValue);
        effect.setText("You gain X life");
        Ability ability = new SimpleActivatedAbility(Zone.COMMAND, effect, new ManaCostsImpl<>("{1}{B}"));
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT));
        effect = new DrawCardSourceControllerEffect(xValue);
        effect.setText("and draw X cards, where X is the sacrificed creature's power");
        ability.addEffect(effect);
        this.getAbilities().add(ability);
    }

    private ObNixilisOfTheBlackOathEmblem(final ObNixilisOfTheBlackOathEmblem card) {
        super(card);
    }

    @Override
    public ObNixilisOfTheBlackOathEmblem copy() {
        return new ObNixilisOfTheBlackOathEmblem(this);
    }
}
