
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author Ketsuban
 */
public final class MissDemeanor extends CardImpl {

    public MissDemeanor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        
        this.subtype.add(SubType.LADYOFPROPERETIQUETTE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // At the beginning of each other player's upkeep, you may compliment that player on their game play. If you don't, sacrifice Miss Demeanour.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new MissDemeanorEffect(), TargetController.NOT_YOU, false, true));
    }

    private MissDemeanor(final MissDemeanor card) {
        super(card);
    }

    @Override
    public MissDemeanor copy() {
        return new MissDemeanor(this);
    }
}

class MissDemeanorEffect extends OneShotEffect {

    public MissDemeanorEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "you may compliment that player on their game play. If you don't, sacrifice {this}";
    }

    public MissDemeanorEffect(final MissDemeanorEffect effect) {
        super(effect);
    }

    @Override
    public MissDemeanorEffect copy() {
        return new MissDemeanorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourceObject = source.getSourcePermanentIfItStillExists(game);
        String activePlayerName = game.getPlayer(game.getActivePlayerId()).getName();
        if (sourceObject != null) {
            if (controller.chooseUse(outcome, "Compliment " + activePlayerName + " on their game play?", source, game)) {
                // TODO(Ketsuban): this could probably stand to be randomly chosen from a pool of compliments
                game.informPlayers(controller.getLogName() + ": That's a well-built deck and you pilot it well, " + activePlayerName + ".");
            } else {
                sourceObject.sacrifice(source, game);
            }
            return true;
        }
        return false;
    }
}
