package mage.cards.i;

import mage.abilities.condition.common.GiftWasPromisedCondition;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.GiftAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.GiftType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;
import mage.target.targetadjustment.ConditionalTargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IntoTheFloodMaw extends CardImpl {
    private static final FilterPermanent playableFilter = new FilterPermanent("creature or nonland permanent");

    static {
        playableFilter.add(TargetController.OPPONENT.getControllerPredicate());
        playableFilter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                Predicates.not(CardType.LAND.getPredicate())
        ));
    }

    public IntoTheFloodMaw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Gift a tapped Fish
        this.addAbility(new GiftAbility(this, GiftType.TAPPED_FISH));

        // Return target creature an opponent controls to its owner's hand. If the gift was promise, instead return target nonland permanent an opponent controls to its owner's hand.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect()
                .setText("return target creature an opponent controls to its owner's hand. If the gift was promised, " +
                        "instead return target nonland permanent an opponent controls to its owner's hand"));
        this.getSpellAbility().addTarget(new TargetPermanent(playableFilter));
        this.getSpellAbility().setTargetAdjuster(new ConditionalTargetAdjuster(GiftWasPromisedCondition.TRUE,
                new TargetOpponentsCreaturePermanent(), new TargetPermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_NON_LAND)));
    }

    private IntoTheFloodMaw(final IntoTheFloodMaw card) {
        super(card);
    }

    @Override
    public IntoTheFloodMaw copy() {
        return new IntoTheFloodMaw(this);
    }
}
