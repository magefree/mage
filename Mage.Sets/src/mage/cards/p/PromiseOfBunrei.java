
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SpiritToken;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class PromiseOfBunrei extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a creature you control");
    
    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
    }
    
    public PromiseOfBunrei(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W}");

        // When a creature you control dies, sacrifice Promise of Bunrei. If you do, create four 1/1 colorless Spirit creature tokens.
        this.addAbility(new DiesCreatureTriggeredAbility(new PromiseOfBunreiEffect(), false, filter));
    }

    public PromiseOfBunrei(final PromiseOfBunrei card) {
        super(card);
    }

    @Override
    public PromiseOfBunrei copy() {
        return new PromiseOfBunrei(this);
    }
}

class PromiseOfBunreiEffect extends OneShotEffect {
    
    public PromiseOfBunreiEffect() {
        super(Outcome.Benefit);
        this.staticText = "sacrifice {this}. If you do, create four 1/1 colorless Spirit creature tokens";
    }
    
    public PromiseOfBunreiEffect(final PromiseOfBunreiEffect effect) {
        super(effect);
    }
    
    @Override
    public PromiseOfBunreiEffect copy() {
        return new PromiseOfBunreiEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (controller != null && permanent != null) {
            if (permanent.sacrifice(source.getSourceId(), game)) {
                return new CreateTokenEffect(new SpiritToken(), 4).apply(game, source);
            }
            return true;
        }
        return false;
    }
}
