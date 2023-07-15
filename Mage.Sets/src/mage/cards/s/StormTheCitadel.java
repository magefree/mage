package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerOrPlaneswalkerTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.DefendingPlayerControlsPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StormTheCitadel extends CardImpl {

    private static final FilterPermanent filter
            = new FilterPermanent("artifact or enchantment defending player controls");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()
        ));
        filter.add(DefendingPlayerControlsPredicate.instance);
    }

    public StormTheCitadel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{G}");

        // Until end of turn, creatures you control get +2/+2 and gain "Whenever this creature deals combat damage to a creature or planeswalker, destroy target artifact or enchantment defending player controls."
        Ability ability = new DealsCombatDamageToAPlayerOrPlaneswalkerTriggeredAbility(
                new DestroyTargetEffect(), false);
        ability.addTarget(new TargetPermanent(filter));

        this.getSpellAbility().addEffect(new BoostControlledEffect(
                2, 2, Duration.EndOfTurn
        ).setText("Until end of turn, creatures you control get +2/+2"));

        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
                ability, Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURE
        ).setText("and gain \"Whenever this creature deals combat damage to a player or planeswalker, " +
                "destroy target artifact or enchantment defending player controls.\""
        ));
    }

    private StormTheCitadel(final StormTheCitadel card) {
        super(card);
    }

    @Override
    public StormTheCitadel copy() {
        return new StormTheCitadel(this);
    }
}
