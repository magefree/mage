package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;

import java.util.UUID;

/**
 * @author azra1l <algee2005@gmail.com>
 */
public final class AeonEngine extends CardImpl {
    public AeonEngine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        // Aeon Engine enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}, Exile Aeon Engine: Reverse the gameâs turn order. (For example, if play had proceeded clockwise around the table, it now goes counterclockwise.)
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AeonEngineEffect(), new TapSourceCost());
        ability.addCost(new ExileSourceCost());
        this.addAbility(ability);
    }

    private AeonEngine(final AeonEngine card) {
        super(card);
    }

    @Override
    public AeonEngine copy() {
        return new AeonEngine(this);
    }
}

class AeonEngineEffect extends OneShotEffect {

    AeonEngineEffect() {
        super(Outcome.Benefit);
        this.staticText = "Reverse the game's turn order.";
    }

    private AeonEngineEffect(final AeonEngineEffect effect) {
        super(effect);
    }

    public AeonEngineEffect copy() {
        return new AeonEngineEffect(this);
    }

    public boolean apply(Game game, Ability source) {
        game.getState().setReverseTurnOrder(true);
        return true;

    }
}
