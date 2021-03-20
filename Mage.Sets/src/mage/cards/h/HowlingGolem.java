package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Rystan
 */
public final class HowlingGolem extends CardImpl {
    
    public HowlingGolem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");
        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever Howling Golem attacks or blocks, each player draws a card.
        this.addAbility(new AttacksOrBlocksTriggeredAbility(new DrawCardAllEffect(1), false));
    }

    private HowlingGolem(final HowlingGolem card) {
        super(card);
    }

    @Override
    public HowlingGolem copy() {
        return new HowlingGolem(this);
    }    
}
