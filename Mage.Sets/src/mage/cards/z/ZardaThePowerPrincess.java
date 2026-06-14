package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.ExaltedAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class ZardaThePowerPrincess extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.HERO, "Heroes");

    public ZardaThePowerPrincess(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Other Heroes you control have exalted.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
            new ExaltedAbility(), Duration.WhileOnBattlefield, filter, true
        )));

    }

    private ZardaThePowerPrincess(final ZardaThePowerPrincess card) {
        super(card);
    }

    @Override
    public ZardaThePowerPrincess copy() {
        return new ZardaThePowerPrincess(this);
    }
}
