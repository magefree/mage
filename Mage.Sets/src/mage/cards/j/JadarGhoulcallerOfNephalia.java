package mage.cards.j;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.DecayedAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.permanent.token.ZombieDecayedToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JadarGhoulcallerOfNephalia extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("if you control no creatures with decayed");

    static {
        filter.add(new AbilityPredicate(DecayedAbility.class));
    }

    private static final Condition condition
            = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.EQUAL_TO, 0);
    private static final Hint hint = new ValueHint(
            "Creatures you control with decayed", new PermanentsOnBattlefieldCount(filter)
    );

    public JadarGhoulcallerOfNephalia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // At the beginning of your end step, if you control no creatures with decayed, create a 2/2 black Zombie creature token with decayed.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                Zone.BATTLEFIELD, new CreateTokenEffect(new ZombieDecayedToken()),
                TargetController.YOU, condition, false
        ).addHint(hint));
    }

    private JadarGhoulcallerOfNephalia(final JadarGhoulcallerOfNephalia card) {
        super(card);
    }

    @Override
    public JadarGhoulcallerOfNephalia copy() {
        return new JadarGhoulcallerOfNephalia(this);
    }
}
