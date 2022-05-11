package mage.view;

import mage.MageObject;
import mage.abilities.Mode;
import mage.abilities.Modes;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.Effect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.HintUtils;
import mage.abilities.icon.other.VariableCostCardIcon;
import mage.cards.Card;
import mage.constants.AbilityType;
import mage.constants.CardType;
import mage.constants.MageObjectType;
import mage.game.Game;
import mage.game.stack.StackAbility;
import mage.target.targetpointer.FixedTarget;
import mage.target.targetpointer.TargetPointer;
import mage.util.GameLog;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.game.stack.StackObject;
import mage.target.Target;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class StackAbilityView extends CardView {

    private static final long serialVersionUID = 1L;

    // in GUI: that's view will be replaced by sourceCard, so don't forget to sync settings like
    // selectable, chooseable, card icons etc. Search by getSourceCard
    private final CardView sourceCard;

    public StackAbilityView(Game game, StackAbility ability, String sourceName, CardView sourceCard) {
        this.id = ability.getId();
        this.mageObjectType = MageObjectType.ABILITY_STACK;
        this.abilityType = ability.getStackAbility().getAbilityType();
        this.sourceCard = sourceCard;
        this.sourceCard.setMageObjectType(mageObjectType);
        this.name = "Ability";
        this.loyalty = "";

        this.cardTypes = ability.getCardType(game);
        this.subTypes = ability.getSubtype(game);
        this.superTypes = ability.getSuperType();
        this.color = ability.getColor(game);
        this.manaCostLeftStr = String.join("", ability.getManaCostSymbols());
        this.manaCostRightStr = "";
        this.cardTypes = ability.getCardType(game);
        this.subTypes = ability.getSubtype(game);
        this.superTypes = ability.getSuperType();
        this.color = ability.getColor(game);
        this.power = ability.getPower().toString();
        this.toughness = ability.getToughness().toString();
        String nameToShow;
        if (sourceCard.isFaceDown()) {
            CardView tmpSourceCard = this.getSourceCard();
            tmpSourceCard.displayName = "Face Down";
            tmpSourceCard.superTypes.clear();
            tmpSourceCard.subTypes.clear();
            tmpSourceCard.cardTypes.clear();
            tmpSourceCard.cardTypes.add(CardType.CREATURE);
            tmpSourceCard.manaCostLeftStr = "";
            tmpSourceCard.manaCostRightStr = "";
            tmpSourceCard.power = "2";
            tmpSourceCard.toughness = "2";
            nameToShow = "creature without name";
        } else {
            nameToShow = sourceName;
        }
        this.rules = new ArrayList<>();
        rules.add(ability.getRule(nameToShow));
        this.counters = sourceCard.getCounters();

        updateTargets(game, ability);

        // card icons (warning, it must be synced in gui dialogs with replaced card, see comments at the start of the file)
        // cost x
        if (ability.getManaCostsToPay().containsX()) {
            int costX = ManacostVariableValue.END_GAME.calculate(game, ability, null);
            this.cardIcons.add(new VariableCostCardIcon(costX));
        }
    }

    private void updateTargets(Game game, StackAbility ability) {
        List<String> names = new ArrayList<>();
        for (UUID modeId : ability.getModes().getSelectedModes()) {
            Mode mode = ability.getModes().get(modeId);
            if (!mode.getTargets().isEmpty()) {
                addTargets(mode.getTargets(), mode.getEffects(), ability, game);
            } else {
                List<UUID> targetList = new ArrayList<>();
                for (Effect effect : mode.getEffects()) {
                    TargetPointer targetPointer = effect.getTargetPointer();
                    if (targetPointer instanceof FixedTarget) {
                        targetList.add(((FixedTarget) targetPointer).getTarget());
                    }
                }
                if (!targetList.isEmpty()) {
                    overrideTargets(targetList);

                    for (UUID uuid : targetList) {
                        MageObject mageObject = game.getObject(uuid);
                        if (mageObject != null) {
                            if ((mageObject instanceof Card) && ((Card) mageObject).isFaceDown(game)) {
                                continue;
                            }
                            String newName = GameLog.getColoredObjectIdNameForTooltip(mageObject);
                            if (!names.contains(newName)) {
                                names.add(newName);
                            }
                        }
                    }
                }
            }
        }
        if (!names.isEmpty()) {
            getRules().add("<i>Related objects: " + names + "</i>");
        }

        // show for modal ability, which mode was choosen
        if (ability.isModal()) {
            Modes modes = ability.getModes();
            for (UUID modeId : modes.getSelectedModes()) {
                Mode mode = modes.get(modeId);
                this.rules.add("<span color='green'><i>Chosen mode: " + mode.getEffects().getText(mode) + "</i></span>");
            }
        }

        if (HintUtils.ABILITY_HINTS_ENABLE) {
            List<String> abilityHints = new ArrayList<>();
            for (Hint hint : ability.getHints()) {
                abilityHints.add(hint.getText(game, ability));
            }
            // total hints
            if (!abilityHints.isEmpty()) {
                rules.add(HintUtils.HINT_START_MARK);
                HintUtils.appendHints(rules, abilityHints);
            }
        }

        // show target of an ability on the stack if "related objects" is empty
        if (!ability.getTargets().isEmpty()
                && names.isEmpty()) {
            StackObject stackObjectTarget = null;
            for (Target target : ability.getTargets()) {
                for (UUID targetId : target.getTargets()) {
                    MageObject mo = game.getObject(targetId);
                    if (mo instanceof StackObject) {
                        stackObjectTarget = (StackObject) mo;
                    }
                    if (stackObjectTarget != null) {
                        this.rules.add("<span color='green'><i>Targeted ability related to this card: " + game.getCard(stackObjectTarget.getSourceId()).getIdName());
                    }
                }
            }

        }
    }

    public CardView getSourceCard() {
        return this.sourceCard;
    }

    @Override
    public AbilityType getAbilityType() {
        return abilityType;
    }

}
