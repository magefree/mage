package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ControlsPermanentsControllerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SkipUntapOptionalAbility;
import mage.abilities.condition.common.SourceTappedCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class Seasinger extends CardImpl {

    private static final FilterPermanent filter
            = new FilterPermanent("Island");
    private static final FilterPermanent filter2
            = new FilterCreaturePermanent("creature whose controller controls an Island");

    static {
        filter.add(SubType.ISLAND.getPredicate());
        filter.add(TargetController.YOU.getControllerPredicate());
        filter2.add(SeasingerPredicate.instance);
    }

    public Seasinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");
        this.subtype.add(SubType.MERFOLK);

        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // When you control no Islands, sacrifice Seasinger.
        this.addAbility(new ControlsPermanentsControllerTriggeredAbility(
                filter, ComparisonType.EQUAL_TO, 0, new SacrificeSourceEffect()
        ));

        // You may choose not to untap Seasinger during your untap step.
        this.addAbility(new SkipUntapOptionalAbility());

        // {tap}: Gain control of target creature whose controller controls an Island for as long as you control Seasinger and Seasinger remains tapped.
        Ability ability = new SimpleActivatedAbility(new ConditionalContinuousEffect(
                new GainControlTargetEffect(Duration.WhileControlled), SourceTappedCondition.TAPPED,
                "gain control of target creature whose controller controls " +
                        "an Island for as long as you control {this} and {this} remains tapped"
        ), new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter2));
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

enum SeasingerPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;
    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.ISLAND);

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        return game.getBattlefield().contains(filter, input.getObject().getControllerId(), input.getSource(), game, 1);
    }
}
