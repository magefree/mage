package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MistfordRiverTurtle extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("another target attacking non-Human creature");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(AttackingPredicate.instance);
        filter.add(Predicates.not(SubType.HUMAN.getPredicate()));
    }

    public MistfordRiverTurtle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(5);

        // Whenever Mistford River Turtle attacks, another target attacking non-Human creature can't be blocked this turn.
        Ability ability = new AttacksTriggeredAbility(new CantBeBlockedTargetEffect(Duration.EndOfTurn)
                .setText("another target attacking non-Human creature can't be blocked this turn"), false);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private MistfordRiverTurtle(final MistfordRiverTurtle card) {
        super(card);
    }

    @Override
    public MistfordRiverTurtle copy() {
        return new MistfordRiverTurtle(this);
    }
}
