package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author muz
 */
public final class StarfleetSecurity extends CardImpl {

    public StarfleetSecurity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.OFFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When this creature dies during your turn, investigate.
        this.addAbility(new DiesSourceTriggeredAbility(new InvestigateEffect()).withTriggerCondition(MyTurnCondition.instance));
    }

    private StarfleetSecurity(final StarfleetSecurity card) {
        super(card);
    }

    @Override
    public StarfleetSecurity copy() {
        return new StarfleetSecurity(this);
    }
}
