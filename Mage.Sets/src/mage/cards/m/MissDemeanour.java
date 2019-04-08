
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
public final class MissDemeanour extends CardImpl {

    public MissDemeanour(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        
        this.subtype.add(SubType.LADYOFPROPERETIQUETTE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // At the beginning of each other player's upkeep, you may compliment that player on their game play. If you don't, sacrifice Miss Demeanour.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new MissDemeanourEffect(), TargetController.NOT_YOU, false, true));
    }

    public MissDemeanour(final MissDemeanour card) {
        super(card);
    }

    @Override
    public MissDemeanour copy() {
        return new MissDemeanour(this);
    }
}

class MissDemeanourEffect extends OneShotEffect {

    public MissDemeanourEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "you may compliment that player on their game play. If you don't, sacrifice {this}";
    }

    public MissDemeanourEffect(final MissDemeanourEffect effect) {
        super(effect);
    }

    @Override
    public MissDemeanourEffect copy() {
        return new MissDemeanourEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourceObject = (Permanent) source.getSourceObjectIfItStillExists(game);
        if (sourceObject != null) {
            if (!controller.chooseUse(outcome, "Compliment " + game.getPlayer(game.getActivePlayerId()).getName() + " on their game play?", source, game)) {
                sourceObject.sacrifice(source.getSourceId(), game);
            }
            return true;
        }
        return false;
    }
}
