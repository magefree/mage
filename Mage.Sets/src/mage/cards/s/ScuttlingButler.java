package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.MulticoloredPredicate;

/**
 *
 * @author weirddan455
 */
public final class ScuttlingButler extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("multicolored permanents");

    static {
        filter.add(MulticoloredPredicate.instance);
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 1);

    public ScuttlingButler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(1);

        // At the beginning of combat on your turn, if you control two or more multicolored permanents, Scuttling Butler gains double strike until end of turn.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfCombatTriggeredAbility(new GainAbilitySourceEffect(DoubleStrikeAbility.getInstance(), Duration.EndOfTurn), TargetController.YOU, false),
                condition,
                "At the beginning of combat on your turn, if you control two or more multicolored permanents, {this} gains double strike until end of turn."
        ));
    }

    private ScuttlingButler(final ScuttlingButler card) {
        super(card);
    }

    @Override
    public ScuttlingButler copy() {
        return new ScuttlingButler(this);
    }
}
