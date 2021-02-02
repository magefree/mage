package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.SubType;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPlaneswalkerPermanent;

/**
 *
 * @author TheElk801
 */
public final class CourtCleric extends CardImpl {

    private static final FilterControlledPlaneswalkerPermanent filter
            = new FilterControlledPlaneswalkerPermanent(
                    SubType.AJANI,
                    "an Ajani planeswalker"
            );

    public CourtCleric(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Court Cleric gets +1/+1 as long as you control an Ajani planeswalker.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(
                        new BoostSourceEffect(1, 1, Duration.WhileOnBattlefield),
                        new PermanentsOnTheBattlefieldCondition(filter),
                        "{this} gets +1/+1 as long as you control an Ajani planeswalker"
                )
        ));
    }

    private CourtCleric(final CourtCleric card) {
        super(card);
    }

    @Override
    public CourtCleric copy() {
        return new CourtCleric(this);
    }
}
