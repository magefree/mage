package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.command.Emblem;

public final class DavrielSoulBrokerCostReductionEmblem extends Emblem {

    // You get an emblem with "Spells you cast cost {B} less to cast."
    public DavrielSoulBrokerCostReductionEmblem() {
        super("Emblem Davriel");

        Ability ability = new SimpleStaticAbility(
                Zone.COMMAND,
                new SpellsCostReductionControllerEffect(
                        new FilterCard(), new ManaCostsImpl<>("{B}")).setText("spells you cast cost {B} less to cast.")
        );
        this.getAbilities().add(ability);
    }

    private DavrielSoulBrokerCostReductionEmblem(final DavrielSoulBrokerCostReductionEmblem card) {
        super(card);
    }

    @Override
    public DavrielSoulBrokerCostReductionEmblem copy() {
        return new DavrielSoulBrokerCostReductionEmblem(this);
    }
}
