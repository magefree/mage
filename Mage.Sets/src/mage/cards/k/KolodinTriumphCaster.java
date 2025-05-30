package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.SaddleTargetMountEffect;
import mage.abilities.effects.common.continuous.AddCardTypeTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.target.targetpointer.TargetPointer;

/**
 *
 * @author Jmlundeen
 */
public final class KolodinTriumphCaster extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Mounts and Vehicles you control");
    private static final FilterControlledPermanent mountFilter = new FilterControlledPermanent("Mount");
    private static final FilterControlledPermanent vehicleFilter = new FilterControlledPermanent("Vehicle");
    static {
        filter.add(Predicates.or(
                SubType.MOUNT.getPredicate(),
                SubType.VEHICLE.getPredicate()
        ));
        mountFilter.add(SubType.MOUNT.getPredicate());
        vehicleFilter.add(SubType.VEHICLE.getPredicate());
    }

    public KolodinTriumphCaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PILOT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Mounts and Vehicles you control have haste.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new GainAbilityControlledEffect(HasteAbility.getInstance(),
                        Duration.WhileOnBattlefield, filter)));
        // Whenever a Mount you control enters, it becomes saddled until end of turn.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD, new SaddleTargetMountEffect("it becomes saddled until end of turn"),
                mountFilter, false, SetTargetPointer.PERMANENT));
        // Whenever a Vehicle you control enters, it becomes an artifact creature until end of turn.
        Effect effect = new AddCardTypeTargetEffect(Duration.EndOfTurn, CardType.ARTIFACT, CardType.CREATURE)
                .setText("it becomes an artifact creature until end of turn");
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD, effect, vehicleFilter,
                false, SetTargetPointer.PERMANENT));
    }

    private KolodinTriumphCaster(final KolodinTriumphCaster card) {
        super(card);
    }

    @Override
    public KolodinTriumphCaster copy() {
        return new KolodinTriumphCaster(this);
    }
}
