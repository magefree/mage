
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfDrawTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect.HandSizeModification;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public final class AnvilOfBogardan extends CardImpl {
    
    public AnvilOfBogardan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // Players have no maximum hand size.
        Effect effect = new MaximumHandSizeControllerEffect(Integer.MAX_VALUE, Duration.WhileOnBattlefield, HandSizeModification.SET, TargetController.ANY);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

        // At the beginning of each player's draw step, that player draws an additional card, then discards a card.
        this.addAbility(new BeginningOfDrawTriggeredAbility(Zone.BATTLEFIELD, new AnvilOfBogardanEffect(), TargetController.ANY, false));
    }
    
    private AnvilOfBogardan(final AnvilOfBogardan card) {
        super(card);
    }
    
    @Override
    public AnvilOfBogardan copy() {
        return new AnvilOfBogardan(this);
    }
}

class AnvilOfBogardanEffect extends OneShotEffect {
    
    private AnvilOfBogardanEffect(final AnvilOfBogardanEffect effect) {
        super(effect);
    }
    
    public AnvilOfBogardanEffect() {
        super(Outcome.Neutral);
        staticText = "that player draws an additional card, then discards a card";
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        if (targetPlayer != null) {
            targetPlayer.drawCards(1, source, game);
            targetPlayer.discard(1, false, false, source, game);
            return true;
        }
        return false;
    }
    
    @Override
    public AnvilOfBogardanEffect copy() {
        return new AnvilOfBogardanEffect(this);
    }
}
