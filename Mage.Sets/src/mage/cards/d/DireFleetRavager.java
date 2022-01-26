
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.constants.SubType;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public final class DireFleetRavager extends CardImpl {

    public DireFleetRavager(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.PIRATE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // When Dire Fleet Ravager enters the battlefield, each player loses a third of their life, rounded up.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DireFleetRavagerEffect()));
    }

    private DireFleetRavager(final DireFleetRavager card) {
        super(card);
    }

    @Override
    public DireFleetRavager copy() {
        return new DireFleetRavager(this);
    }
}

class DireFleetRavagerEffect extends OneShotEffect {

    DireFleetRavagerEffect() {
        super(Outcome.Detriment);
        this.staticText = "each player loses a third of their life, rounded up";
    }

    DireFleetRavagerEffect(final DireFleetRavagerEffect effect) {
        super(effect);
    }

    @Override
    public DireFleetRavagerEffect copy() {
        return new DireFleetRavagerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    int lifeToLose = (int) Math.ceil(player.getLife() / 3.0);
                    player.loseLife(lifeToLose, game, source, false);
                }
            }
            return true;
        }
        return false;
    }
}
