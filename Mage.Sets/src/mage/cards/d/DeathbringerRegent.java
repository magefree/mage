package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CastFromHandSourcePermanentCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.watchers.common.CastFromHandWatcher;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class DeathbringerRegent extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("other creatures");

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final Condition condition = new CompoundCondition(
            "you cast it from your hand and there are five or more other creatures on the battlefield",
            CastFromHandSourcePermanentCondition.instance,
            new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 4)
    );

    public DeathbringerRegent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{B}");
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Deathbringer Regent enters the battlefield, if you cast it from your hand and there are five or more other creatures on the battlefield, destroy all other creatures.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DestroyAllEffect(filter))
                .withInterveningIf(condition), new CastFromHandWatcher());
    }

    private DeathbringerRegent(final DeathbringerRegent card) {
        super(card);
    }

    @Override
    public DeathbringerRegent copy() {
        return new DeathbringerRegent(this);
    }
}
