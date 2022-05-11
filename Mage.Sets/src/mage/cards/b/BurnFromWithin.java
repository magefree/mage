package mage.cards.b;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.LoseAbilityTargetEffect;
import mage.abilities.effects.common.replacement.DiesReplacementEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class BurnFromWithin extends CardImpl {

    public BurnFromWithin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}");

        // Burn from Within deals X damage to any target. If a creature is dealt damage this way, it loses indestructible until end of turn.
        // If that creature would die this turn, exile it instead.
        this.getSpellAbility().addEffect(new BurnFromWithinEffect());
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private BurnFromWithin(final BurnFromWithin card) {
        super(card);
    }

    @Override
    public BurnFromWithin copy() {
        return new BurnFromWithin(this);
    }
}

class BurnFromWithinEffect extends OneShotEffect {

    public BurnFromWithinEffect() {
        super(Outcome.Benefit);
        this.staticText = "{this} deals X damage to any target. " +
                "If a creature is dealt damage this way, it loses indestructible until end of turn. " +
                "If that creature would die this turn, exile it instead";
    }

    public BurnFromWithinEffect(final BurnFromWithinEffect effect) {
        super(effect);
    }

    @Override
    public BurnFromWithinEffect copy() {
        return new BurnFromWithinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) { return false; }

        int amount = source.getManaCostsToPay().getX();

        // Target is a creature
        Permanent creature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (creature != null) {
            game.addEffect(new DiesReplacementEffect(new MageObjectReference(creature, game), Duration.EndOfTurn), source);
            int damageDealt = creature.damage(amount, source.getSourceId(), source, game, false, true);
            if (damageDealt > 0) {
                ContinuousEffect effect = new LoseAbilityTargetEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn);
                effect.setTargetPointer(new FixedTarget(creature.getId(), game));
                game.addEffect(effect, source);
            }
            return true;
        }

        // Target is a player
        Player targetPlayer = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (targetPlayer != null) {
            targetPlayer.damage(amount, source.getSourceId(), source, game);
            return true;
        }

        // No valid target
        return false;
    }
}
