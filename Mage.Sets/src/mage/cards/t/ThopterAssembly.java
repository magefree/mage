package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.permanent.token.ThopterColorlessToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThopterAssembly extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.THOPTER, "");

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.EQUAL_TO, 0);

    public ThopterAssembly(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}");
        this.subtype.add(SubType.THOPTER);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of your upkeep, if you control no Thopters other than Thopter Assembly, return Thopter Assembly to its owner's hand and create five 1/1 colorless Thopter artifact creature tokens with flying.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(
                        new ReturnToHandSourceEffect(true),
                        TargetController.YOU, false
                ), condition, "At the beginning of your upkeep, " +
                "if you control no Thopters other than {this}, " +
                "return {this} to its owner's hand and create five 1/1 colorless " +
                "Thopter artifact creature tokens with flying."
        );
        ability.addEffect(new CreateTokenEffect(new ThopterColorlessToken(), 5));
        this.addAbility(ability);
    }

    private ThopterAssembly(final ThopterAssembly card) {
        super(card);
    }

    @Override
    public ThopterAssembly copy() {
        return new ThopterAssembly(this);
    }
}
