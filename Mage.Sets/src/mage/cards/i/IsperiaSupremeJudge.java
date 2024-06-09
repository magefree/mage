
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksAllTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 *
 * @author LevelX2
 */
public final class IsperiaSupremeJudge extends CardImpl {
 

    public IsperiaSupremeJudge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}{W}{U}{U}");
        this.subtype.add(SubType.SPHINX);
        this.supertype.add(SuperType.LEGENDARY);
        


        this.power = new MageInt(6);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever a creature attacks you or a planeswalker you control, you may draw a card.
        this.addAbility(new AttacksAllTriggeredAbility(new DrawCardSourceControllerEffect(1), true, true));
    }

    private IsperiaSupremeJudge(final IsperiaSupremeJudge card) {
        super(card);
    }

    @Override
    public IsperiaSupremeJudge copy() {
        return new IsperiaSupremeJudge(this);
    }
}
