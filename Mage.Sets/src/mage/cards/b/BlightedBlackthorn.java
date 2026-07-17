package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.costs.common.BlightCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BlightedBlackthorn extends CardImpl {

    public BlightedBlackthorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.TREEFOLK);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(7);

        // Whenever this creature enters or attacks, you may blight 2. If you do, you draw a card and lose 1 life.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new DoIfCostPaid(
                new DrawCardSourceControllerEffect(1, true), new BlightCost(2)
        ).addEffect(new LoseLifeSourceControllerEffect(1).setText("and lose 1 life"))));
    }

    private BlightedBlackthorn(final BlightedBlackthorn card) {
        super(card);
    }

    @Override
    public BlightedBlackthorn copy() {
        return new BlightedBlackthorn(this);
    }
}
