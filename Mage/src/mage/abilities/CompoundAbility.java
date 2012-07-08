package mage.abilities;

import java.util.List;

/**
 * @author noxx
 */
public class CompoundAbility extends AbilitiesImpl<Ability> {
    
    private String ruleText;
    
    public CompoundAbility(Ability... abilities) {
        this(null, abilities);
    }

    public CompoundAbility(String ruleText, Ability... abilities) {
        for (Ability ability : abilities) {
            add(ability);
        }
    }

    public CompoundAbility(final CompoundAbility compoundAbility) {
        for (Ability ability : compoundAbility) {
            add(ability);
        }
        this.ruleText = compoundAbility.ruleText;
    }

    public String getRule() {
        if (ruleText != null) {
            return ruleText;
        }

        StringBuilder sb = new StringBuilder();
        List<String> rules = super.getRules(null);
        for (int index = 0; index < rules.size(); index++) {
            if (index > 0) {
                if (index < rules.size() - 1) {
                    sb.append(", ");
                } else {
                    sb.append(" and ");
                }
            }
            sb.append(rules.get(index));
        }

        // we can't simply cache it to this.ruleText as some cards may change abilities dynamically
        return sb.toString();
    }

    @Override
    public CompoundAbility copy() {
        return new CompoundAbility(this);
    }
}
