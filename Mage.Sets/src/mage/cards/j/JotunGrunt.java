
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInASingleGraveyard;

/**
 *
 * @author emerald000
 */
public final class JotunGrunt extends CardImpl {

    public JotunGrunt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Cumulative upkeep-Put two cards from a single graveyard on the bottom of their owner's library.
        this.addAbility(new CumulativeUpkeepAbility(new JotunGruntCost()));
    }

    private JotunGrunt(final JotunGrunt card) {
        super(card);
    }

    @Override
    public JotunGrunt copy() {
        return new JotunGrunt(this);
    }
}

class JotunGruntCost extends CostImpl {

    JotunGruntCost() {
        this.addTarget(new TargetCardInASingleGraveyard(2, 2, new FilterCard()));
        this.text = "Put two cards from a single graveyard on the bottom of their owner's library";
    }


    JotunGruntCost(final JotunGruntCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            if (targets.choose(Outcome.Removal, controllerId, source.getSourceId(), source, game)) {
                for (UUID targetId: targets.get(0).getTargets()) {
                    Card card = game.getCard(targetId);
                    if (card == null || game.getState().getZone(targetId) != Zone.GRAVEYARD) {
                        return false;
                    }
                    paid |= controller.moveCardToLibraryWithInfo(card, source, game, Zone.GRAVEYARD, false, true);
                }
            }

        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return targets.canChoose(controllerId, source, game);
    }

    @Override
    public JotunGruntCost copy() {
        return new JotunGruntCost(this);
    }
}
