package mage.view;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class AbilityPickerView implements Serializable {

    private static final long serialVersionUID = 1L;

    private Map<UUID, String> choices = new LinkedHashMap<>();
    private String message;
    private GameView gameView;

    public AbilityPickerView(GameView gameView, String objectName, List<? extends Ability> abilities, String message) {
        this.gameView = gameView;
        this.message = message;

        int num = 0;
        for (Ability ability : abilities) {
            num++;
            String rule;
            if (objectName == null) {
                rule = ability.getRule(true);
            } else {
                // spell abilities must start with "Cast name" (split cards have different names for each spell part)
                if (ability instanceof SpellAbility) {
                    SpellAbility spell = (SpellAbility) ability;
                    rule = getAbilityRules(spell, spell.getCardName());
                    if (!rule.startsWith("Cast ")) {
                        rule = spell.toString() + ": " + rule; // spell.toString() must return this.name (example: Cast Armed)
                    }
                } else {
                    rule = getAbilityRules(ability, objectName);
                }
            }
            choices.put(ability.getId(), num + ". " + rule);
        }
    }

    public AbilityPickerView(GameView gameView, Map<UUID, String> modes, String message) {
        this.gameView = gameView;
        this.choices = modes;
        this.message = message;
    }

    private String getAbilityRules(Ability ability, String objectName) {
        String rule = ability.getRule(objectName);
        if (rule.isEmpty()) {
            rule = ability.toString();
        }
        if (!rule.isEmpty()) {
            rule = Character.toUpperCase(rule.charAt(0)) + rule.substring(1);
        }
        return rule;
    }

    public Map<UUID, String> getChoices() {
        return choices;
    }

    public String getMessage() {
        return message;
    }

    public GameView getGameView() {
        return gameView;
    }
}
