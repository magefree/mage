
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.common.DiscardXTargetCost;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.target.Target;
import mage.target.TargetPermanent;

/**
 *
 * @author fireshoes
 */
public final class TurbulentDreams extends CardImpl {
    
    private static final FilterPermanent filter = new FilterPermanent("nonland permanents");
    
    static {
        filter.add(Predicates.not(new CardTypePredicate(CardType.LAND)));
    }

    public TurbulentDreams(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{U}{U}");

        // As an additional cost to cast Turbulent Dreams, discard X cards.
        this.getSpellAbility().addCost(new DiscardXTargetCost(new FilterCard("cards"), true));
        
        // Return X target nonland permanents to their owners' hands.
        Effect effect = new ReturnToHandTargetEffect();
        effect.setText("Return X target nonland permanents to their owners' hands");
        this.getSpellAbility().addEffect(effect);
    }

    public TurbulentDreams(final TurbulentDreams card) {
        super(card);
    }
    
    @Override
    public void adjustTargets(Ability ability, Game game) {
        int xValue = new GetXValue().calculate(game, ability, null);
            Target target = new TargetPermanent(0, xValue, filter, false);
            ability.addTarget(target);
    }

    @Override
    public TurbulentDreams copy() {
        return new TurbulentDreams(this);
    }
}
