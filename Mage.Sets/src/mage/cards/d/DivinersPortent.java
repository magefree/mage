package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.RollDieWithResultTableEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DivinersPortent extends CardImpl {

    public DivinersPortent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{U}{U}{U}");

        // Roll a d20 and add the number of cards in your hand.
        RollDieWithResultTableEffect effect = new RollDieWithResultTableEffect(
                20, "roll a d20 and add the number " +
                "of cards in your hand", CardsInControllerHandCount.instance, 0
        );
        this.getSpellAbility().addEffect(effect);

        // 1-14 | Draw X cards.
        effect.addTableEntry(1, 14, new DrawCardSourceControllerEffect(ManacostVariableValue.REGULAR));

        // 15+ | Scry X, then draw X cards.
        effect.addTableEntry(15, Integer.MAX_VALUE, new DivinersPortentEffect());
    }

    private DivinersPortent(final DivinersPortent card) {
        super(card);
    }

    @Override
    public DivinersPortent copy() {
        return new DivinersPortent(this);
    }
}

class DivinersPortentEffect extends OneShotEffect {

    DivinersPortentEffect() {
        super(Outcome.Benefit);
        staticText = "scry X, then draw X cards";
    }

    private DivinersPortentEffect(final DivinersPortentEffect effect) {
        super(effect);
    }

    @Override
    public DivinersPortentEffect copy() {
        return new DivinersPortentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        int xValue = source.getManaCostsToPay().getX();
        if (player == null || xValue < 1) {
            return false;
        }
        player.scry(xValue, source, game);
        player.drawCards(xValue, source, game);
        return true;
    }
}
