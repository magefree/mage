package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;

/**
 *
 * @author TheElk801
 */
public final class BurglarRat extends CardImpl {

    public BurglarRat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.RAT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Burglar Rat enters the battlefield, each opponent discards a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new DiscardEachPlayerEffect(
                        StaticValue.get(1), false,
                        TargetController.OPPONENT
                )
        ));
    }

    private BurglarRat(final BurglarRat card) {
        super(card);
    }

    @Override
    public BurglarRat copy() {
        return new BurglarRat(this);
    }
}
