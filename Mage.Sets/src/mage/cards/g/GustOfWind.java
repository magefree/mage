package mage.cards.g;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GustOfWind extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent("you control a creature with flying");
    private static final FilterPermanent filter2
            = new FilterNonlandPermanent("nonland permanent you don't control");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
        filter2.add(TargetController.OPPONENT.getControllerPredicate());
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);

    public GustOfWind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}");

        // This spell costs {2} less to cast if you control a creature with flying.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(2, condition)
        ).setRuleAtTheTop(true).addHint(new ConditionHint(condition, "You control a creature with flying")));

        // Return target nonland permanent you don't control to its owner's hand.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter2));

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private GustOfWind(final GustOfWind card) {
        super(card);
    }

    @Override
    public GustOfWind copy() {
        return new GustOfWind(this);
    }
}
