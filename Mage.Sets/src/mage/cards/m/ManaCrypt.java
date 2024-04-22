package mage.cards.m;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ManaCrypt extends CardImpl {

    public ManaCrypt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{0}");

        // At the beginning of your upkeep, flip a coin. If you lose the flip, Mana Crypt deals 3 damage to you.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new ManaCryptEffect(), TargetController.YOU, false));

        // {T}: Add {C}{C}.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.ColorlessMana(2), new TapSourceCost()));
    }

    private ManaCrypt(final ManaCrypt card) {
        super(card);
    }

    @Override
    public ManaCrypt copy() {
        return new ManaCrypt(this);
    }
}

class ManaCryptEffect extends OneShotEffect {

    ManaCryptEffect() {
        super(Outcome.Damage);
        staticText = "flip a coin. If you lose the flip, {this} deals 3 damage to you";
    }

    private ManaCryptEffect(final ManaCryptEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            if (!player.flipCoin(source, game, true)) {
                player.damage(3, source.getSourceId(), source, game);
            }
            return true;
        }
        return false;
    }

    @Override
    public ManaCryptEffect copy() {
        return new ManaCryptEffect(this);
    }
}
