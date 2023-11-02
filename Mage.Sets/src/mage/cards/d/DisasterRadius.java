
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author jeffwadsworth
 */
public final class DisasterRadius extends CardImpl {

    public DisasterRadius(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{5}{R}{R}");


        // As an additional cost to cast Disaster Radius, reveal a creature card from your hand.
        TargetCardInHand targetCard = new TargetCardInHand(new FilterCreatureCard("a creature card from your hand"));
        this.getSpellAbility().addCost(new RevealTargetFromHandCost(targetCard));
        
        // Disaster Radius deals X damage to each creature your opponents control, where X is the revealed card's converted mana cost.
        this.getSpellAbility().addEffect(new DisasterRadiusEffect());
    }

    private DisasterRadius(final DisasterRadius card) {
        super(card);
    }

    @Override
    public DisasterRadius copy() {
        return new DisasterRadius(this);
    }
}

class DisasterRadiusEffect extends OneShotEffect {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature your opponents control");
    
    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public DisasterRadiusEffect() {
        super(Outcome.GainLife);
        staticText = "{this} deals X damage to each creature your opponents control, where X is the revealed card's mana value";
    }

    private DisasterRadiusEffect(final DisasterRadiusEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        RevealTargetFromHandCost cost = (RevealTargetFromHandCost) source.getCosts().get(0);
        if (cost != null) {
            int damage = cost.manaValues;
            for (Permanent creature : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game)) {
                if (creature != null) {
                    creature.damage(damage, source.getSourceId(), source, game, false, false);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public DisasterRadiusEffect copy() {
        return new DisasterRadiusEffect(this);
    }

}
