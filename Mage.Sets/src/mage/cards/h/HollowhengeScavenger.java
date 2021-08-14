
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.hint.common.MorbidHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author nantuko
 */
public final class HollowhengeScavenger extends CardImpl {

    private static final String staticText = "<i>Morbid</i> &mdash; When {this} enters the battlefield, if a creature died this turn, you gain 5 life.";

    public HollowhengeScavenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        this.color.setGreen(true);
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // <i>Morbid</i> &mdash; When Hollowhenge Scavenger enters the battlefield, if a creature died this turn, you gain 5 life.
        TriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new GainLifeEffect(5));
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, MorbidCondition.instance, staticText).addHint(MorbidHint.instance));
    }

    private HollowhengeScavenger(final HollowhengeScavenger card) {
        super(card);
    }

    @Override
    public HollowhengeScavenger copy() {
        return new HollowhengeScavenger(this);
    }
}
