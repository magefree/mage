package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.BlightCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DreamSeizer extends CardImpl {

    public DreamSeizer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When this creature enters, you may blight 1. If you do, each opponent discards a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DoIfCostPaid(
                new DiscardEachPlayerEffect(TargetController.OPPONENT), new BlightCost(1)
        )));
    }

    private DreamSeizer(final DreamSeizer card) {
        super(card);
    }

    @Override
    public DreamSeizer copy() {
        return new DreamSeizer(this);
    }
}
