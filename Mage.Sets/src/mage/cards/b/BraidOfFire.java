
package mage.cards.b;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author AlumiuN
 */
public final class BraidOfFire extends CardImpl {

    public BraidOfFire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{R}");

        // Cumulative upkeep-Add {R}.
        this.addAbility(new CumulativeUpkeepAbility(new BraidOfFireCost()));
    }

    private BraidOfFire(final BraidOfFire card) {
        super(card);
    }

    @Override
    public BraidOfFire copy() {
        return new BraidOfFire(this);
    }
}

class BraidOfFireCost extends CostImpl {

    public BraidOfFireCost() {
        this.text = "Add {R}";
    }

    private BraidOfFireCost(final BraidOfFireCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player player = game.getPlayer(controllerId);
        if(player != null) {
            player.getManaPool().addMana(Mana.RedMana(1), game, ability);
            paid = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return game.getPlayer(controllerId) != null;
    }

    @Override
    public BraidOfFireCost copy() {
        return new BraidOfFireCost(this);
    }

}
