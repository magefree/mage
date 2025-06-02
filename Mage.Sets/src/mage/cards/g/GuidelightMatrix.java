package mage.cards.g;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CompositeCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.SaddleTargetMountEffect;
import mage.abilities.effects.common.continuous.AddCardTypeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.target.TargetPermanent;

/**
 *
 * @author Jmlundeen
 */
public final class GuidelightMatrix extends CardImpl {

    private static final FilterControlledPermanent mountFilter = new FilterControlledPermanent("Mount you control");
    private static final FilterControlledPermanent vehicleFilter = new FilterControlledPermanent("Vehicle you control");

    static {
        mountFilter.add(SubType.MOUNT.getPredicate());
        vehicleFilter.add(SubType.VEHICLE.getPredicate());
    }

    public GuidelightMatrix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");
        

        // When this artifact enters, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1)));
        // {2}, {T}: Target Mount you control becomes saddled until end of turn. Activate only as a sorcery.
        Ability saddledAbility = new ActivateAsSorceryActivatedAbility(new SaddleTargetMountEffect(), new ManaCostsImpl<>("{2}"));
        saddledAbility.addCost(new TapSourceCost());
        saddledAbility.addTarget(new TargetPermanent(mountFilter));
        this.addAbility(saddledAbility);
        // {2}, {T}: Target Vehicle you control becomes an artifact creature until end of turn.
        Ability animateVehicleAbility = new SimpleActivatedAbility(
                new AddCardTypeTargetEffect(Duration.EndOfTurn, CardType.ARTIFACT, CardType.CREATURE)
                        .setText("Target Vehicle you control becomes an artifact creature until end of turn."),
                new ManaCostsImpl<>("{2}"));
        animateVehicleAbility.addCost(new TapSourceCost());
        animateVehicleAbility.addTarget(new TargetPermanent(vehicleFilter));
        this.addAbility(animateVehicleAbility);
    }

    private GuidelightMatrix(final GuidelightMatrix card) {
        super(card);
    }

    @Override
    public GuidelightMatrix copy() {
        return new GuidelightMatrix(this);
    }
}
