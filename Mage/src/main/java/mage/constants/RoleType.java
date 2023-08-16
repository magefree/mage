package mage.constants;

import mage.game.permanent.token.Token;
import mage.game.permanent.token.YoungHeroRoleToken;

import java.util.function.Supplier;

/**
 * @author TheElk801
 */
public enum RoleType {
    YOUNG_HERO("Young Hero", YoungHeroRoleToken::new);

    private final String name;
    private final Supplier<Token> supplier;

    RoleType(String name, Supplier<Token> supplier) {
        this.name = name;
        this.supplier = supplier;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public Token createToken() {
        return supplier.get();
    }
}
