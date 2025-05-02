package mage.cards.o;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OutlawMedic extends CardImpl {

    public OutlawMedic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // When Outlaw Medic dies, draw a card.
        this.addAbility(new DiesSourceTriggeredAbility(new DrawCardSourceControllerEffect(1)));
    }

    private OutlawMedic(final OutlawMedic card) {
        super(card);
    }

    @Override
    public OutlawMedic copy() {
        return new OutlawMedic(this);
    }
}
