package mage.abilities.effects.common;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetAmount;

import java.util.*;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class DamageMultiEffect extends OneShotEffect {

    private String sourceName = "{this}";
    private final Set<MageObjectReference> damagedSet = new HashSet<>();

    public DamageMultiEffect() {
        super(Outcome.Damage);
    }

    public DamageMultiEffect(String whoDealDamageName) {
        super(Outcome.Damage);
        this.sourceName = whoDealDamageName;
    }

    protected DamageMultiEffect(final DamageMultiEffect effect) {
        super(effect);
        this.damagedSet.addAll(effect.damagedSet);
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
                if (permanent.damage(multiTarget.getTargetAmount(target), source.getSourceId(), source, game, false, true) > 0) {
                    damagedSet.add(new MageObjectReference(permanent, game));
                }
            } else {
                Player player = game.getPlayer(target);
                if (player != null) {
                    player.damage(multiTarget.getTargetAmount(target), source.getSourceId(), source, game);
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
        StringBuilder sb = new StringBuilder(sourceName);
        sb.append(" deals ");

        Target target = mode.getTargets().get(0);
        if (!(target instanceof TargetAmount)) {
            throw new IllegalStateException("Must use TargetAmount");
        }
        TargetAmount targetAmount = (TargetAmount) target;

        DynamicValue amount = targetAmount.getAmount();
        sb.append(amount.toString());
        sb.append(" damage divided as you choose among ");
        sb.append(targetAmount.getDescription());

        return sb.toString();
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
