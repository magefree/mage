package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author Styxo
 */
public final class LammastideWeave extends CardImpl {

    public LammastideWeave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Name a card, then target player puts the top card of their library into their graveyard. If that card is the named card, you gain life equal to its converted mana cost.
        this.getSpellAbility().addEffect(new ChooseACardNameEffect(ChooseACardNameEffect.TypeOfName.ALL));
        this.getSpellAbility().addEffect(new LammastideWeaveEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));

    }

    public LammastideWeave(final LammastideWeave card) {
        super(card);
    }

    @Override
    public LammastideWeave copy() {
        return new LammastideWeave(this);
    }
}

class LammastideWeaveEffect extends OneShotEffect {

    public LammastideWeaveEffect() {
        super(Outcome.DrawCard);
        this.staticText = ", then target player puts the top card of their library into their graveyard. "
                + "If that card has the chosen name, you gain life equal to its converted mana cost.";
    }

    public LammastideWeaveEffect(final LammastideWeaveEffect effect) {
        super(effect);
    }

    @Override
    public LammastideWeaveEffect copy() {
        return new LammastideWeaveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        String cardName = (String) game.getState().getValue(source.getSourceId().toString() + ChooseACardNameEffect.INFO_KEY);
        if (controller != null && targetPlayer != null && cardName != null && !cardName.isEmpty()) {
            Card card = targetPlayer.getLibrary().getFromTop(game);
            if (card != null) {
                controller.moveCards(card, Zone.GRAVEYARD, source, game);
                if (card.getName().equals(cardName)) {
                    controller.gainLife(card.getConvertedManaCost(), game, source);
                }
            }
            return true;
        }
        return false;
    }

}
