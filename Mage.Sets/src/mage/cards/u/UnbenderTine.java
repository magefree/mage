
package mage.cards.u;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class UnbenderTine extends CardImpl {
    
    private static final FilterPermanent filter = new FilterPermanent("another target permanent");
    
    static {
        filter.add(AnotherPredicate.instance);
    }

    public UnbenderTine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}{W}{U}");




        // {tap}: Untap another target permanent.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new UnbenderTineEffect(), new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private UnbenderTine(final UnbenderTine card) {
        super(card);
    }

    @Override
    public UnbenderTine copy() {
        return new UnbenderTine(this);
    }
}

class UnbenderTineEffect extends OneShotEffect {

    public UnbenderTineEffect() {
        super(Outcome.Untap);
        this.staticText = "Untap another target permanent";
    }

    private UnbenderTineEffect(final UnbenderTineEffect effect) {
        super(effect);
    }

    @Override
    public UnbenderTineEffect copy() {
        return new UnbenderTineEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetPermanent = game.getPermanent(source.getFirstTarget());
        if (targetPermanent == null) {
            return false;
        }
        return targetPermanent.untap(game);
    }
        
}