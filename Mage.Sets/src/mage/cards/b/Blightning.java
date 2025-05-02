package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
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
import mage.target.common.TargetPlayerOrPlaneswalker;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class Blightning extends CardImpl {

    public Blightning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}{R}");

        // Blightning deals 3 damage to target player. That player discards two cards.
        this.getSpellAbility().addTarget(new TargetPlayerOrPlaneswalker());
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addEffect(new BlightningEffect());
    }

    private Blightning(final Blightning card) {
        super(card);
    }

    @Override
    public Blightning copy() {
        return new Blightning(this);
    }
}

class BlightningEffect extends OneShotEffect {

    BlightningEffect() {
        super(Outcome.Detriment);
        this.staticText = "That player or that planeswalker's controller discards two cards.";
    }

    private BlightningEffect(final BlightningEffect effect) {
        super(effect);
    }

    @Override
    public BlightningEffect copy() {
        return new BlightningEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayerOrPlaneswalkerController(source.getFirstTarget());
        if (player == null) {
            return false;
        }
        Effect effect = new DiscardTargetEffect(2);
        effect.setTargetPointer(new FixedTarget(player.getId(), game));
        return effect.apply(game, source);
    }
}
