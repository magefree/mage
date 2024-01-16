package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldWithCounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ChosenSubtypePredicate;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 *
 * @author jimga150
 */
public final class FromTheRubble extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard(
            "creature card of the chosen type from your graveyard"
    );

    static {
        filter.add(ChosenSubtypePredicate.TRUE);
    }

    public FromTheRubble(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{W}{W}");
        

        // As From the Rubble enters the battlefield, choose a creature type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.PutCreatureInPlay)));

        // At the beginning of your end step, return target creature card of the chosen type from your graveyard to
        // the battlefield with a finality counter on it.
        Ability ability = new BeginningOfYourEndStepTriggeredAbility(
                new ReturnFromGraveyardToBattlefieldWithCounterTargetEffect(CounterType.FINALITY.createInstance()),
                false
        );
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private FromTheRubble(final FromTheRubble card) {
        super(card);
    }

    @Override
    public FromTheRubble copy() {
        return new FromTheRubble(this);
    }
}
