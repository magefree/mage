package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AstorBearerOfBlades extends CardImpl {

    private static final FilterCard filter = new FilterCard("Equipment or Vehicle card");
    private static final FilterPermanent filter2 = new FilterPermanent(SubType.VEHICLE, "Vehicles");

    static {
        filter.add(Predicates.or(
                SubType.EQUIPMENT.getPredicate(),
                SubType.VEHICLE.getPredicate()
        ));
    }

    public AstorBearerOfBlades(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Astor, Bearer of Blades enters the battlefield, look at the top seven cards of your library. You may reveal an Equipment or Vehicle card from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new LookLibraryAndPickControllerEffect(
                        7, 1, filter,
                        LookLibraryControllerEffect.PutCards.HAND,
                        LookLibraryControllerEffect.PutCards.BOTTOM_RANDOM
                )
        ));

        // Equipment you control have equip {1}.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new EquipAbility(1, false), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_EQUIPMENT
        )));

        // Vehicles you control have crew 1.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new CrewAbility(1), Duration.WhileOnBattlefield, filter2
        )));
    }

    private AstorBearerOfBlades(final AstorBearerOfBlades card) {
        super(card);
    }

    @Override
    public AstorBearerOfBlades copy() {
        return new AstorBearerOfBlades(this);
    }
}
