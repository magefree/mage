
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.token.SpyMasterGoblinToken;
import mage.game.permanent.token.Token;
import mage.players.Player;

/**
 *
 * @author spjspj
 */
public final class GoblinSpymaster extends CardImpl {

    public GoblinSpymaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // At the beginning of each opponent's end step, that player creates a 1/1 red Goblin creature token with "Creatures you control attack each combat if able."
        this.addAbility(new BeginningOfEndStepTriggeredAbility(Zone.BATTLEFIELD, new SpyMasterGoblinCreateTokenEffect(), TargetController.OPPONENT, null, false));
    }

    private GoblinSpymaster(final GoblinSpymaster card) {
        super(card);
    }

    @Override
    public GoblinSpymaster copy() {
        return new GoblinSpymaster(this);
    }
}

class SpyMasterGoblinCreateTokenEffect extends OneShotEffect {

    public SpyMasterGoblinCreateTokenEffect() {
        super(Outcome.Detriment);
        this.staticText = "that player creates a 1/1 red Goblin creature token with \"Creatures you control attack each combat if able.\"";
    }

    private SpyMasterGoblinCreateTokenEffect(final SpyMasterGoblinCreateTokenEffect effect) {
        super(effect);
    }

    @Override
    public SpyMasterGoblinCreateTokenEffect copy() {
        return new SpyMasterGoblinCreateTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getActivePlayerId());

        if (player != null) {
            Token token = new SpyMasterGoblinToken();
            token.putOntoBattlefield(1, game, source, player.getId());
        }

        return true;
    }
}
