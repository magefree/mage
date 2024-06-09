package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ObekaBruteChronologist extends CardImpl {

    public ObekaBruteChronologist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // {T}: The player whose turn it is may end the turn.
        this.addAbility(new SimpleActivatedAbility(new ObekaBruteChronologistEffect(), new TapSourceCost()));
    }

    private ObekaBruteChronologist(final ObekaBruteChronologist card) {
        super(card);
    }

    @Override
    public ObekaBruteChronologist copy() {
        return new ObekaBruteChronologist(this);
    }
}

class ObekaBruteChronologistEffect extends OneShotEffect {

    ObekaBruteChronologistEffect() {
        super(Outcome.Benefit);
        staticText = "the player whose turn it is may end the turn";
    }

    private ObekaBruteChronologistEffect(final ObekaBruteChronologistEffect effect) {
        super(effect);
    }

    @Override
    public ObekaBruteChronologistEffect copy() {
        return new ObekaBruteChronologistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getActivePlayerId());
        return player != null
                && player.chooseUse(outcome, "End the turn?", source, game)
                && game.endTurn(source);
    }
}
