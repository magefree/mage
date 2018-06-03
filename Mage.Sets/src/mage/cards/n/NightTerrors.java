
package mage.cards.n;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterNonlandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;

/**
 *
 * @author North
 */
public final class NightTerrors extends CardImpl {

    public NightTerrors(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}");


        // Target player reveals their hand. You choose a nonland card from it. Exile that card.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new NightTerrorsEffect());
    }

    public NightTerrors(final NightTerrors card) {
        super(card);
    }

    @Override
    public NightTerrors copy() {
        return new NightTerrors(this);
    }
}

class NightTerrorsEffect extends OneShotEffect {

    public NightTerrorsEffect() {
        super(Outcome.Exile);
        this.staticText = "Target player reveals their hand. You choose a nonland card from it. Exile that card";
    }

    public NightTerrorsEffect(final NightTerrorsEffect effect) {
        super(effect);
    }

    @Override
    public NightTerrorsEffect copy() {
        return new NightTerrorsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (player != null && targetPlayer != null) {
            targetPlayer.revealCards("Night Terrors", targetPlayer.getHand(), game);

            TargetCard target = new TargetCard(Zone.HAND, new FilterNonlandCard("nonland card to exile"));
            if (player.choose(Outcome.Exile, targetPlayer.getHand(), target, game)) {
                Card card = targetPlayer.getHand().get(target.getFirstTarget(), game);
                if (card != null) {
                    card.moveToExile(null, "", source.getSourceId(), game);
                }
            }

            return true;
        }
        return false;
    }
}
