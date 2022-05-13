package mage.cards.b;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Styxo
 */
public final class BrainPry extends CardImpl {

    public BrainPry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        //Name a nonland card. Target player reveals their hand. That player discards a card with that name. If they can't, you draw a card.
        this.getSpellAbility().addEffect((new ChooseACardNameEffect(ChooseACardNameEffect.TypeOfName.NON_LAND_NAME)));
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new BrainPryEffect());
    }

    private BrainPry(final BrainPry card) {
        super(card);
    }

    @Override
    public BrainPry copy() {
        return new BrainPry(this);
    }
}

class BrainPryEffect extends OneShotEffect {

    public BrainPryEffect() {
        super(Outcome.Discard);
        staticText = "Target player reveals their hand. That player discards a card with that name. If they can't, you draw a card";
    }

    public BrainPryEffect(final BrainPryEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        String cardName = (String) game.getState().getValue(source.getSourceId().toString() + ChooseACardNameEffect.INFO_KEY);
        if (targetPlayer != null && controller != null && sourceObject != null && cardName != null) {
            boolean hasDiscarded = false;
            for (Card card : targetPlayer.getHand().getCards(game)) {
                if (CardUtil.haveSameNames(card, cardName, game)) {
                    targetPlayer.discard(card, false, source, game);
                    hasDiscarded = true;
                    break;
                }
            }
            if (!hasDiscarded) {
                controller.drawCards(1, source, game);
            }
            controller.lookAtCards(sourceObject.getName() + " Hand", targetPlayer.getHand(), game);
        }
        return true;
    }

    @Override
    public BrainPryEffect copy() {
        return new BrainPryEffect(this);
    }
}
