package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.FirstCombatPhaseCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.AdditionalCombatPhaseEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.watchers.common.CrewedVehicleWatcher;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BalthierAndFran extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.VEHICLE, "Vehicles");
    private static final FilterCreaturePermanent filter2
            = new FilterCreaturePermanent(SubType.VEHICLE, "a Vehicle crewed by {this} this turn");

    static {
        filter2.add(BalthierAndFranPredicate.instance);
    }

    public BalthierAndFran(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.RABBIT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Vehicles you control get +1/+1 and have vigilance and reach.
        Ability ability = new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filter
        ));
        ability.addEffect(new GainAbilityControlledEffect(
                VigilanceAbility.getInstance(), Duration.WhileOnBattlefield, filter
        ).setText("and have vigilance"));
        ability.addEffect(new GainAbilityControlledEffect(
                ReachAbility.getInstance(), Duration.WhileOnBattlefield, filter
        ).setText("and reach"));
        this.addAbility(ability);

        // Whenever a Vehicle crewed by Balthier and Fran this turn attacks, if it's the first combat phase of the turn, you may pay {1}{R}{G}. If you do, after this phase, there is an additional combat phase.
        this.addAbility(new AttacksAllTriggeredAbility(
                new DoIfCostPaid(new AdditionalCombatPhaseEffect(), new ManaCostsImpl<>("{1}{R}{G}")),
                false, filter2, SetTargetPointer.NONE, false
        ).withInterveningIf(FirstCombatPhaseCondition.instance), new CrewedVehicleWatcher());
    }

    private BalthierAndFran(final BalthierAndFran card) {
        super(card);
    }

    @Override
    public BalthierAndFran copy() {
        return new BalthierAndFran(this);
    }
}

enum BalthierAndFranPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        return Optional
                .ofNullable(input.getSource().getSourcePermanentIfItStillExists(game))
                .map(permanent -> CrewedVehicleWatcher.checkIfCrewedThisTurn(permanent, input.getObject(), game))
                .orElse(false);
    }
}
