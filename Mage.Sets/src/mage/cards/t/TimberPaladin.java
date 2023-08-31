package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.EnchantedSourceCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;

/**
 *
 * @author Xanderhall
 */
public final class TimberPaladin extends CardImpl {

    private static Condition exactlyOne = new EnchantedSourceCondition(1, ComparisonType.EQUAL_TO, true);
    private static Condition exactlyTwo = new EnchantedSourceCondition(2, ComparisonType.EQUAL_TO, true);
    private static Condition threeOrMore = new EnchantedSourceCondition(3, ComparisonType.OR_GREATER, true);

    public TimberPaladin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{G}");
        
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // As long as Timber Paladin is enchanted by exactly one Aura, it has base power and toughness 3/3.
        Ability ability1 = new SimpleStaticAbility(new ConditionalContinuousEffect(
            new SetBasePowerToughnessSourceEffect(3, 3, Duration.WhileOnBattlefield),
            exactlyOne,
            "As long as {this} is enchanted by exactly one Aura, it has base power and toughness 3/3."
        ));

        this.addAbility(ability1);
        // As long as Timber Paladin is enchanted by exactly two Auras, it has base power and toughness 5/5 and vigilance.

        Ability ability2 = new SimpleStaticAbility(new ConditionalContinuousEffect(
            new SetBasePowerToughnessSourceEffect(5, 5, Duration.WhileOnBattlefield),
            exactlyTwo,
            "As long as {this} is enchanted by exactly two Auras, it has base power and toughness 5/5"
        ));
        ability2.addEffect(new ConditionalContinuousEffect(new GainAbilitySourceEffect(VigilanceAbility.getInstance()), exactlyTwo, "and vigilance."));

        this.addAbility(ability2);
        
        // As long as Timber Paladin is enchanted by three or more Auras, it has base power and toughness 10/10, vigilance, and trample.
        Ability ability3 = new SimpleStaticAbility(new ConditionalContinuousEffect(
            new SetBasePowerToughnessSourceEffect(10, 10, Duration.WhileOnBattlefield),
            threeOrMore,
            "As long as {this} is enchanted by three or more Auras, it has base power and toughness 10/10"
        ));
        ability3.addEffect(new ConditionalContinuousEffect(new GainAbilitySourceEffect(VigilanceAbility.getInstance()), threeOrMore, ", vigilance"));
        ability3.addEffect(new ConditionalContinuousEffect(new GainAbilitySourceEffect(TrampleAbility.getInstance()), threeOrMore, ", and trample."));
        this.addAbility(ability3);
    }

    private TimberPaladin(final TimberPaladin card) {
        super(card);
    }

    @Override
    public TimberPaladin copy() {
        return new TimberPaladin(this);
    }
}
