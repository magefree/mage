package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PapercraftDecoy extends CardImpl {

    public PapercraftDecoy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");

        this.subtype.add(SubType.FROG);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Papercraft Decoy leaves the battlefield, you may pay {2}. If you do, draw a card.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new DoIfCostPaid(
                new DrawCardSourceControllerEffect(1), new GenericManaCost(2)
        ), false));
    }

    private PapercraftDecoy(final PapercraftDecoy card) {
        super(card);
    }

    @Override
    public PapercraftDecoy copy() {
        return new PapercraftDecoy(this);
    }
}
