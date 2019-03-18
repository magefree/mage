
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByAllSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author L_J
 */
public final class SaprazzanBreaker extends CardImpl {

    public SaprazzanBreaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {U}: Put the top card of your library into your graveyard. If that card is a land card, Saprazzan Breaker can't be blocked this turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new SaprazzanBreakerEffect(), new ManaCostsImpl("{U}")));
    }

    public SaprazzanBreaker(final SaprazzanBreaker card) {
        super(card);
    }

    @Override
    public SaprazzanBreaker copy() {
        return new SaprazzanBreaker(this);
    }
}

class SaprazzanBreakerEffect extends OneShotEffect {

    public SaprazzanBreakerEffect() {
        super(Outcome.Benefit);
        this.staticText = "Put the top card of your library into your graveyard. If that card is a land card, {this} can't be blocked this turn";
    }

    public SaprazzanBreakerEffect(final SaprazzanBreakerEffect effect) {
        super(effect);
    }

    @Override
    public SaprazzanBreakerEffect copy() {
        return new SaprazzanBreakerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Card card = player.getLibrary().getFromTop(game);
            if (card != null) {
                player.moveCards(card, Zone.GRAVEYARD, source, game);
                if (card.isLand()) {
                    game.addEffect(new CantBeBlockedByAllSourceEffect(StaticFilters.FILTER_PERMANENT_CREATURES, Duration.EndOfTurn), source);
                }
            }
            return true;
        }
        return false;
    }
}
