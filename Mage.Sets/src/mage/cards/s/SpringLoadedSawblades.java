package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.AddCardTypeSourceEffect;
import mage.abilities.keyword.CraftAbility;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterOpponentsCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpringLoadedSawblades extends TransformingDoubleFacedCard {

    private static final FilterPermanent filter
            = new FilterOpponentsCreaturePermanent("tapped creature an opponent controls");
    private static final FilterControlledPermanent filter2
            = new FilterControlledArtifactPermanent("other untapped artifacts you control");

    static {
        filter.add(TappedPredicate.TAPPED);
        filter2.add(AnotherPredicate.instance);
        filter2.add(TappedPredicate.UNTAPPED);
    }

    public SpringLoadedSawblades(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.ARTIFACT}, new SubType[]{}, "{1}{W}",
                "Bladewheel Chariot",
                new CardType[]{CardType.ARTIFACT}, new SubType[]{SubType.VEHICLE}, "W"
        );
        this.getRightHalfCard().setPT(5, 5);

        this.secondSideCardClazz = mage.cards.b.BladewheelChariot.class;

        // Flash
        this.getLeftHalfCard().addAbility(FlashAbility.getInstance());

        // When Spring-Loaded Sawblades enters the battlefield, it deals 5 damage to target tapped creature an opponent controls.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(5, "it"));
        ability.addTarget(new TargetPermanent(filter));
        this.getLeftHalfCard().addAbility(ability);

        // Craft with artifact {3}{W}
        this.getLeftHalfCard().addAbility(new CraftAbility("{3}{W}"));

        // Bladewheel Chariot
        // Tap two other untapped artifacts you control: Bladewheel Chariot becomes an artifact creature until end of turn.
        this.getRightHalfCard().addAbility(new SimpleActivatedAbility(new AddCardTypeSourceEffect(
                Duration.EndOfTurn, CardType.ARTIFACT, CardType.CREATURE
        ).setText("{this} becomes an artifact creature until end of turn"), new TapTargetCost(new TargetControlledPermanent(2, filter2))));

        // Crew 1
        this.getRightHalfCard().addAbility(new CrewAbility(1));

        this.finalizeDFC();
    }

    private SpringLoadedSawblades(final SpringLoadedSawblades card) {
        super(card);
    }

    @Override
    public SpringLoadedSawblades copy() {
        return new SpringLoadedSawblades(this);
    }
}
