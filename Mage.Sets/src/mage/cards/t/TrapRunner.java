package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.AfterBlockersAreDeclaredCondition;
import mage.abilities.condition.common.IsPhaseCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.BecomeBlockedTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TurnPhase;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.filter.predicate.permanent.BlockedPredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class TrapRunner extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("unblocked attacking creature");

    static {
        filter.add(AttackingPredicate.instance);
        filter.add(Predicates.not(BlockedPredicate.instance));
    }

    private static final Condition condition = new CompoundCondition(
            "during combat after blockers are declared",
            new IsPhaseCondition(TurnPhase.COMBAT),
            AfterBlockersAreDeclaredCondition.instance
    );

    public TrapRunner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {T}: Target unblocked attacking creature becomes blocked. Activate this ability only during combat after blockers are declared.
        Ability ability = new ConditionalActivatedAbility(
                Zone.BATTLEFIELD, new BecomeBlockedTargetEffect(), new TapSourceCost(), condition
        );
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private TrapRunner(final TrapRunner card) {
        super(card);
    }

    @Override
    public TrapRunner copy() {
        return new TrapRunner(this);
    }

}
