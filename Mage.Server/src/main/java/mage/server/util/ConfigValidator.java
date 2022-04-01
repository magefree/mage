package mage.server.util;

import mage.server.util.config.Config;
import mage.utils.ValidationResult;

public class ConfigValidator {

    private ConfigValidator() {

    }

    public static ValidationResult validate(Config config) {
        if(config.getServer().isMultihome() && config.getServer().getHome().isEmpty()) {
            return ValidationResult.invalid("Multihome enabled config should contain at least one home");
        }
        if(!config.getServer().isMultihome() && !config.getServer().getHome().isEmpty()) {
            return ValidationResult.invalid("Non-multihome config should not contain any home");
        }
        return ValidationResult.ok();
    }
}
