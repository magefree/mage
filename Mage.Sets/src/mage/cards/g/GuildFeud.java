
package mage.cards.g;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public final class GuildFeud extends CardImpl {

    public GuildFeud(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{5}{R}");

        // At the beginning of your upkeep, target opponent reveals the top three cards
        // of their library, may put a creature card from among them onto the battlefield,
        // then puts the rest into their graveyard. You do the same with the top three
        // cards of your library. If two creatures are put onto the battlefield this way,
        // those creatures fight each other.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new GuildFeudEffect(), TargetController.YOU, false);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private GuildFeud(final GuildFeud card) {
        super(card);
    }

    @Override
    public GuildFeud copy() {
        return new GuildFeud(this);
    }
}

class GuildFeudEffect extends OneShotEffect {

    public GuildFeudEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "target opponent reveals the top three cards of their library, may put a creature card from among them onto the battlefield, then puts the rest into their graveyard. You do the same with the top three cards of your library. If two creatures are put onto the battlefield this way, those creatures fight each other";
    }

    public GuildFeudEffect(final GuildFeudEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(targetPointer.getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        Permanent opponentCreature = null;
        Permanent controllerCreature = null;
        MageObject sourceObject = source.getSourceObject(game);
        if (opponent != null && controller != null && sourceObject != null) {
            for (int activePlayer = 0; activePlayer < 2; activePlayer++) {
                Player player = (activePlayer == 0 ? opponent : controller);
                Cards topThreeCards = new CardsImpl(player.getLibrary().getTopCards(game, 3));
                player.revealCards(source, player.getName() + " top library cards", topThreeCards, game);
                Card creatureToBattlefield;
                if (!topThreeCards.isEmpty()) {
                    if (player.chooseUse(Outcome.PutCreatureInPlay, "Put a creature card among them to the battlefield?", source, game)) {
                        TargetCard target = new TargetCard(Zone.LIBRARY,
                                new FilterCreatureCard(
                                        "creature card to put on the battlefield"));
                        if (player.choose(Outcome.PutCreatureInPlay, topThreeCards, target, source, game)) {
                            creatureToBattlefield = topThreeCards.get(target.getFirstTarget(), game);
                            if (creatureToBattlefield != null) {
                                topThreeCards.remove(creatureToBattlefield);
                                if (player.moveCards(creatureToBattlefield, Zone.BATTLEFIELD, source, game)) {
                                    if (activePlayer == 0) {
                                        opponentCreature = game.getPermanent(creatureToBattlefield.getId());
                                    } else {
                                        controllerCreature = game.getPermanent(creatureToBattlefield.getId());
                                    }
                                }
                            }
                        }
                    }
                    player.moveCards(topThreeCards, Zone.GRAVEYARD, source, game);
                }
            }
            // If two creatures are put onto the battlefield this way, those creatures fight each other
            if (opponentCreature != null && controllerCreature != null) {
                opponentCreature.fight(controllerCreature, source, game);
            }
            return true;
        }
        return false;
    }

    @Override
    public GuildFeudEffect copy() {
        return new GuildFeudEffect(this);
    }
}
