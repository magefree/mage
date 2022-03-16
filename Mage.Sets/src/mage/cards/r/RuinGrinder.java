package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.MountaincyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RuinGrinder extends CardImpl {

    public RuinGrinder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}{R}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(7);
        this.toughness = new MageInt(4);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // When Ruin Grinder dies, each player may discard their hand and draw seven cards.
        this.addAbility(new DiesSourceTriggeredAbility(new RuinGrinderEffect()));

        // Mountaincycling {2}
        this.addAbility(new MountaincyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private RuinGrinder(final RuinGrinder card) {
        super(card);
    }

    @Override
    public RuinGrinder copy() {
        return new RuinGrinder(this);
    }
}

class RuinGrinderEffect extends OneShotEffect {

    RuinGrinderEffect() {
        super(Outcome.Benefit);
        staticText = "each player may discard their hand and draw seven cards";
    }

    private RuinGrinderEffect(final RuinGrinderEffect effect) {
        super(effect);
    }

    @Override
    public RuinGrinderEffect copy() {
        return new RuinGrinderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Player> wheelers = new ArrayList<>();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null && player.chooseUse(
                    Outcome.DrawCard, "Discard your hand and draw seven?", source, game
            )) {
                game.informPlayers(player.getName() + " chooses to discard their hand and draw seven");
                wheelers.add(player);
            }
        }
        for (Player player : wheelers) {
            player.discard(player.getHand(), false, source, game);
            player.drawCards(7, source, game);
        }
        return true;
    }
}
