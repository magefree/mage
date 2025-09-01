package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.OneOrMoreCombatDamagePlayerTriggeredAbility;
import mage.abilities.condition.common.FirstCombatPhaseCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.AdditionalCombatPhaseEffect;
import mage.abilities.effects.common.UntapAllEffect;
import mage.abilities.keyword.MeleeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TifaMartialArtist extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creatures you control with power 7 or greater");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 6));
    }

    public TifaMartialArtist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Melee
        this.addAbility(new MeleeAbility());

        // Whenever one or more creatures you control with power 7 or greater deal combat damage to a player, untap all creatures you control. If it's the first combat phase of your turn, there is an additional combat phase after this phase.
        Ability ability = new OneOrMoreCombatDamagePlayerTriggeredAbility(
                new UntapAllEffect(StaticFilters.FILTER_CONTROLLED_CREATURES), filter
        );
        ability.addEffect(new ConditionalOneShotEffect(
                new AdditionalCombatPhaseEffect(), FirstCombatPhaseCondition.instance,
                "If it's the first combat phase of your turn, there is an additional combat phase after this phase"
        ));
        this.addAbility(ability);
    }

    private TifaMartialArtist(final TifaMartialArtist card) {
        super(card);
    }

    @Override
    public TifaMartialArtist copy() {
        return new TifaMartialArtist(this);
    }
}
