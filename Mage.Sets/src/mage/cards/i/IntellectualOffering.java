
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterNonlandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetOpponent;

/**
 *
 * @author emerald000
 */
public final class IntellectualOffering extends CardImpl {

    public IntellectualOffering(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{4}{U}");


        // Choose an opponent. You and that player each draw three cards.
        this.getSpellAbility().addEffect(new IntellectualOfferingDrawEffect());
        
        // Choose an opponent. Untap all nonland permanents you control and all nonland permanents that player controls.
        this.getSpellAbility().addEffect(new IntellectualOfferingUntapEffect());
    }

    private IntellectualOffering(final IntellectualOffering card) {
        super(card);
    }

    @Override
    public IntellectualOffering copy() {
        return new IntellectualOffering(this);
    }
}

class IntellectualOfferingDrawEffect extends OneShotEffect {
    
    IntellectualOfferingDrawEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Choose an opponent. You and that player each draw three cards";
    }
    
    IntellectualOfferingDrawEffect(final IntellectualOfferingDrawEffect effect) {
        super(effect);
    }
    
    @Override
    public IntellectualOfferingDrawEffect copy() {
        return new IntellectualOfferingDrawEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Target target = new TargetOpponent(true);
            target.choose(Outcome.DrawCard, source.getControllerId(), source.getSourceId(), source, game);
            player.drawCards(3, source, game);
            Player opponent = game.getPlayer(target.getFirstTarget());
            if (opponent != null) {
                opponent.drawCards(3, source, game);
                return true;
            }
        }
        return false;
    }
}

class IntellectualOfferingUntapEffect extends OneShotEffect {
    
    IntellectualOfferingUntapEffect() {
        super(Outcome.Untap);
        this.staticText = "<br><br>Choose an opponent. Untap all nonland permanents you control and all nonland permanents that player controls";
    }
    
    IntellectualOfferingUntapEffect(final IntellectualOfferingUntapEffect effect) {
        super(effect);
    }
    
    @Override
    public IntellectualOfferingUntapEffect copy() {
        return new IntellectualOfferingUntapEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Target target = new TargetOpponent(true);
            target.choose(Outcome.Untap, source.getControllerId(), source.getSourceId(), source, game);
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(new FilterNonlandPermanent(), player.getId(), game)) {
                permanent.untap(game);
            }
            Player opponent = game.getPlayer(target.getFirstTarget());
            if (opponent != null) {
                for (Permanent permanent : game.getBattlefield().getAllActivePermanents(new FilterNonlandPermanent(), opponent.getId(), game)) {
                    permanent.untap(game);
                }
                return true;
            }
        }
        return false;
    }
}
