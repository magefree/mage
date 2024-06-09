package mage.cards.o;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OrderOfSacredDusk extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.VAMPIRE, "Vampires");

    public OrderOfSacredDusk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{W}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Convoke
        this.addAbility(new ConvokeAbility());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Exalted
        this.addAbility(new ExaltedAbility());

        // Other Vampires you control have exalted.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new ExaltedAbility(), Duration.WhileOnBattlefield, filter, true
        )));
    }

    private OrderOfSacredDusk(final OrderOfSacredDusk card) {
        super(card);
    }

    @Override
    public OrderOfSacredDusk copy() {
        return new OrderOfSacredDusk(this);
    }
}
