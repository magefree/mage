
package mage.cards.j;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.OrCondition;
import mage.abilities.condition.common.ChoseToNotBargainCondition;
import mage.abilities.condition.common.PastBargainingCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.cost.SpellCostIncreaseSourceEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.keyword.BargainAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class JohannsStopgap extends CardImpl {

    private static final FilterControlledPermanent filterBargain = new FilterControlledPermanent();

    static {
        filterBargain.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.ENCHANTMENT.getPredicate(),
                TokenPredicate.TRUE
        ));
    }

    public JohannsStopgap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}");

        // Bargain (You may sacrifice an artifact, enchantment, or token as you cast this spell.)
        this.addAbility(new BargainAbility());

        // This spell costs {2} less to cast if it's bargained.
        //
        // This is a workaround in order to allow casting when having one of {3}{U} or {1}{U}
        // and an available bargainable permanent. We take the approach to by default reduce the cost by {2},
        // and increase it by {2} if both the spell is not bargained and it is not possible to Bargain.
        Ability ability = new SimpleStaticAbility(Zone.ALL, new SpellCostReductionSourceEffect(2)
                .setText("This spell costs {2} less to cast if it's bargained"));
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);
        ability = new SimpleStaticAbility(Zone.ALL, new SpellCostIncreaseSourceEffect(2,
                new OrCondition(
                        new CompoundCondition(
                                // We are before bargaining, and bargaining is not possible.
                                new InvertCondition(PastBargainingCondition.instance),
                                new PermanentsOnTheBattlefieldCondition(filterBargain, ComparisonType.EQUAL_TO, 0, true)
                        ),
                        // Choice was made to not bargain.
                        ChoseToNotBargainCondition.instance
                )
        ));
        this.addAbility(ability);

        // Return target nonland permanent to its owner's hand. Draw a card.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
    }

    private JohannsStopgap(final JohannsStopgap card) {
        super(card);
    }

    @Override
    public JohannsStopgap copy() {
        return new JohannsStopgap(this);
    }
}

