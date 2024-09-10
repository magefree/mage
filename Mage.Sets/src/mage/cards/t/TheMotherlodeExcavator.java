package mage.cards.t;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.combat.CantBlockAllEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.filter.predicate.permanent.DefendingPlayerControlsSourceAttackingPredicate;
import mage.game.Game;
import mage.target.TargetPermanent;
import mage.target.common.TargetOpponent;

/**
 * @author Cguy7777
 */
public final class TheMotherlodeExcavator extends CardImpl {

    private static final FilterLandPermanent filterLand
            = new FilterLandPermanent("nonbasic land defending player controls");
    private static final FilterCreaturePermanent filterCreatures
            = new FilterCreaturePermanent("creatures that player controls without flying");

    static {
        filterLand.add(DefendingPlayerControlsSourceAttackingPredicate.instance);
        filterLand.add(Predicates.not(SuperType.BASIC.getPredicate()));
        filterCreatures.add(DefendingPlayerControlsSourceAttackingPredicate.instance);
        filterCreatures.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public TheMotherlodeExcavator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ROBOT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // When The Motherlode, Excavator enters the battlefield, choose target opponent.
        // You get an amount of {E} equal to the number of nonbasic lands that player controls.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new GetEnergyCountersControllerEffect(TheMotherlodeExcavatorCount.instance)
                        .setText("choose target opponent. You get an amount of {E} <i>(energy counters)</i> " +
                                "equal to the number of nonbasic lands that player controls"));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // Whenever The Motherlode attacks, you may pay {E}{E}{E}{E}.
        // When you do, destroy target nonbasic land defending player controls,
        // and creatures that player controls without flying can't block this turn.
        ReflexiveTriggeredAbility reflexiveAbility
                = new ReflexiveTriggeredAbility(new DestroyTargetEffect(), false);
        reflexiveAbility.addEffect(new CantBlockAllEffect(filterCreatures, Duration.EndOfTurn)
                .concatBy(", and"));
        reflexiveAbility.addTarget(new TargetPermanent(filterLand));

        this.addAbility(new AttacksTriggeredAbility(
                new DoWhenCostPaid(reflexiveAbility, new PayEnergyCost(4), "Pay {E}{E}{E}{E}?")));
    }

    private TheMotherlodeExcavator(final TheMotherlodeExcavator card) {
        super(card);
    }

    @Override
    public TheMotherlodeExcavator copy() {
        return new TheMotherlodeExcavator(this);
    }
}

enum TheMotherlodeExcavatorCount implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        if (sourceAbility.getFirstTarget() == null) {
            return 0;
        }
        FilterLandPermanent filter = FilterLandPermanent.nonbasicLands();
        filter.add(new ControllerIdPredicate(sourceAbility.getFirstTarget()));
        return game.getBattlefield().count(
                filter,
                sourceAbility.getControllerId(),
                sourceAbility,
                game);
    }

    @Override
    public TheMotherlodeExcavatorCount copy() {
        return this;
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public String getMessage() {
        return "nonbasic lands that player controls";
    }
}
