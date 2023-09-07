
package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author cbt33
 */
public final class LaquatussCreativity extends CardImpl {

    public LaquatussCreativity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{U}");


        // Target player draws cards equal to the number of cards in their hand, then discards that many cards.
        this.getSpellAbility().addEffect(new LaquatussCreativityEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private LaquatussCreativity(final LaquatussCreativity card) {
        super(card);
    }

    @Override
    public LaquatussCreativity copy() {
        return new LaquatussCreativity(this);
    }
}

class LaquatussCreativityEffect extends OneShotEffect {
    
    public LaquatussCreativityEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target player draws cards equal to the number of cards in their hand, then discards that many cards.";
    }
    
    private LaquatussCreativityEffect(final LaquatussCreativityEffect effect) {
        super(effect);
    }
    
    @Override
    public LaquatussCreativityEffect copy() {
        return new LaquatussCreativityEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if(player != null) {
            int handCount = player.getHand().count(new FilterCard(), game);
            player.drawCards(handCount, source, game);
            player.discard(handCount, false, false, source, game);
        }
        return false;
    }
}
