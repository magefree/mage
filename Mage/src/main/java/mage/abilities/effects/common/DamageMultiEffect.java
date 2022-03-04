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

import java.util.*;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class DamageMultiEffect extends OneShotEffect {

    protected DynamicValue amount;
    private String sourceName = "{this}";
    private final Set<MageObjectReference> damagedSet = new HashSet<>();

    public DamageMultiEffect(int amount) {
        this(StaticValue.get(amount));
    }

    public DamageMultiEffect(int amount, String whoDealDamageName) {
        this(StaticValue.get(amount));
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

        String amountString = amount.toString();
        sb.append(amountString);
        sb.append(" damage divided as you choose among ");
        sb.append(amountString.equals("2") ? "one or two " : amountString.equals("3") ? "one, two, or three " : "any number of ");

        String targetName = mode.getTargets().get(0).getTargetName();
        if (!targetName.contains("target")) {
            sb.append("target ");
        }
        sb.append(targetName);

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
