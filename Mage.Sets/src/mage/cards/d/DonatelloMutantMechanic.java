package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.PutIntoGraveFromBattlefieldAllTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PutSavedPermanentCountersTargetEffect;
import mage.abilities.effects.common.continuous.AddCardSubTypeTargetEffect;
import mage.abilities.effects.common.continuous.AddCardTypeTargetEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.permanent.CounterAnyPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DonatelloMutantMechanic extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledArtifactPermanent();

    static {
        filter.add(CounterAnyPredicate.instance);
    }

    public DonatelloMutantMechanic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.NINJA);
        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // {T}: Put three +1/+1 counters on target artifact you control. If it isn't a creature, it becomes a 0/0 Robot creature in addition to its other types. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance(3)), new TapSourceCost()
        );
        ability.addEffect(new DonatelloMutantMechanicEffect());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT));
        this.addAbility(ability);

        // Whenever an artifact you control is put into a graveyard from the battlefield, if it had counters on it, put those counters on up to one target artifact or creature you control.
        ability = new PutIntoGraveFromBattlefieldAllTriggeredAbility(
                new PutSavedPermanentCountersTargetEffect("permanentDied"),
                false, filter, false
        );
        ability.addTarget(new TargetPermanent(
                0, 1, StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_OR_CREATURE
        ));
        this.addAbility(ability);
    }

    private DonatelloMutantMechanic(final DonatelloMutantMechanic card) {
        super(card);
    }

    @Override
    public DonatelloMutantMechanic copy() {
        return new DonatelloMutantMechanic(this);
    }
}

class DonatelloMutantMechanicEffect extends OneShotEffect {

    DonatelloMutantMechanicEffect() {
        super(Outcome.Benefit);
        staticText = "If it isn't a creature, it becomes a 0/0 Robot creature in addition to its other types";
    }

    private DonatelloMutantMechanicEffect(final DonatelloMutantMechanicEffect effect) {
        super(effect);
    }

    @Override
    public DonatelloMutantMechanicEffect copy() {
        return new DonatelloMutantMechanicEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null || !permanent.isCreature(game)) {
            return false;
        }
        game.addEffect(new AddCardTypeTargetEffect(Duration.Custom, CardType.CREATURE)
                .setTargetPointer(new FixedTarget(permanent, game)), source);
        game.addEffect(new AddCardSubTypeTargetEffect(SubType.ROBOT, Duration.Custom)
                .setTargetPointer(new FixedTarget(permanent, game)), source);
        game.addEffect(new SetBasePowerToughnessTargetEffect(0, 0, Duration.Custom)
                .setTargetPointer(new FixedTarget(permanent, game)), source);
        return true;
    }
}
