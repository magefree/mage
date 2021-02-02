
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.ExileOpponentsCardFromExileToGraveyardCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

/**
 *
 * @author fireshoes
 */
public final class MindRaker extends CardImpl {

    public MindRaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.PROCESSOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // When Mind Raker enters the battlefield, you may put a card an opponent owns from exile into that player's graveyard. If you do, each opponent discards a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new DoIfCostPaid(new DiscardEachPlayerEffect(TargetController.OPPONENT), new ExileOpponentsCardFromExileToGraveyardCost(true)), false));
    }

    private MindRaker(final MindRaker card) {
        super(card);
    }

    @Override
    public MindRaker copy() {
        return new MindRaker(this);
    }
}
