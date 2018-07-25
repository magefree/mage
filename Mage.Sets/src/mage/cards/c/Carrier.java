package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.LockedInDynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author NinthWorld
 */
public final class Carrier extends CardImpl {

    public Carrier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");
        
        this.subtype.add(SubType.PROTOSS);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Carrier gets +1/+0 for each card in your hand.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostSourceEffect(new CardsInControllerHandCount(), new StaticValue(0), Duration.WhileOnBattlefield)));

        // Whenever Carrier attacks, draw a card.
        this.addAbility(new AttacksTriggeredAbility(new DrawCardSourceControllerEffect(1), false));
    }

    public Carrier(final Carrier card) {
        super(card);
    }

    @Override
    public Carrier copy() {
        return new Carrier(this);
    }
}
