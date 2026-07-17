package mage.cards.f;

import java.util.UUID;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceTargetsPermanentCondition;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author muz
 */
public final class FantasticBounce extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("a tapped creature");

    static {
        filter.add(TappedPredicate.TAPPED);
    }

    private static final Condition condition = new SourceTargetsPermanentCondition(filter);

    public FantasticBounce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}");


        // This spell costs {2} less to cast if it targets a tapped creature.
        this.addAbility(new SimpleStaticAbility(
            Zone.ALL, new SpellCostReductionSourceEffect(2, condition).setCanWorksOnStackOnly(true)
        ).setRuleAtTheTop(true));

        // Return target nonland permanent to its owner's hand.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private FantasticBounce(final FantasticBounce card) {
        super(card);
    }

    @Override
    public FantasticBounce copy() {
        return new FantasticBounce(this);
    }
}
