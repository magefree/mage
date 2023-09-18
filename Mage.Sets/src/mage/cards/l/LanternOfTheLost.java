package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LanternOfTheLost extends CardImpl {

    public LanternOfTheLost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // When Lantern of the Lost enters the battlefield, exile target card from a graveyard.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileTargetEffect());
        ability.addTarget(new TargetCardInGraveyard());
        this.addAbility(ability);

        // {1}, {T}, Exile Lantern of the Lost: Exile all cards from all graveyards, then draw a card.
        ability = new SimpleActivatedAbility(new LanternOfTheLostEffect(), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addCost(new ExileSourceCost());
        this.addAbility(ability);
    }

    private LanternOfTheLost(final LanternOfTheLost card) {
        super(card);
    }

    @Override
    public LanternOfTheLost copy() {
        return new LanternOfTheLost(this);
    }
}

class LanternOfTheLostEffect extends OneShotEffect {

    LanternOfTheLostEffect() {
        super(Outcome.Benefit);
        staticText = "exile all cards from all graveyards, then draw a card";
    }

    private LanternOfTheLostEffect(final LanternOfTheLostEffect effect) {
        super(effect);
    }

    @Override
    public LanternOfTheLostEffect copy() {
        return new LanternOfTheLostEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        game.getState()
                .getPlayersInRange(source.getControllerId(), game)
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .map(Player::getGraveyard)
                .forEach(cards::addAll);
        player.moveCards(cards, Zone.EXILED, source, game);
        player.drawCards(1, source, game);
        return true;
    }
}
