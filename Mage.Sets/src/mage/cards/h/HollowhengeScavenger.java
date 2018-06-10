
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author nantuko
 */
public final class HollowhengeScavenger extends CardImpl {

    private static final String staticText = "Morbid - When {this} enters the battlefield, if a creature died this turn, you gain 5 life.";

    public HollowhengeScavenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        this.color.setGreen(true);
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Morbid - When Hollowhenge Scavenger enters the battlefield, if a creature died this turn, you gain 5 life.
        TriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new GainLifeEffect(5));
        this.addAbility(new ConditionalTriggeredAbility(ability, MorbidCondition.instance, staticText));
    }

    public HollowhengeScavenger(final HollowhengeScavenger card) {
        super(card);
    }

    @Override
    public HollowhengeScavenger copy() {
        return new HollowhengeScavenger(this);
    }
}
