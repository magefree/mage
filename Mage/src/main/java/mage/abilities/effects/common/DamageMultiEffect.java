package mage.abilities.effects.common;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class DamageMultiEffect extends OneShotEffect {

    protected DynamicValue amount;
    private String sourceName = "{source}";
    private final Set<MageObjectReference> damagedSet = new HashSet<>();

    public DamageMultiEffect(int amount) {
        this(new StaticValue(amount));
    }

    public DamageMultiEffect(int amount, String whoDealDamageName) {
        this(new StaticValue(amount));
        this.sourceName = whoDealDamageName;
    }

    public DamageMultiEffect(DynamicValue amount) {
        super(Outcome.Damage);
        this.amount = amount;
    }

    public DamageMultiEffect(final DamageMultiEffect effect) {
        super(effect);
        this.damagedSet.addAll(effect.damagedSet);
        this.amount = effect.amount;
        this.sourceName = effect.sourceName;
    }

    @Override
    public DamageMultiEffect copy() {
        return new DamageMultiEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        this.damagedSet.clear();
        if (source.getTargets().isEmpty()) {
            return true;
        }
        Target multiTarget = source.getTargets().get(0);
        for (UUID target : multiTarget.getTargets()) {
            Permanent permanent = game.getPermanent(target);
            if (permanent != null) {
                if (permanent.damage(multiTarget.getTargetAmount(target), source.getSourceId(), game, false, true) > 0) {
                    damagedSet.add(new MageObjectReference(permanent, game));
                } ;
            } else {
                Player player = game.getPlayer(target);
                if (player != null) {
                    player.damage(multiTarget.getTargetAmount(target), source.getSourceId(), game, false, true);
                }
            }
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        if (amount.toString().equals("3")) {
            return this.sourceName + " deals 3 damage divided as you choose among one, two, or three targets";
        }
        return this.sourceName + " deals " + amount.toString() + " damage divided as you choose among any number of " + mode.getTargets().get(0).getTargetName();
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public Set<MageObjectReference> getDamagedSet() {
        return damagedSet;
    }
}
