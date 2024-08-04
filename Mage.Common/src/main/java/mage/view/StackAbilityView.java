package mage.view;

import mage.MageObject;
import mage.abilities.Mode;
import mage.abilities.Modes;
import mage.abilities.effects.Effect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.HintUtils;
import mage.cards.Card;
import mage.constants.AbilityType;
import mage.constants.CardType;
import mage.constants.MageObjectType;
import mage.game.Game;
import mage.game.stack.StackAbility;
import mage.game.stack.StackObject;
import mage.target.Target;
import mage.target.targetpointer.TargetPointer;
import mage.util.GameLog;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class StackAbilityView extends CardView {

    private static final long serialVersionUID = 1L;

    // in GUI: that's view will be replaced by sourceCard, so don't forget to sync settings like
    // selectable, chooseable, card icons etc. Search by getSourceCard
    private final CardView sourceCard;

    public StackAbilityView(Game game, StackAbility ability, String sourceName, MageObject sourceObject, CardView sourceView) {
        this.id = ability.getId();
        this.mageObjectType = MageObjectType.ABILITY_STACK;
        this.abilityType = ability.getStackAbility().getAbilityType();
        this.sourceCard = sourceView;
        this.sourceCard.setMageObjectType(mageObjectType);
        this.name = "Ability";
        this.loyalty = "";
        this.defense = "";

        this.cardTypes = ability.getCardType(game);
        this.subTypes = ability.getSubtype(game);
        this.superTypes = ability.getSuperType(game);
        this.color = ability.getColor(game);
        this.manaCostLeftStr = ability.getManaCostSymbols();
        this.manaCostRightStr = new ArrayList<>();
        this.cardTypes = ability.getCardType(game);
        this.subTypes = ability.getSubtype(game);
        this.superTypes = ability.getSuperType(game);
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
            tmpSourceCard.manaCostLeftStr = new ArrayList<>();
            tmpSourceCard.manaCostRightStr = new ArrayList<>();
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

        this.generateCardIcons(ability, sourceObject, game);
    }

    private void updateTargets(Game game, StackAbility ability) {
        List<UUID> targetList = new ArrayList<>();
        for (UUID modeId : ability.getModes().getSelectedModes()) {
            Mode mode = ability.getModes().get(modeId);
            if (!mode.getTargets().isEmpty()) {
                addTargets(mode.getTargets(), mode.getEffects(), ability, game);
            } else {
                for (Effect effect : mode.getEffects()) {
                    TargetPointer targetPointer = effect.getTargetPointer();
                    targetList.addAll(targetPointer.getTargets(game, ability));
                }
                if (!targetList.isEmpty()) {
                    overrideTargets(targetList);

                    for (UUID uuid : targetList) {
                        MageObject mageObject = game.getObject(uuid);
                        if (mageObject != null) {
                            if ((mageObject instanceof Card) && ((Card) mageObject).isFaceDown(game)) {
                                continue;
                            }
                            List<String> rules = getRules();
                            rules.add("<i>Related objects: "
                                    + GameLog.getColoredObjectIdNameForTooltip(mageObject) + "</i>");
                            // TODO: apply this to more card types
                            if (mageObject.isCreature()){
                                Card card = game.getCard(uuid);
                                rules.add(getCardTypesText(card));
                                rules.add(getCardRulesText(card));
                            }
                        }
                    }
                }
            }
        }

        // show for modal ability, which mode was chosen
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
                && targetList.isEmpty()) {
            StackObject stackObjectTarget = null;
            for (Target target : ability.getTargets()) {
                for (UUID targetId : target.getTargets()) {
                    MageObject mo = game.getObject(targetId);
                    if (mo instanceof StackObject) {
                        stackObjectTarget = (StackObject) mo;
                    }
                    if (stackObjectTarget != null) {
                        this.rules.add("<span color='green'><i>Target on stack: " + stackObjectTarget.getIdName());
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

    private String getCardTypesText(Card card) {
        StringBuilder sb = new StringBuilder();
        if (card != null && card.isCreature())
        {
            sb.append("Mana Cost: ");
            sb.append(card.getManaCost()).append(" | ");
            if (!card.getSuperType().isEmpty()) {
                sb.append(card.getSuperType()).append(" ");
            }
            sb.append(card.getCardType()).append(" - ");
            sb.append(card.getSubtype());
        }
        // Remove all "[", "]", and ","
        return sb.toString().replaceAll("[\\[\\],]", "");
    }

    private String getCardRulesText(Card card) {
        StringBuilder sb = new StringBuilder();
        if (card != null && card.isCreature())
        {
            sb.append(card.getRules());
            if (sb.length() >= 2) {
                // Replace the last char with ' '
                sb.setCharAt(sb.length() - 1, ' ');

                // Remove the first character
                sb.deleteCharAt(0);
            }
            sb.append(card.getPower()).append("/");
            sb.append(card.getToughness());

            // Replace "{this}" placeholder with card name and remove commas after periods
            return sb.toString().replace("{this}", card.getName()).replace(".,", ".");
        }
        return sb.toString();
    }
}
