package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.continuous.AddCardTypeSourceEffect;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BladewheelChariot extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledArtifactPermanent("other untapped artifacts you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TappedPredicate.UNTAPPED);
    }

    public BladewheelChariot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);
        this.nightCard = true;
        this.color.setWhite(true);

        // Tap two other untapped artifacts you control: Bladewheel Chariot becomes an artifact creature until end of turn.
        this.addAbility(new SimpleActivatedAbility(new AddCardTypeSourceEffect(
                Duration.EndOfTurn, CardType.ARTIFACT, CardType.CREATURE
        ).setText("{this} becomes an artifact creature until end of turn"), new TapTargetCost(new TargetControlledPermanent(2, filter))));

        // Crew 1
        this.addAbility(new CrewAbility(1));
    }

    private BladewheelChariot(final BladewheelChariot card) {
        super(card);
    }

    @Override
    public BladewheelChariot copy() {
        return new BladewheelChariot(this);
    }
}
