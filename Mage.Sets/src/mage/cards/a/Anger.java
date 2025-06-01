
package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author Backfir3
 */
public final class Anger extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(new FilterControlledPermanent(SubType.MOUNTAIN));

    public Anger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.subtype.add(SubType.INCARNATION);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // As long as Anger is in your graveyard and you control a Mountain, creatures you control have haste
        this.addAbility(new SimpleStaticAbility(
                Zone.GRAVEYARD,
                new ConditionalContinuousEffect(
                        new GainAbilityControlledEffect(
                                HasteAbility.getInstance(), Duration.WhileOnBattlefield,
                                StaticFilters.FILTER_PERMANENT_CREATURE
                        ), condition, "as long as this card is in your graveyard " +
                        "and you control a Mountain, creatures you control have haste"
                )
        ));
    }

    private Anger(final Anger card) {
        super(card);
    }

    @Override
    public Anger copy() {
        return new Anger(this);
    }
}
