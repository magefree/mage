package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class PillarTombsOfAku extends CardImpl {

    public PillarTombsOfAku(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{B}");

        this.addSuperType(SuperType.WORLD);

        // At the beginning of each player's upkeep, that player may sacrifice a creature. If that player doesn't, he or she loses 5 life and you sacrifice Pillar Tombs of Aku.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new PillarTombsOfAkuEffect(),
                TargetController.ANY,
                false
        ));
    }

    public PillarTombsOfAku(final PillarTombsOfAku card) {
        super(card);
    }

    @Override
    public PillarTombsOfAku copy() {
        return new PillarTombsOfAku(this);
    }
}

class PillarTombsOfAkuEffect extends OneShotEffect {

    public PillarTombsOfAkuEffect() {
        super(Outcome.Benefit);
        this.staticText = "that player may sacrifice a creature. If that "
                + "player doesn’t, they lose 5 life and you sacrifice {this}";
    }

    public PillarTombsOfAkuEffect(final PillarTombsOfAkuEffect effect) {
        super(effect);
    }

    @Override
    public PillarTombsOfAkuEffect copy() {
        return new PillarTombsOfAkuEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player activePlayer = game.getPlayer(game.getActivePlayerId());
        if (activePlayer == null) {
            return false;
        }
        if (activePlayer.chooseUse(Outcome.Sacrifice, "Sacrifice a creature?", source, game)) {
            Cost cost = new SacrificeTargetCost(new TargetControlledCreaturePermanent());
            if (cost.canPay(source, source.getSourceId(), activePlayer.getId(), game)
                    && cost.pay(source, game, source.getSourceId(), activePlayer.getId(), true)) {
                return true;
            }
        }
        activePlayer.loseLife(5, game, false);
        return new SacrificeSourceEffect().apply(game, source);
    }
}
