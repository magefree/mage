
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CastFromHandSourcePermanentCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.watchers.common.CastFromHandWatcher;

/**
 *
 * @author jeffwadsworth
 */
public final class DeathbringerRegent extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("other creatures");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public DeathbringerRegent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{B}");
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Deathbringer Regent enters the battlefield, if you cast it from your hand and there are five or more other creatures on the battlefield, destroy all other creatures.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new DestroyAllEffect(filter), false),
                new DeathbringerRegentCondition(),
                "When {this} enters the battlefield, if you cast it from your hand and there are five or more other creatures on the battlefield, destroy all other creatures."),
                new CastFromHandWatcher());
    }

    private DeathbringerRegent(final DeathbringerRegent card) {
        super(card);
    }

    @Override
    public DeathbringerRegent copy() {
        return new DeathbringerRegent(this);
    }
}

class DeathbringerRegentCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        return CastFromHandSourcePermanentCondition.instance.apply(game, source)
                && game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, game).size() >= 6;
    }
}
