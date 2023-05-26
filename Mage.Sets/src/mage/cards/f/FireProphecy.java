package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FireProphecy extends CardImpl {

    public FireProphecy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Fire Prophecy deals 3 damage to target creature. You may put a card from your hand on the bottom of your library. If you do, draw a card.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new FireProphecyEffect());
    }

    private FireProphecy(final FireProphecy card) {
        super(card);
    }

    @Override
    public FireProphecy copy() {
        return new FireProphecy(this);
    }
}

class FireProphecyEffect extends OneShotEffect {

    FireProphecyEffect() {
        super(Outcome.Benefit);
        staticText = "You may put a card from your hand on the bottom of your library. If you do, draw a card.";
    }

    private FireProphecyEffect(final FireProphecyEffect effect) {
        super(effect);
    }

    @Override
    public FireProphecyEffect copy() {
        return new FireProphecyEffect(this);
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
        player.choose(outcome, player.getHand(), target, source, game);
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
