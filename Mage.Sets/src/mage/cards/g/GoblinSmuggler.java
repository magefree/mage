package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GoblinSmuggler extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("another creature with power 2 or less");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
    }

    public GoblinSmuggler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // {T}: Another target creature with power 2 or less can't be blocked this turn.
        Ability ability = new SimpleActivatedAbility(
                new CantBeBlockedTargetEffect(Duration.EndOfTurn)
                        .setText("Another target creature with power 2 or less can't be blocked this turn."),
                new TapSourceCost()
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private GoblinSmuggler(final GoblinSmuggler card) {
        super(card);
    }

    @Override
    public GoblinSmuggler copy() {
        return new GoblinSmuggler(this);
    }
}
