package mage.cards.f;

import mage.ObjectColor;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.continuous.LifeTotalCantChangeControllerEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.TokenPredicate;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class FlareOfFortitude extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("nontoken white creature");

    static {
        filter.add(TokenPredicate.FALSE);
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public FlareOfFortitude(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}{W}");

        // You may sacrifice a nontoken white creature rather than pay this spell's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new SacrificeTargetCost(filter)).setRuleAtTheTop(true));

        // Until end of turn, your life total can't change, and permanents you control gain hexproof and indestructible.
        this.getSpellAbility().addEffect(
                new LifeTotalCantChangeControllerEffect(Duration.UntilYourNextTurn)
                        .setText("Until your next turn, your life total can't change")
        );
        this.getSpellAbility().addEffect(
                new GainAbilityAllEffect(
                        HexproofAbility.getInstance(), Duration.EndOfTurn,
                        StaticFilters.FILTER_CONTROLLED_PERMANENT, false
                ).setText(", and permanents you control gain hexproof")
        );
        this.getSpellAbility().addEffect(
                new GainAbilityAllEffect(
                        IndestructibleAbility.getInstance(), Duration.EndOfTurn,
                        StaticFilters.FILTER_CONTROLLED_PERMANENT, false
                ).setText("and indestructible")
        );
    }

    private FlareOfFortitude(final FlareOfFortitude card) {
        super(card);
    }

    @Override
    public FlareOfFortitude copy() {
        return new FlareOfFortitude(this);
    }
}
