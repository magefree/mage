package mage.cards.t;

import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.ShuffleLibrarySourceEffect;
import java.util.UUID;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author muz
 */
public final class ThroughTheForestGate extends CardImpl {

    public ThroughTheForestGate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{6}{G}{G}");

        // Look at the top twenty cards of your library, put any number of land cards from among them onto the battlefield tapped, then shuffle. You gain 8 life.
        this.getSpellAbility().addEffect(new ThroughTheForestGateEffect());
        this.getSpellAbility().addEffect(new ShuffleLibrarySourceEffect().setText(", then shuffle"));
        this.getSpellAbility().addEffect(new GainLifeEffect(8));
    }

    private ThroughTheForestGate(final ThroughTheForestGate card) {
        super(card);
    }

    @Override
    public ThroughTheForestGate copy() {
        return new ThroughTheForestGate(this);
    }
}

class ThroughTheForestGateEffect extends OneShotEffect {

    ThroughTheForestGateEffect() {
        super(Outcome.PutLandInPlay);
        staticText = "look at the top twenty cards of your library, put any number of land cards from among them onto the battlefield tapped";
    }

    private ThroughTheForestGateEffect(final ThroughTheForestGateEffect effect) {
        super(effect);
    }

    @Override
    public ThroughTheForestGateEffect copy() {
        return new ThroughTheForestGateEffect(this);
    }

    @Override
    public boolean apply(Game game, mage.abilities.Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 20));
        cards.retainZone(Zone.LIBRARY, game);
        if (cards.isEmpty()) {
            return true;
        }

        TargetCard target = new TargetCardInLibrary(0, Integer.MAX_VALUE, StaticFilters.FILTER_CARD_LANDS);
        target.withChooseHint("to put onto the battlefield tapped");
        player.choose(outcome, cards, target, source, game);
        player.moveCards(
            new CardsImpl(target.getTargets()).getCards(game), Zone.BATTLEFIELD, source,
            game, true, false, false, null
        );
        return true;
    }
}
