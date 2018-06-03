
package mage.cards.v;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.common.DiscardXTargetCost;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.common.FilterAttackingCreature;
import mage.game.Game;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class VengefulDreams extends CardImpl {

    public VengefulDreams(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}{W}");

        // As an additional cost to cast Vengeful Dreams, discard X cards.
        this.getSpellAbility().addCost(new DiscardXTargetCost(new FilterCard("cards"), true));
        
        // Exile X target attacking creatures.
        Effect effect = new ExileTargetEffect();
        effect.setText("Exile X target attacking creatures");
        this.getSpellAbility().addEffect(effect);
    }

    public VengefulDreams(final VengefulDreams card) {
        super(card);
    }
    
    @Override
    public void adjustTargets(Ability ability, Game game) {
        int xValue = new GetXValue().calculate(game, ability, null);
            Target target = new TargetCreaturePermanent(0, xValue, new FilterAttackingCreature(), false);
            ability.addTarget(target);
    }

    @Override
    public VengefulDreams copy() {
        return new VengefulDreams(this);
    }
}
