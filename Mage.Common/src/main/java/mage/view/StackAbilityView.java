
package mage.view;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Mode;
import mage.abilities.Modes;
import mage.abilities.effects.Effect;
import mage.cards.Card;
import mage.constants.AbilityType;
import mage.constants.CardType;
import mage.constants.MageObjectType;
import mage.game.Game;
import mage.game.stack.StackAbility;
import mage.target.targetpointer.FixedTarget;
import mage.target.targetpointer.TargetPointer;
import mage.util.GameLog;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class StackAbilityView extends CardView {

    private static final long serialVersionUID = 1L;

    private final CardView sourceCard;

    public StackAbilityView(Game game, StackAbility ability, String sourceName, CardView sourceCard) {
        this.id = ability.getId();
        this.mageObjectType = MageObjectType.ABILITY_STACK;
        this.abilityType = ability.getStackAbility().getAbilityType();
        this.sourceCard = sourceCard;
        this.sourceCard.setMageObjectType(mageObjectType);
        this.name = "Ability";
        this.loyalty = "";

        this.cardTypes = ability.getCardType();
        this.subTypes = ability.getSubtype(game);
        this.superTypes = ability.getSuperType();
        this.color = ability.getColor(game);
        this.manaCost = ability.getManaCost().getSymbols();
        this.cardTypes = ability.getCardType();
        this.subTypes = ability.getSubtype(game);
        this.superTypes = ability.getSuperType();
        this.color = ability.getColor(game);
        this.manaCost = ability.getManaCost().getSymbols();
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
            tmpSourceCard.manaCost.clear();
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
    }

    private void updateTargets(Game game, StackAbility ability) {
        List<String> names = new ArrayList<>();
        for (UUID modeId : ability.getModes().getSelectedModes()) {
            Mode mode = ability.getModes().get(modeId);
            if (!mode.getTargets().isEmpty()) {
                setTargets(mode.getTargets());
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
                            names.add(GameLog.getColoredObjectIdNameForTooltip(mageObject));
                        }
                    }

                }
            }
        }
        if (!names.isEmpty()) {
            getRules().add("<i>Related objects: " + names.toString() + "</i>");
        }
        // show for modal ability, which mode was choosen
        if (ability.isModal()) {
            Modes modes = ability.getModes();
            for (UUID modeId : modes.getSelectedModes()) {
                Mode mode = modes.get(modeId);
                this.rules.add("<span color='green'><i>Chosen mode: " + mode.getEffects().getText(mode) + "</i></span>");
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
