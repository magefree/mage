
package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author Plopman
 */
public final class LandGrant extends CardImpl {

    private static final FilterLandCard filter = new FilterLandCard("Forest card");
    
    static {
        filter.add(SubType.FOREST.getPredicate());
    }
    
    public LandGrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{G}");


        // If you have no land cards in hand, you may reveal your hand rather than pay Land Grant's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new LandGrantReavealCost(), new LandGrantCondition(),
            "If you have no land cards in hand, you may reveal your hand rather than pay this spell's mana cost."));

        // Search your library for a Forest card, reveal that card, and put it into your hand. Then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true, true));
    }

    private LandGrant(final LandGrant card) {
        super(card);
    }

    @Override
    public LandGrant copy() {
        return new LandGrant(this);
    }
}

class LandGrantCondition implements Condition {
 
    @Override
    public boolean apply(Game game, Ability source) {   
        Player player = game.getPlayer(source.getControllerId());
        if (player != null && player.getHand().count(new FilterLandCard(), game) == 0) {
            return true;
        }
        return false;
    }
    
    @Override
    public String toString() {
        return "If you have no land cards in hand";
    }
}

class LandGrantReavealCost extends CostImpl {

    public LandGrantReavealCost() {
        this.text = "reveal your hand";
    }

    private LandGrantReavealCost(final LandGrantReavealCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player player = game.getPlayer(controllerId);
        if (player != null) {
            player.revealCards("Land Grant", player.getHand(), game);
            paid = true;
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return true;
    }

    @Override
    public LandGrantReavealCost copy() {
        return new LandGrantReavealCost(this);
    }

}
