package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.cost.SpellsCostIncreasingAllEffect;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.command.Emblem;

public final class DavrielSoulBrokerCostIncreaseEmblem extends Emblem {

    // You get an emblem with "Spells you cast cost {B} more to cast."
    public DavrielSoulBrokerCostIncreaseEmblem() {
        super("Emblem Davriel");

        Ability ability = new SimpleStaticAbility(
                Zone.COMMAND,
                new SpellsCostIncreasingAllEffect(
                        new ManaCostsImpl<>("{B}"),
                        new FilterCard(),
                        TargetController.YOU).setText("spells you cast cost {B} more to cast.")
        );
        this.getAbilities().add(ability);
    }

    private DavrielSoulBrokerCostIncreaseEmblem(final DavrielSoulBrokerCostIncreaseEmblem card) {
        super(card);
    }

    @Override
    public DavrielSoulBrokerCostIncreaseEmblem copy() {
        return new DavrielSoulBrokerCostIncreaseEmblem(this);
    }
}
