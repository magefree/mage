package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.common.combat.CantAttackSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WardenOfTheChained extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 3));
    }

    private static final Condition condition
            = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.EQUAL_TO, 0);

    public WardenOfTheChained(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{G}");

        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Warden of the Chained can't attack unless you control another creature with power 4 or greater.
        this.addAbility(new SimpleStaticAbility(new ConditionalRestrictionEffect(
                new CantAttackSourceEffect(Duration.WhileOnBattlefield), condition,
                "{this} can't attack unless you control another creature with power 4 or greater"
        )));
    }

    private WardenOfTheChained(final WardenOfTheChained card) {
        super(card);
    }

    @Override
    public WardenOfTheChained copy() {
        return new WardenOfTheChained(this);
    }
}
