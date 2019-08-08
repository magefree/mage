package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NightmareUnmaking extends CardImpl {

    public NightmareUnmaking(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{B}");

        // Choose one —
        // • Exile each creature with power greater than the number of cards in your hand.
        this.getSpellAbility().addEffect(new NightmareUnmakingEffect(false));

        // • Exile each creature with power less than the number of cards in your hand.
        this.getSpellAbility().addMode(new Mode(new NightmareUnmakingEffect(true)));
    }

    private NightmareUnmaking(final NightmareUnmaking card) {
        super(card);
    }

    @Override
    public NightmareUnmaking copy() {
        return new NightmareUnmaking(this);
    }
}

class NightmareUnmakingEffect extends OneShotEffect {

    private final ComparisonType comparisonType;

    NightmareUnmakingEffect(boolean lessThan) {
        super(Outcome.Benefit);
        this.comparisonType = lessThan ? ComparisonType.FEWER_THAN : ComparisonType.MORE_THAN;
        staticText = "exile each creature with power "
                + (lessThan ? "less" : "greater")
                + " than the number of cards in your hand";
    }

    private NightmareUnmakingEffect(final NightmareUnmakingEffect effect) {
        super(effect);
        this.comparisonType = effect.comparisonType;
    }

    @Override
    public NightmareUnmakingEffect copy() {
        return new NightmareUnmakingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        FilterPermanent filter = new FilterCreaturePermanent();
        filter.add(new PowerPredicate(comparisonType, player.getHand().size()));
        return new ExileAllEffect(filter).apply(game, source);
    }
}