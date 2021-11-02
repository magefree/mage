package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ControlsPermanentsControllerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SkipUntapOptionalAbility;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.condition.common.SourceTappedCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.CardIdPredicate;
import mage.filter.predicate.permanent.ControllerControlsIslandPredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class Seasinger extends CardImpl {

    private static final String rule = "Gain control of target creature whose controller controls an Island for as long as you control Seasinger and Seasinger remains tapped";
    private static final FilterPermanent islandYouControl = new FilterPermanent("Island");
    private static final FilterCreaturePermanent creatureWhoseControllerControlsIsland = new FilterCreaturePermanent("creature whose controller controls an island");

    static {
        islandYouControl.add(SubType.ISLAND.getPredicate());
        islandYouControl.add(TargetController.YOU.getControllerPredicate());
    }

    public Seasinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");
        this.subtype.add(SubType.MERFOLK);

        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        FilterPermanent seasinger = new FilterPermanent();
        seasinger.add(TargetController.YOU.getControllerPredicate());
        seasinger.add(new CardIdPredicate(this.getId()));
        Condition condition = new CompoundCondition(new PermanentsOnTheBattlefieldCondition(seasinger, ComparisonType.EQUAL_TO, 1), SourceTappedCondition.instance);

        // When you control no Islands, sacrifice Seasinger.
        this.addAbility(new ControlsPermanentsControllerTriggeredAbility(
                new FilterLandPermanent(SubType.ISLAND, "no Islands"), ComparisonType.EQUAL_TO, 0,
                new SacrificeSourceEffect()));

        // You may choose not to untap Seasinger during your untap step.
        this.addAbility(new SkipUntapOptionalAbility());

        // {tap}: Gain control of target creature whose controller controls an Island for as long as you control Seasinger and Seasinger remains tapped.
        ConditionalContinuousEffect effect = new ConditionalContinuousEffect(
                new GainControlTargetEffect(Duration.Custom),
                condition, rule);
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new TapSourceCost());
        creatureWhoseControllerControlsIsland.add(new ControllerControlsIslandPredicate());
        ability.addTarget(new TargetCreaturePermanent(creatureWhoseControllerControlsIsland));
        this.addAbility(ability);
    }

    private Seasinger(final Seasinger card) {
        super(card);
    }

    @Override
    public Seasinger copy() {
        return new Seasinger(this);
    }
}
