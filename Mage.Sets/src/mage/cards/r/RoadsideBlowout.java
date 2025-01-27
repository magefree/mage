package mage.cards.r;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceTargetsPermanentCondition;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RoadsideBlowout extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("a permanent with mana value 1");
    private static final FilterPermanent filter2 = new FilterPermanent("creature or Vehicle an opponent controls");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, 1));
        filter2.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                SubType.VEHICLE.getPredicate()
        ));
        filter2.add(TargetController.OPPONENT.getControllerPredicate());
    }

    private static final Condition condition = new SourceTargetsPermanentCondition(filter);

    public RoadsideBlowout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");

        // This spell costs {2} less to cast if it targets a permanent with mana value 1.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(2, condition).setCanWorksOnStackOnly(true)
        ).setRuleAtTheTop(true));

        // Return target creature or Vehicle an opponent controls to its owner's hand.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter2));

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private RoadsideBlowout(final RoadsideBlowout card) {
        super(card);
    }

    @Override
    public RoadsideBlowout copy() {
        return new RoadsideBlowout(this);
    }
}
