package mage.constants;

import mage.abilities.Ability;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.*;

import java.util.UUID;
import java.util.function.Supplier;

/**
 * @author TheElk801
 */
public enum RoleType {
    CURSED("Cursed", CursedRoleToken::new),
    MONSTER("Monster", MonsterRoleToken::new),
    ROYAL("Royal", RoyalRoleToken::new),
    SORCERER("Sorcerer", SorcererRoleToken::new),
    VIRTUOUS("Virtuous", VirtuousRoleToken::new),
    WICKED("Wicked", WickedRoleToken::new),
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

    public Token createToken(Permanent permanent, Game game, Ability source) {
        Token token = supplier.get();
        token.putOntoBattlefield(1, game, source);
        for (UUID tokenId : token.getLastAddedTokenIds()) {
            Permanent aura = game.getPermanent(tokenId);
            if (aura == null || !aura.hasSubtype(SubType.AURA, game)) {
                continue;
            }
            aura.getAbilities().get(0).getTargets().get(0).add(permanent.getId(), game);
            aura.getAbilities().get(0).getEffects().get(0).apply(game, aura.getAbilities().get(0));
        }
        return token;
    }
}
