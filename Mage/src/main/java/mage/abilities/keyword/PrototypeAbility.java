package mage.abilities.keyword;

import mage.ObjectColor;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PrototypedCondition;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.effects.common.continuous.SetCardColorSourceEffect;
import mage.abilities.effects.common.cost.SetCardCostSourceEffect;
import mage.cards.Card;
import mage.constants.*;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801, Susucr
 */
public class PrototypeAbility extends SpellAbility {

    private int power;
    private int toughness;

    public PrototypeAbility(Card card, String manaString, ObjectColor color, int power, int toughness) {
        super(new ManaCostsImpl<>(manaString), card.getName(), Zone.HAND, SpellAbilityType.PROTOTYPE);

        SetBasePowerToughnessSourceEffect basePTEffect =
            new SetBasePowerToughnessSourceEffect(power, toughness, Duration.EndOfGame, false);

        basePTEffect.setText("");

        card.addAbility(new SimpleStaticAbility(
            Zone.ALL,
            new ConditionalContinuousEffect(
                basePTEffect,
                PrototypedCondition.instance,
                "")
        ));

        card.addAbility(new SimpleStaticAbility(
            Zone.ALL,
            new ConditionalContinuousEffect(
                new SetCardColorSourceEffect(color, Duration.EndOfGame),
                PrototypedCondition.instance,
                "")
        ));

        card.addAbility(new SimpleStaticAbility(
            Zone.ALL,
            new ConditionalContinuousEffect(
                new SetCardCostSourceEffect(this.manaCosts, Duration.EndOfGame),
                PrototypedCondition.instance,
                "")
        ));

        this.power = power;
        this.toughness = toughness;

        setRuleAtTheTop(true);
    }

    private PrototypeAbility(final PrototypeAbility ability) {
        super(ability);
    }

    @Override
    public PrototypeAbility copy() {
        return new PrototypeAbility(this);
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder("Prototype ");
        sb.append(this.manaCosts.getText());
        sb.append("&mdash; ").append(this.power).append("/").append(this.toughness);
        sb.append(" <i>(You may cast this spell with different mana cost, color, and size. It keeps its abilities and types.)");
        return sb.toString();
    }
}
