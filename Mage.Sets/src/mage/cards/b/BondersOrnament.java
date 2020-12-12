package mage.cards.b;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BondersOrnament extends CardImpl {

    public BondersOrnament(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());

        // {4}, {T}: Each player who controls a permanent named Bonder's Ornament draws a card.
        Ability ability = new SimpleActivatedAbility(new BondersOrnamentEffect(), new GenericManaCost(4));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private BondersOrnament(final BondersOrnament card) {
        super(card);
    }

    @Override
    public BondersOrnament copy() {
        return new BondersOrnament(this);
    }
}

class BondersOrnamentEffect extends OneShotEffect {

    BondersOrnamentEffect() {
        super(Outcome.Benefit);
        staticText = "Each player who controls a permanent named Bonder's Ornament draws a card.";
    }

    private BondersOrnamentEffect(final BondersOrnamentEffect effect) {
        super(effect);
    }

    @Override
    public BondersOrnamentEffect copy() {
        return new BondersOrnamentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            if (game.getBattlefield()
                    .getAllActivePermanents(playerId)
                    .stream()
                    .map(MageObject::getName)
                    .noneMatch("Bonder's Ornament"::equals)) {
                continue;
            }
            player.drawCards(1, source, game);
        }
        return true;
    }
}