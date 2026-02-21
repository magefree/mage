package mage.cards.m;

import java.util.UUID;

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

/**
 *
 * @author muz
 */
public final class ManholeMissile extends CardImpl {

    public ManholeMissile(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Manhole Missile deals 3 damage to target creature. You may put a card from your hand on the bottom of your library. If you do, draw a card.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addEffect(new ManholeMissileEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private ManholeMissile(final ManholeMissile card) {
        super(card);
    }

    @Override
    public ManholeMissile copy() {
        return new ManholeMissile(this);
    }
}

class ManholeMissileEffect extends OneShotEffect {

    ManholeMissileEffect() {
        super(Outcome.Benefit);
        staticText = "you may put a card from your hand on the bottom of your library. If you do, draw a card";
    }

    private ManholeMissileEffect(final ManholeMissileEffect effect) {
        super(effect);
    }

    @Override
    public ManholeMissileEffect copy() {
        return new ManholeMissileEffect(this);
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
        if (player.putCardsOnBottomOfLibrary(card, game, source)) {
            player.drawCards(1, source, game);
        }
        return true;
    }
}
