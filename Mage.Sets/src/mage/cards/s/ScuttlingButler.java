package mage.cards.s;

import mage.MageInt;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.MulticoloredPredicate;

import java.util.UUID;

/**
 * @author weirddan455
 */
public final class ScuttlingButler extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("you control two or more multicolored permanents");

    static {
        filter.add(MulticoloredPredicate.instance);
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 1);
    private static final Hint hint = new ValueHint("Multicolored permanents you control", new PermanentsOnBattlefieldCount(filter));

    public ScuttlingButler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(1);

        // At the beginning of combat on your turn, if you control two or more multicolored permanents, Scuttling Butler gains double strike until end of turn.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new GainAbilitySourceEffect(
                DoubleStrikeAbility.getInstance(), Duration.EndOfTurn
        )).withInterveningIf(condition));
    }

    private ScuttlingButler(final ScuttlingButler card) {
        super(card);
    }

    @Override
    public ScuttlingButler copy() {
        return new ScuttlingButler(this);
    }
}
