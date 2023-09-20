package mage.cards.r;

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
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class RiddleOfLightning extends CardImpl {

    public RiddleOfLightning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{R}{R}");

        // Choose any target. Scry 3, then reveal the top card of your library. Riddle of Lightning deals damage equal to that card's converted mana cost to that creature or player.
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        Effect effect = new ScryEffect(3);
        effect.setText("Choose any target. Scry 3");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addEffect(new RiddleOfLightningEffect());
    }

    private RiddleOfLightning(final RiddleOfLightning card) {
        super(card);
    }

    @Override
    public RiddleOfLightning copy() {
        return new RiddleOfLightning(this);
    }
}

class RiddleOfLightningEffect extends OneShotEffect {

    public RiddleOfLightningEffect() {
        super(Outcome.Damage);
        this.staticText = ", then reveal the top card of your library. {this} deals damage equal to that card's mana value to that permanent or player";
    }

    private RiddleOfLightningEffect(final RiddleOfLightningEffect effect) {
        super(effect);
    }

    @Override
    public RiddleOfLightningEffect copy() {
        return new RiddleOfLightningEffect(this);
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
                Player targetPlayer = game.getPlayer(this.getTargetPointer().getFirst(game, source));
                if (targetPlayer != null) {
                    targetPlayer.damage(card.getManaValue(), source.getSourceId(), source, game);
                    return true;
                }
            }
            return true;
        }
        return false;
    }
}
