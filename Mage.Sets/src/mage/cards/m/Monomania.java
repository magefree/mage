
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;

/**
 *
 * @author nantuko
 */
public final class Monomania extends CardImpl {

    public Monomania(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{B}{B}");


        // Target player chooses a card in their hand and discards the rest.
        this.getSpellAbility().addEffect(new MonomaniaEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    public Monomania(final Monomania card) {
        super(card);
    }

    @Override
    public Monomania copy() {
        return new Monomania(this);
    }
}

class MonomaniaEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("a card");

    public MonomaniaEffect() {
        super(Outcome.Discard);
        staticText = "Target player chooses a card in their hand and discards the rest";
    }

    public MonomaniaEffect(final MonomaniaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            TargetCard target = new TargetCard(Zone.HAND, filter);
            if (player.choose(Outcome.Detriment, player.getHand(), target, game)) {
                while (player.getHand().size() > 1) {
                    for (UUID uuid : player.getHand()) {
                        if (!uuid.equals(target.getFirstTarget())) {
                            Card card = player.getHand().get(uuid, game);
                            if (card != null) {
                                player.discard(card, source, game);
                                break;
                            }
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public MonomaniaEffect copy() {
        return new MonomaniaEffect(this);
    }

}
