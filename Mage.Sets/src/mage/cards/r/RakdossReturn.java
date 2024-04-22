
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponentOrPlaneswalker;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class RakdossReturn extends CardImpl {

    public RakdossReturn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{B}{R}");

        // Rakdos's Return deals X damage to target opponent or planeswalker. That player or that planeswalker’s controller discards X cards.
        this.getSpellAbility().addEffect(new DamageTargetEffect(ManacostVariableValue.REGULAR));
        this.getSpellAbility().addEffect(new RakdossReturnEffect());
        this.getSpellAbility().addTarget(new TargetOpponentOrPlaneswalker());
    }

    private RakdossReturn(final RakdossReturn card) {
        super(card);
    }

    @Override
    public RakdossReturn copy() {
        return new RakdossReturn(this);
    }
}

class RakdossReturnEffect extends OneShotEffect {

    RakdossReturnEffect() {
        super(Outcome.Benefit);
        this.staticText = "That player or that planeswalker's controller discards X cards.";
    }

    private RakdossReturnEffect(final RakdossReturnEffect effect) {
        super(effect);
    }

    @Override
    public RakdossReturnEffect copy() {
        return new RakdossReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayerOrPlaneswalkerController(source.getFirstTarget());
        if (player == null) {
            return false;
        }
        Effect effect = new DiscardTargetEffect(ManacostVariableValue.REGULAR);
        effect.setTargetPointer(new FixedTarget(player.getId(), game));
        return effect.apply(game, source);
    }
}
