package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class PhosphorescentFeast extends CardImpl {

    public PhosphorescentFeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}{G}{G}");

        // Chroma - Reveal any number of cards in your hand. You gain 2 life for each green mana symbol in those cards' mana costs.
        Effect effect = new PhosphorescentFeastEffect();
        effect.setText("<i>Chroma</i> &mdash; Reveal any number of cards in your hand. You gain 2 life for each green mana symbol in those cards' mana costs.");
        this.getSpellAbility().addEffect(effect);

    }

    private PhosphorescentFeast(final PhosphorescentFeast card) {
        super(card);
    }

    @Override
    public PhosphorescentFeast copy() {
        return new PhosphorescentFeast(this);
    }
}

class PhosphorescentFeastEffect extends OneShotEffect {

    public PhosphorescentFeastEffect() {
        super(Outcome.GainLife);
    }

    public PhosphorescentFeastEffect(final PhosphorescentFeastEffect effect) {
        super(effect);
    }

    @Override
    public PhosphorescentFeastEffect copy() {
        return new PhosphorescentFeastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int chroma = 0;
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        if (player.getHand().count(new FilterCard(), game) > 0) {
            TargetCardInHand target = new TargetCardInHand(0, Integer.MAX_VALUE, new FilterCard());
            if (player.choose(Outcome.Benefit, target, source, game)) {

                Cards cards = new CardsImpl();
                for (UUID uuid : target.getTargets()) {
                    cards.add(player.getHand().get(uuid, game));
                }
                player.revealCards("cards", cards, game);
                for (Card card : cards.getCards(game)) {
                    chroma += card.getManaCost().getMana().getGreen();
                }
                player.gainLife(chroma * 2, game, source);
            }
        }
        return true;
    }
}
