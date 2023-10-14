package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EcstaticBeauty extends CardImpl {

    public EcstaticBeauty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Exile the top three cards of your library. You may play those cards until end of turn. Put four time counters on each of those cards that has suspend.
        this.getSpellAbility().addEffect(new EcstaticBeautyEffect());

        // Suspend 4--{R}
        this.addAbility(new SuspendAbility(4, new ManaCostsImpl<>("{R}"), this));
    }

    private EcstaticBeauty(final EcstaticBeauty card) {
        super(card);
    }

    @Override
    public EcstaticBeauty copy() {
        return new EcstaticBeauty(this);
    }
}

class EcstaticBeautyEffect extends OneShotEffect {

    EcstaticBeautyEffect() {
        super(Outcome.Benefit);
        staticText = "exile the top three cards of your library. " +
                "You may play those cards until end of turn. " +
                "Put four time counters on each of those cards that has suspend";
    }

    private EcstaticBeautyEffect(final EcstaticBeautyEffect effect) {
        super(effect);
    }

    @Override
    public EcstaticBeautyEffect copy() {
        return new EcstaticBeautyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 3));
        player.moveCards(cards, Zone.EXILED, source, game);
        for (Card card : cards.getCards(game)) {
            CardUtil.makeCardPlayable(game, source, card, Duration.EndOfTurn, false);
            if (card.getAbilities(game).containsClass(SuspendAbility.class)) {
                card.addCounters(CounterType.TIME.createInstance(4), source, game);
            }
        }
        return true;
    }
}
