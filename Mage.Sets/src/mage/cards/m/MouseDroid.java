package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.RepairAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author NinthWorld
 */
public final class MouseDroid extends CardImpl {

    public MouseDroid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{U}");
        
        this.subtype.add(SubType.DROID);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // When Mouse Droid dies, draw a card.
        this.addAbility(new DiesSourceTriggeredAbility(new DrawCardSourceControllerEffect(1)));

        // Repair 3
        this.addAbility(new RepairAbility(3));
    }

    private MouseDroid(final MouseDroid card) {
        super(card);
    }

    @Override
    public MouseDroid copy() {
        return new MouseDroid(this);
    }
}
