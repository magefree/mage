package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author North
 */
public final class NightTerrors extends CardImpl {

    public NightTerrors(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Target player reveals their hand. You choose a nonland card from it. Exile that card.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new NightTerrorsEffect());
    }

    private NightTerrors(final NightTerrors card) {
        super(card);
    }

    @Override
    public NightTerrors copy() {
        return new NightTerrors(this);
    }
}

class NightTerrorsEffect extends OneShotEffect {

    NightTerrorsEffect() {
        super(Outcome.Exile);
        this.staticText = "Target player reveals their hand. You choose a nonland card from it. Exile that card";
    }

    private NightTerrorsEffect(final NightTerrorsEffect effect) {
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
        if (player == null || targetPlayer == null) {
            return false;
        }
        targetPlayer.revealCards(source, targetPlayer.getHand(), game);

        TargetCard target = new TargetCard(Zone.HAND, StaticFilters.FILTER_CARD_NON_LAND);
        if (!player.choose(Outcome.Exile, targetPlayer.getHand(), target, source, game)) {
            return true;
        }
        Card card = targetPlayer.getHand().get(target.getFirstTarget(), game);
        return card != null && player.moveCards(card, Zone.EXILED, source, game);
    }
}
