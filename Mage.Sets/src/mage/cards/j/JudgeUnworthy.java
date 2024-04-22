
package mage.cards.j;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAttackingOrBlockingCreature;

/**
 *
 * @author fireshoes
 */
public final class JudgeUnworthy extends CardImpl {

    public JudgeUnworthy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");

        // Choose target attacking or blocking creature. Scry 3, then reveal the top card of your library. Judge Unworthy deals damage equal to that card's converted mana cost to that creature.
        this.getSpellAbility().addTarget(new TargetAttackingOrBlockingCreature());
        Effect effect = new ScryEffect(3);
        effect.setText("Choose target attacking or blocking creature. Scry 3");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addEffect(new JudgeUnworthyEffect());  
    }

    private JudgeUnworthy(final JudgeUnworthy card) {
        super(card);
    }

    @Override
    public JudgeUnworthy copy() {
        return new JudgeUnworthy(this);
    }
}

class JudgeUnworthyEffect extends OneShotEffect {
    
    public JudgeUnworthyEffect() {
        super(Outcome.Damage);
        this.staticText = ", then reveal the top card of your library. {this} deals damage equal to that card's mana value to that creature";
    }
    
    private JudgeUnworthyEffect(final JudgeUnworthyEffect effect) {
        super(effect);
    }
    
    @Override
    public JudgeUnworthyEffect copy() {
        return new JudgeUnworthyEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card sourceCard = game.getCard(source.getSourceId());
        if (sourceCard != null && controller != null) {
            if (controller.getLibrary().hasCards()) {
                Card card = controller.getLibrary().getFromTop(game);
                controller.revealCards(sourceCard.getName(), new CardsImpl(card), game);
                Permanent targetCreature = game.getPermanent(this.getTargetPointer().getFirst(game, source));
                if (targetCreature != null) {
                    targetCreature.damage(card.getManaValue(), source.getSourceId(), source, game, false, true);
                    return true;
                }
            }
            return true;
        }
        return false;
    }
}
