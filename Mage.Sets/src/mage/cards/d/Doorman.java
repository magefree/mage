package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.combat.CantBeBlockedByAllTargetEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.common.TargetCreaturePermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class Doorman extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures with power 2 or less and/or Walls");

    static {
        filter.add(Predicates.or(
            new PowerPredicate(ComparisonType.FEWER_THAN, 3),
            SubType.WALL.getPredicate()
        ));
    }

    public Doorman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {T}: Until end of turn, target creature can't be blocked by creatures with power 2 or less and/or Walls.
        Ability ability = new SimpleActivatedAbility(
            new CantBeBlockedByAllTargetEffect(filter, Duration.EndOfTurn)
                .setText("Until end of turn, target creature can't be blocked by creatures with power 2 or less and/or Walls this turn"),
            new TapSourceCost()
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private Doorman(final Doorman card) {
        super(card);
    }

    @Override
    public Doorman copy() {
        return new Doorman(this);
    }
}
