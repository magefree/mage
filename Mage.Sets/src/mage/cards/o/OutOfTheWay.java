package mage.cards.o;

import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceTargetsPermanentCondition;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OutOfTheWay extends CardImpl {

    private static final FilterPermanent filter
            = new FilterPermanent("a green permanent");
    private static final FilterPermanent filter2
            = new FilterNonlandPermanent("nonland permanent an opponent controls");

    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
        filter2.add(TargetController.OPPONENT.getControllerPredicate());
    }

    private static final Condition condition = new SourceTargetsPermanentCondition(filter);

    public OutOfTheWay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}");

        // This spell costs {2} less to cast if it targets a green permanent.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(2, condition).setCanWorksOnStackOnly(true)
        ).setRuleAtTheTop(true));

        // Return target nonland permanent an opponent controls to its owner's hand.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter2));

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private OutOfTheWay(final OutOfTheWay card) {
        super(card);
    }

    @Override
    public OutOfTheWay copy() {
        return new OutOfTheWay(this);
    }
}
