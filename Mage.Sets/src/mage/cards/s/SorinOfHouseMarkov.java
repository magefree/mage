package mage.cards.s;

import mage.MageInt;
import mage.abilities.Pronoun;
import mage.abilities.common.BeginningOfPostCombatMainTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.YouGainedLifeCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.ExileAndReturnSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.ExtortAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class SorinOfHouseMarkov extends CardImpl {

    private static final Condition condition = new YouGainedLifeCondition(ComparisonType.OR_GREATER, 3);
    private static final Hint hint = new ConditionHint(condition, "You gained 3 or more life this turn");

    public SorinOfHouseMarkov(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        this.secondSideCardClazz = SorinRavenousNeonate.class;

        // Flying
        this.addAbility(LifelinkAbility.getInstance());

        // Extort
        this.addAbility(new ExtortAbility());

        // At the beginning of your postcombat main phase, if you gained 3 or more life this turn, exile Sorin of House Markov, then return him to the battlefield transformed under his owner's control.
        this.addAbility(new TransformAbility());
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfPostCombatMainTriggeredAbility(
                        new ExileAndReturnSourceEffect(PutCards.BATTLEFIELD_TRANSFORMED, Pronoun.SHE),
                        TargetController.YOU,
                        false
                ), condition, "At the beginning of your postcombat main phase, "
                + "if you gained 3 or more life this turn, exile {this}, "
                + "then return him to the battlefield transformed under his owner's control."
        ).addHint(hint), new PlayerGainedLifeWatcher());
    }

    private SorinOfHouseMarkov(final SorinOfHouseMarkov card) {
        super(card);
    }

    @Override
    public SorinOfHouseMarkov copy() {
        return new SorinOfHouseMarkov(this);
    }
}
