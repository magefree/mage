package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.ValuePhrasing;
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
    protected ValuePhrasing textPhrasing = ValuePhrasing.LEGACY;
    protected boolean rulesTextTargetFirst = false;
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
        this(amount, preventable, targetDescription, false);
    }

    public DamageTargetEffect(DynamicValue amount, boolean preventable, String targetDescription, boolean useOnlyTargetPointer) {
        super(Outcome.Damage);
        this.amount = amount;
        this.preventable = preventable;
        this.targetDescription = targetDescription;
        this.useOnlyTargetPointer = useOnlyTargetPointer;
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
        this.textPhrasing = effect.textPhrasing;
        this.rulesTextTargetFirst = effect.rulesTextTargetFirst;
    }

    public DamageTargetEffect withTargetDescription(String targetDescription) {
        this.targetDescription = targetDescription;
        return this;
    }

    public DamageTargetEffect withTextPhrasing(ValuePhrasing textPhrasing) {
        this.textPhrasing = textPhrasing;
        return this;
    }

    public DamageTargetEffect withTargetFirst() {
        this.rulesTextTargetFirst = true;
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

        // Generate target description
        String targetString = "";
        if (!targetDescription.isEmpty()) {
            targetString = targetDescription;
        } else {
            if (!mode.getTargets().isEmpty()) {
                Target firstTarget = mode.getTargets().get(0);
                String targetName = firstTarget.getTargetName();
                if (targetName.contains("any")) {
                    targetString = targetName;
                } else {
                    if (firstTarget.getMinNumberOfTargets() == 0) {
                        int maxTargets = firstTarget.getMaxNumberOfTargets();
                        if (maxTargets == Integer.MAX_VALUE) {
                            targetString = "any number of ";
                        } else {
                            targetString = "up to " + CardUtil.numberToText(maxTargets) + " ";
                        }
                    }
                    if (!targetName.contains("target ")) {
                        targetString += "target ";
                    }
                    targetString += targetName;
                }
            } else {
                targetString = "that target";
            }
        }

        // Build rules text
        String rulesText;
        if (textPhrasing == ValuePhrasing.LEGACY){
            StringBuilder sb = new StringBuilder();
            String message = amount.getMessage();
            sb.append(this.sourceName).append(" deals ");
            if (!message.equals("1")) {
                sb.append(amount);
            }
            if (!sb.toString().endsWith(" ")) {
                sb.append(' ');
            }
            sb.append("damage to ");
            sb.append(targetString);
            if (!message.isEmpty()) {
                if (message.equals("1")) {
                    sb.append(" equal to the number of ");
                } else {
                    if (message.startsWith("the") || message.startsWith("that") || message.startsWith("twice")) {
                        sb.append(" equal to ");
                    } else {
                        sb.append(" for each ");
                    }
                }
                sb.append(message);
            }
            rulesText = sb.toString();
        } else {
            switch (textPhrasing){
                case X_HIDDEN:
                    rulesText = "{source} deals X damage to {target}";
                    break;
                case X_IS:
                    rulesText = "{source} deals X damage to {target}, where X is {amount}";
                    break;
                case EQUAL_TO:
                    if (rulesTextTargetFirst){
                        rulesText = "{source} deals damage to {target} equal to {amount}";
                    } else {
                        rulesText = "{source} deals damage equal to {amount} to {target}";
                    }
                    break;
                case FOR_EACH:
                    rulesText = "{source} deals {N} damage to {target} for each {amount}";
                    break;
                default:
                    throw new IllegalArgumentException("Phrasing " + textPhrasing + " is not supported in DamageTargetEffect");
            }
            if (amount.getMessage(textPhrasing).equals("that much")) {
                rulesText = "{source} deals that much damage to {target}";
            }
            if (amount instanceof StaticValue){
                rulesText = "{source} deals " + ((StaticValue)amount).getValue() + " damage to {target}";
            }

            rulesText = rulesText.replace("{source}", sourceName);
            rulesText = rulesText.replace("{target}", targetString);
            rulesText = rulesText.replace("{amount}", amount.getMessage(textPhrasing));
            rulesText = rulesText.replace("{N}", (amount instanceof MultipliedValue) ? Integer.toString(((MultipliedValue)amount).getMultiplier()) : "1");
        }

        if (!preventable) {
            rulesText += ". The damage can't be prevented";
        }
        return rulesText;
    }
}
