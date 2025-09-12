package mage.cards.s;

import mage.MageInt;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.YouGainedLifeCondition;
import mage.abilities.effects.common.ExileAndReturnSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.ExtortAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.TransformAbility;
import mage.abilities.triggers.BeginningOfPostcombatMainTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class SorinOfHouseMarkov extends CardImpl {

    private static final Condition condition = new YouGainedLifeCondition(ComparisonType.MORE_THAN, 2);
    private static final Hint hint = new ConditionHint(condition);

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
        this.addAbility(new BeginningOfPostcombatMainTriggeredAbility(
                new ExileAndReturnSourceEffect(PutCards.BATTLEFIELD_TRANSFORMED, Pronoun.HE), false
        ).withInterveningIf(condition).addHint(hint), new PlayerGainedLifeWatcher());
    }

    private SorinOfHouseMarkov(final SorinOfHouseMarkov card) {
        super(card);
    }

    @Override
    public SorinOfHouseMarkov copy() {
        return new SorinOfHouseMarkov(this);
    }
}
