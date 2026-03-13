package mage.cards.s;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.MutagenToken;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.ForEachPlayerTargetsAdjuster;
import mage.target.targetpointer.EachTargetPointer;
import mage.watchers.common.DamagedByWatcher;

/**
 *
 * @author muz
 */
public final class Shellshock extends CardImpl {

    public Shellshock(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{R}");

        // For each opponent, choose up to one target creature that player controls. Shellshock deals X damage to each of those creatures. You create a Mutagen token for each creature dealt damage this way.
        this.getSpellAbility().addEffect(new ShellshockEffect()
            .setTargetPointer(new EachTargetPointer()));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 1));
        this.getSpellAbility().setTargetAdjuster(new ForEachPlayerTargetsAdjuster(false, true));
    }

    private Shellshock(final Shellshock card) {
        super(card);
    }

    @Override
    public Shellshock copy() {
        return new Shellshock(this);
    }
}

class ShellshockEffect extends OneShotEffect {

    ShellshockEffect() {
        super(Outcome.Damage);
        staticText = "for each opponent, choose up to one target creature that player controls."
             + " Shellshock deals X damage to each of those creatures."
             + " You create a Mutagen token for each creature dealt damage this way.";
    }

    public ShellshockEffect(final ShellshockEffect effect) {
        super(effect);
    }

    @Override
    public ShellshockEffect copy() {
        return new ShellshockEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID permanentId : getTargetPointer().getTargets(game, source)) {
            Permanent permanent = game.getPermanent(permanentId);
            if (permanent == null) {
                continue;
            }
            int damage = GetXValue.instance.calculate(game, source, this);
            if (damage > 0) {
                permanent.damage(damage, source.getSourceId(), source, game);

                DamagedByWatcher watcher = game.getState().getWatcher(DamagedByWatcher.class, source.getSourceId());
                if (watcher != null && watcher.wasDamaged(permanentId, game)) {
                    new MutagenToken().putOntoBattlefield(1, game, source);
                }
            }
        }
        return true;
    }
}
