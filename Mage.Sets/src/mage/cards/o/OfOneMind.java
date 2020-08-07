package mage.cards.o;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OfOneMind extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent(SubType.HUMAN);
    private static final FilterPermanent filter2 = new FilterControlledCreaturePermanent();

    static {
        filter2.add(Predicates.not(SubType.HUMAN.getPredicate()));
    }

    private static final Condition condition = new CompoundCondition(
            "you control a Human creature and a non-Human creature",
            new PermanentsOnTheBattlefieldCondition(filter),
            new PermanentsOnTheBattlefieldCondition(filter2)
    );

    public OfOneMind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");

        // This spell costs {2} less to cast if you control a Human creature and a non-Human creature.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SpellCostReductionSourceEffect(2, condition))
                .setRuleAtTheTop(true)
                .addHint(new ConditionHint(condition, "You control a Human creature and a non-Human creature"))
        );

        // Draw two cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
    }

    private OfOneMind(final OfOneMind card) {
        super(card);
    }

    @Override
    public OfOneMind copy() {
        return new OfOneMind(this);
    }
}
