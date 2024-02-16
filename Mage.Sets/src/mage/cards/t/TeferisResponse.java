
package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterStackObject;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.mageobject.TargetsPermanentPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.target.TargetStackObject;

import java.util.UUID;

/**
 *
 * @author AlumiuN
 */
public final class TeferisResponse extends CardImpl {

    private static final FilterStackObject filter = new FilterStackObject("spell or ability an opponent controls that targets a land you control");

    static {
        filter.add(new TargetsPermanentPredicate(new FilterControlledLandPermanent()));
    }
    
    public TeferisResponse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");

        // Counter target spell or ability an opponent controls that targets a land you control. If a permanent's ability is countered this way, destroy that permanent.
        this.getSpellAbility().addEffect(new TeferisResponseEffect());
        this.getSpellAbility().addTarget(new TargetStackObject(filter));
        
        // Draw two cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2).concatBy("<br>"));
    }

    private TeferisResponse(final TeferisResponse card) {
        super(card);
    }

    @Override
    public TeferisResponse copy() {
        return new TeferisResponse(this);
    }
}

class TeferisResponseEffect extends OneShotEffect {
    
    public TeferisResponseEffect() {
        super(Outcome.Detriment);
        this.staticText = "Counter target spell or ability an opponent controls that targets a land you control. If a permanent's ability is countered this way, destroy that permanent";
    }
        
    private TeferisResponseEffect(final TeferisResponseEffect effect) {
        super(effect);
    }

    @Override
    public TeferisResponseEffect copy() {
        return new TeferisResponseEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        UUID targetId = source.getFirstTarget();
        StackObject stackObject = game.getStack().getStackObject(targetId);
        if (targetId != null && game.getStack().counter(targetId, source, game)) {
            UUID permanentId = stackObject.getSourceId();
            if (permanentId != null) {
                Permanent usedPermanent = game.getPermanent(permanentId);
                if (usedPermanent != null) {
                    usedPermanent.destroy(source, game, false);
                }
            }
            return true;
        }
        
        return false;
    }
}