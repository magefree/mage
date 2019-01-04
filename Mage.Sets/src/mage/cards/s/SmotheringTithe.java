package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.DrawCardOpponentTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SmotheringTithe extends CardImpl {

    public SmotheringTithe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        // Whenever an opponent draws a card, that player may pay {2}. If the player doesn't, you create a colorless Treasure artifact token with "{T}, Sacrifice this artifact: Add one mana of any color."
        this.addAbility(new DrawCardOpponentTriggeredAbility(
                new SmotheringTitheEffect(), false, true
        ));
    }

    private SmotheringTithe(final SmotheringTithe card) {
        super(card);
    }

    @Override
    public SmotheringTithe copy() {
        return new SmotheringTithe(this);
    }
}

class SmotheringTitheEffect extends OneShotEffect {

    SmotheringTitheEffect() {
        super(Outcome.Benefit);
        staticText = "that player may pay {2}. If the player doesn't, " +
                "you create a colorless Treasure artifact token " +
                "with \"{T}, Sacrifice this artifact: Add one mana of any color.\"";
    }

    private SmotheringTitheEffect(final SmotheringTitheEffect effect) {
        super(effect);
    }

    @Override
    public SmotheringTitheEffect copy() {
        return new SmotheringTitheEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player == null) {
            return false;
        }
        Cost cost = new GenericManaCost(2);
        if (!player.chooseUse(Outcome.Detriment, "Pay {2} to prevent this effect?", source, game)
                || !cost.pay(source, game, source.getSourceId(), player.getId(), false)) {
            return new CreateTokenEffect(new TreasureToken()).apply(game, source);
        }
        return false;
    }
}