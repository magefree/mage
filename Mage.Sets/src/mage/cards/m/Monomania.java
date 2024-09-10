package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;
import mage.target.common.TargetDiscard;

import java.util.UUID;

/**
 * @author nantuko
 */
public final class Monomania extends CardImpl {

    public Monomania(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{B}");

        // Target player chooses a card in their hand and discards the rest.
        this.getSpellAbility().addEffect(new MonomaniaEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private Monomania(final Monomania card) {
        super(card);
    }

    @Override
    public Monomania copy() {
        return new Monomania(this);
    }
}

class MonomaniaEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("a card");

    MonomaniaEffect() {
        super(Outcome.Discard);
        staticText = "Target player chooses a card in their hand and discards the rest";
    }

    private MonomaniaEffect(final MonomaniaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player == null || player.getHand().isEmpty()) {
            return false;
        }
        TargetCard target = new TargetDiscard(player.getId());
        player.choose(Outcome.Benefit, player.getHand(), target, source, game);
        Cards cards = player.getHand().copy();
        cards.removeIf(target.getTargets()::contains);
        return !player.discard(cards, false, source, game).isEmpty();
    }

    @Override
    public MonomaniaEffect copy() {
        return new MonomaniaEffect(this);
    }
}
