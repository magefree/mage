package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VolcanicSpite extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creature, planeswalker, or battle");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.PLANESWALKER.getPredicate(),
                CardType.BATTLE.getPredicate()
        ));
    }

    public VolcanicSpite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Volcanic Spite deals 3 damage to target creature, planeswalker, or battle. You may put a card from your hand on the bottom of your library. If you do, draw a card.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addEffect(new VolcanicSpiteEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private VolcanicSpite(final VolcanicSpite card) {
        super(card);
    }

    @Override
    public VolcanicSpite copy() {
        return new VolcanicSpite(this);
    }
}

class VolcanicSpiteEffect extends OneShotEffect {

    VolcanicSpiteEffect() {
        super(Outcome.Benefit);
        staticText = "you may put a card from your hand on the bottom of your library. If you do, draw a card";
    }

    private VolcanicSpiteEffect(final VolcanicSpiteEffect effect) {
        super(effect);
    }

    @Override
    public VolcanicSpiteEffect copy() {
        return new VolcanicSpiteEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null
                || player.getHand().isEmpty()
                || !player.chooseUse(
                outcome, "Put a card from your hand " +
                        "on the bottom of your library?", source, game
        )) {
            return false;
        }
        TargetCard target = new TargetCardInHand();
        player.choose(outcome, player.getHand(), target, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return false;
        }
        if (player.putCardsOnBottomOfLibrary(card, game, source, false)) {
            player.drawCards(1, source, game);
        }
        return true;
    }
}
