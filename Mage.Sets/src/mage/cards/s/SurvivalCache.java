
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.ReboundAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Loki
 */
public final class SurvivalCache extends CardImpl {

    public SurvivalCache(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{W}");


        // You gain 2 life. Then if you have more life than an opponent, draw a card.
        this.getSpellAbility().addEffect(new GainLifeEffect(2));
        this.getSpellAbility().addEffect(new SurvivalCacheEffect());
        this.addAbility(new ReboundAbility());
    }

    private SurvivalCache(final SurvivalCache card) {
        super(card);
    }

    @Override
    public SurvivalCache copy() {
        return new SurvivalCache(this);
    }
}

class SurvivalCacheEffect extends OneShotEffect {
    SurvivalCacheEffect() {
        super(Outcome.DrawCard);
        staticText = "Then if you have more life than an opponent, draw a card";
    }

    private SurvivalCacheEffect(final SurvivalCacheEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player sourcePlayer = game.getPlayer(source.getControllerId());
        if (sourcePlayer != null) {
            boolean haveMoreLife = false;
            for (UUID id : game.getOpponents(source.getControllerId())) {
                Player opponent = game.getPlayer(id);
                if (opponent != null && opponent.getLife() < sourcePlayer.getLife()) {
                    haveMoreLife = true;
                    break;
                }
            }
            if (haveMoreLife)
                sourcePlayer.drawCards(1, source, game);
        }
        return false;
    }

    @Override
    public SurvivalCacheEffect copy() {
        return new SurvivalCacheEffect(this);
    }
}