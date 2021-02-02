package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.token.DjinnToken;
import mage.players.Player;

import java.util.UUID;

/**
 * @author KholdFuzion
 */
public final class BottleOfSuleiman extends CardImpl {

    public BottleOfSuleiman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // {1}, Sacrifice Bottle of Suleiman: Flip a coin. If you lose the flip, Bottle of Suleiman deals 5 damage to you. If you win the flip, create a 5/5 colorless Djinn artifact creature token with flying.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BottleOfSuleimanEffect(), new GenericManaCost(1));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private BottleOfSuleiman(final BottleOfSuleiman card) {
        super(card);
    }

    @Override
    public BottleOfSuleiman copy() {
        return new BottleOfSuleiman(this);
    }
}

class BottleOfSuleimanEffect extends OneShotEffect {

    public BottleOfSuleimanEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Flip a coin. If you lose the flip, {this} deals 5 damage to you. If you win the flip, create a 5/5 colorless Djinn artifact creature token with flying.";
    }

    public BottleOfSuleimanEffect(final BottleOfSuleimanEffect effect) {
        super(effect);
    }

    @Override
    public BottleOfSuleimanEffect copy() {
        return new BottleOfSuleimanEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        if (you != null) {
            if (you.flipCoin(source, game, true)) {
                DjinnToken token = new DjinnToken();
                token.putOntoBattlefield(1, game, source, source.getControllerId());
                return true;
            } else {
                you.damage(5, source.getSourceId(), source, game);
                return true;
            }

        }
        return false;
    }
}
