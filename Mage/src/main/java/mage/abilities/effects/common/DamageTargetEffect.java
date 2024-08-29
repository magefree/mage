package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 * @author North
 */
public class DamageTargetEffect extends OneShotEffect {

    protected DynamicValue amount;
    protected DynamicValue.EffectPhrasing phrasing;
    protected boolean preventable;
    protected String targetDescription;
    protected boolean useOnlyTargetPointer; // TODO: investigate why do we ignore targetPointer by default??
    protected String sourceName = "{this}";

    public DamageTargetEffect(int amount) {
        this(StaticValue.get(amount), true);
    }

    public DamageTargetEffect(int amount, String whoDealDamageName) {
        this(StaticValue.get(amount), true);
        this.sourceName = whoDealDamageName;
    }

    public DamageTargetEffect(int amount, boolean preventable) {
        this(StaticValue.get(amount), preventable);
    }

    public DamageTargetEffect(int amount, boolean preventable, String targetDescription) {
        this(StaticValue.get(amount), preventable, targetDescription);
    }

    public DamageTargetEffect(int amount, boolean preventable, String targetDescription, boolean useOnlyTargetPointer) {
        this(StaticValue.get(amount), preventable, targetDescription, useOnlyTargetPointer);
    }

    public DamageTargetEffect(int amount, boolean preventable, String targetDescription, String whoDealDamageName) {
        this(StaticValue.get(amount), preventable, targetDescription);
        this.sourceName = whoDealDamageName;
    }

    public DamageTargetEffect(DynamicValue amount) {
        this(amount, true);
    }

    public DamageTargetEffect(DynamicValue amount, String whoDealDamageName) {
        this(amount, true);
        this.sourceName = whoDealDamageName;
    }

    public DamageTargetEffect(DynamicValue amount, boolean preventable) {
        this(amount, preventable, "");
    }

    public DamageTargetEffect(DynamicValue amount, boolean preventable, String targetDescription) {
        this(amount, preventable, targetDescription, false, DynamicValue.EffectPhrasing.EQUAL_TO);
    }

    public DamageTargetEffect(DynamicValue amount, boolean preventable, String targetDescription, boolean useOnlyTargetPointer) {
        this(amount, preventable, targetDescription, useOnlyTargetPointer, DynamicValue.EffectPhrasing.EQUAL_TO);
    }

    public DamageTargetEffect(DynamicValue amount, boolean preventable, String targetDescription, boolean useOnlyTargetPointer, DynamicValue.EffectPhrasing phrasing) {
        super(Outcome.Damage);
        this.amount = amount;
        this.preventable = preventable;
        this.targetDescription = targetDescription;
        this.useOnlyTargetPointer = useOnlyTargetPointer;
        this.phrasing = phrasing;
    }

    public int getAmount() {
        if (amount instanceof StaticValue) {
            return amount.calculate(null, null, this);
        } else {
            return 0;
        }
    }

    public void setAmount(DynamicValue amount) {
        this.amount = amount;
    }

    protected DamageTargetEffect(final DamageTargetEffect effect) {
        super(effect);
        this.amount = effect.amount.copy();
        this.preventable = effect.preventable;
        this.targetDescription = effect.targetDescription;
        this.useOnlyTargetPointer = effect.useOnlyTargetPointer;
        this.sourceName = effect.sourceName;
        this.phrasing = effect.phrasing;
    }

    public DamageTargetEffect withTargetDescription(String targetDescription) {
        this.targetDescription = targetDescription;
        return this;
    }

    public DamageTargetEffect withPhrasing(DynamicValue.EffectPhrasing phrasing) {
        this.phrasing = phrasing;
        return this;
    }

    // TODO: this should most likely be refactored to not be needed and always use target pointer.
    public Effect setUseOnlyTargetPointer(boolean useOnlyTargetPointer) {
        this.useOnlyTargetPointer = useOnlyTargetPointer;
        return this;
    }

    @Override
    public DamageTargetEffect copy() {
        return new DamageTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (!useOnlyTargetPointer && source.getTargets().size() > 1) {
            for (Target target : source.getTargets()) {
                for (UUID targetId : target.getTargets()) {
                    Permanent permanent = game.getPermanent(targetId);
                    if (permanent != null) {
                        permanent.damage(amount.calculate(game, source, this), source.getSourceId(), source, game, false, preventable);
                    }
                    Player player = game.getPlayer(targetId);
                    if (player != null) {
                        player.damage(amount.calculate(game, source, this), source.getSourceId(), source, game, false, preventable);
                    }
                }
            }
            return true;
        }
        for (UUID targetId : this.getTargetPointer().getTargets(game, source)) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent != null) {
                permanent.damage(amount.calculate(game, source, this), source.getSourceId(), source, game, false, preventable);
            } else {
                Player player = game.getPlayer(targetId);
                if (player != null) {
                    player.damage(amount.calculate(game, source, this), source.getSourceId(), source, game, false, preventable);
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
        StringBuilder sb = new StringBuilder();
        sb.append(this.sourceName).append(" deals ");
        if (amount instanceof StaticValue) {
            sb.append(((StaticValue)amount).getValue());
        } else if (phrasing == DynamicValue.EffectPhrasing.X_IS || phrasing == DynamicValue.EffectPhrasing.X_HIDDEN) {
            sb.append("X");
        } else if (phrasing == DynamicValue.EffectPhrasing.EQUAL_TO) {
            // do nothing
        } else if (amount instanceof MultipliedValue) {
            sb.append(((MultipliedValue)amount).getMultiplierText());
        }
        if (!sb.toString().endsWith(" ")) {
            sb.append(' ');
        }
        sb.append("damage to ");
        if (!targetDescription.isEmpty()) {
            sb.append(targetDescription);
        } else {
            if (!mode.getTargets().isEmpty()) {
                Target firstTarget = mode.getTargets().get(0);
                String targetName = firstTarget.getTargetName();
                if (targetName.contains("any")) {
                    sb.append(targetName);
                } else {
                    if (firstTarget.getMinNumberOfTargets() == 0) {
                        int maxTargets = firstTarget.getMaxNumberOfTargets();
                        if (maxTargets == Integer.MAX_VALUE) {
                            sb.append("any number of ");
                        } else {
                            sb.append("up to ");
                            sb.append(CardUtil.numberToText(maxTargets));
                            sb.append(' ');
                        }
                    }
                    if (!targetName.contains("target ")) {
                        sb.append("target ");
                    }
                    sb.append(targetName);
                }
            } else {
                sb.append("that target");
            }
        }
        if (!(amount instanceof StaticValue)) {
            switch (phrasing) {
                case X_IS:
                    sb.append(", where X is ");
                    break;
                case X_HIDDEN:
                    // No additional text
                    break;
                case EQUAL_TO:
                    sb.append(" equal to ");
                    break;
                case FOR_EACH:
                    sb.append(" for each ");
                    break;
                default:
                    throw new IllegalArgumentException("DynamicValue.EffectPhrasing enum not implemented: " + phrasing);
            }
            sb.append(amount.getMessage(phrasing));
        }
        if (!preventable) {
            sb.append(". The damage can't be prevented");
        }
        return sb.toString();
    }
}
