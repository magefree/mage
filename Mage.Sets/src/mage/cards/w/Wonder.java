package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlyingAbility;
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
 * @author magenoxx_at_gmail.com
 */
public final class Wonder extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(new FilterControlledPermanent(SubType.ISLAND));

    public Wonder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.subtype.add(SubType.INCARNATION);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // As long as Wonder is in your graveyard and you control an Island, creatures you control have flying.
        this.addAbility(new SimpleStaticAbility(
                Zone.GRAVEYARD,
                new ConditionalContinuousEffect(new GainAbilityControlledEffect(
                        FlyingAbility.getInstance(), Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURE
                ), condition, "as long as this card is in your graveyard and you control an Island, creatures you control have flying")
        ));
    }

    private Wonder(final Wonder card) {
        super(card);
    }

    @Override
    public Wonder copy() {
        return new Wonder(this);
    }
}
