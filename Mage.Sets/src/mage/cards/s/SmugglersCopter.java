
package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author emerald000
 */
public final class SmugglersCopter extends CardImpl {

    public SmugglersCopter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Smuggler's Copter attacks or blocks, you may draw a card. If you do, discard a card.
        Effect effect = new DrawDiscardControllerEffect();
        effect.setText("you may draw a card. If you do, discard a card");
        this.addAbility(new AttacksOrBlocksTriggeredAbility(effect, true));

        // Crew 1
        this.addAbility(new CrewAbility(1));
    }

    private SmugglersCopter(final SmugglersCopter card) {
        super(card);
    }

    @Override
    public SmugglersCopter copy() {
        return new SmugglersCopter(this);
    }
}
