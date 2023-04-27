
package mage.cards.w;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ShuffleHandIntoLibraryDrawThatManySourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class WhirlpoolWarrior extends CardImpl {

    public WhirlpoolWarrior(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Whirlpool Warrior enters the battlefield, shuffle the cards from your hand into your library, then draw that many cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ShuffleHandIntoLibraryDrawThatManySourceEffect()));

        // {R}, Sacrifice Whirlpool Warrior: Each player shuffles the cards from their hand into their library, then draws that many cards.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new WhirlpoolWarriorActivatedEffect(), new ManaCostsImpl<>("{R}"));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private WhirlpoolWarrior(final WhirlpoolWarrior card) {
        super(card);
    }

    @Override
    public WhirlpoolWarrior copy() {
        return new WhirlpoolWarrior(this);
    }
}

class WhirlpoolWarriorActivatedEffect extends OneShotEffect {

    public WhirlpoolWarriorActivatedEffect() {
        super(Outcome.Benefit);
        this.staticText = "Each player shuffles the cards from their hand into their library, then draws that many cards";
    }

    public WhirlpoolWarriorActivatedEffect(final WhirlpoolWarriorActivatedEffect effect) {
        super(effect);
    }

    @Override
    public WhirlpoolWarriorActivatedEffect copy() {
        return new WhirlpoolWarriorActivatedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Map<UUID, Integer> playerCards = new LinkedHashMap<>();
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    int cardsHand = player.getHand().size();
                    if (cardsHand > 0) {
                        playerCards.put(playerId, cardsHand);
                        player.moveCards(player.getHand(), Zone.LIBRARY, source, game);
                        player.shuffleLibrary(source, game);
                    }
                }
            }
            for (Entry<UUID, Integer> entry : playerCards.entrySet()) {
                Player player = game.getPlayer(entry.getKey());
                if (player != null) {
                    player.drawCards(entry.getValue(), source, game);
                }
            }
            return true;
        }
        return false;
    }
}
