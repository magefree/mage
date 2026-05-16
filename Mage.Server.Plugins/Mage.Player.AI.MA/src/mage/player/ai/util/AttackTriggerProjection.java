package mage.player.ai.util;

import mage.constants.SubType;
import mage.constants.SubTypeSet;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Lightweight projection for combat effects that are visible before declaring attackers.
 *
 * This intentionally separates "attacks" triggers from "is attacking" static effects:
 * - attacks: only projected for creatures that will be declared as attackers
 * - attacking: projected as a combat-state effect for creatures that are attacking
 */
public final class AttackTriggerProjection {

    private AttackTriggerProjection() {
    }

    public static Projection projectDeclaredAttacker(Permanent attacker, Game game) {
        if (attacker == null || game == null) {
            return Projection.none();
        }
        Projection projection = Projection.none();
        for (Permanent source : game.getBattlefield().getAllActivePermanents(attacker.getControllerId())) {
            String rules = rules(source, game);
            if (rules.isEmpty()) {
                continue;
            }
            projection = projection.plus(projectAttacksTrigger(source, attacker, rules, game));
            projection = projection.plus(projectAttackingStateEffect(source, attacker, rules, game));
        }
        return projection;
    }

    public static int likelyAttackingDamage(Permanent attacker, Game game) {
        if (attacker == null || game == null) {
            return 0;
        }
        int damage = CombatUtil.getCombatDamageValue(attacker, game);
        Projection projection = projectDeclaredAttacker(attacker, game);
        if (projection.grantsDoubleStrike) {
            damage += CombatUtil.getCombatDamageValue(attacker, game);
        }
        damage += projection.powerBonus;
        if (projection.grantsDoubleStrike && projection.powerBonus > 0) {
            damage += projection.powerBonus;
        }
        return Math.max(0, damage);
    }

    private static Projection projectAttacksTrigger(Permanent source, Permanent attacker, String rules, Game game) {
        if (!rules.contains("whenever") || !rules.contains("attacks")) {
            return Projection.none();
        }
        if (!triggerCanApplyToAttacker(source, attacker, rules, game)) {
            return Projection.none();
        }
        Projection projection = Projection.none();
        if (rules.contains("double strike")) {
            projection = projection.withDoubleStrike("declared-attack trigger grants double strike");
        }
        int powerBonus = parsePowerBonus(rules);
        if (powerBonus > 0) {
            projection = projection.withPowerBonus(powerBonus, "declared-attack trigger grants +" + powerBonus + "/+0 or better");
        }
        if (rules.contains("draw a card")) {
            projection = projection.withValue(10, "declared-attack trigger draws a card");
        }
        if (rules.contains("create") && (rules.contains("token") || rules.contains("treasure"))) {
            projection = projection.withValue(8, "declared-attack trigger creates resources");
        }
        if (rules.contains("deals ") || rules.contains("loses ") && rules.contains("life")) {
            projection = projection.withValue(10, "declared-attack trigger adds damage/life pressure");
        }
        if (rules.contains("tap target") || rules.contains("can't block")) {
            projection = projection.withValue(10, "declared-attack trigger can affect blockers");
        }
        return projection;
    }

    private static Projection projectAttackingStateEffect(Permanent source, Permanent attacker, String rules, Game game) {
        if (!rules.contains("attacking") || rules.contains("whenever")) {
            return Projection.none();
        }
        if (!stateEffectCanApplyToAttacker(source, attacker, rules, game)) {
            return Projection.none();
        }
        Projection projection = Projection.none();
        if (rules.contains("double strike")) {
            projection = projection.withDoubleStrike("attacking-state effect grants double strike");
        }
        int powerBonus = parsePowerBonus(rules);
        if (powerBonus > 0) {
            projection = projection.withPowerBonus(powerBonus, "attacking-state effect grants +" + powerBonus + "/+0 or better");
        }
        return projection;
    }

    private static boolean triggerCanApplyToAttacker(Permanent source, Permanent attacker, String rules, Game game) {
        if (source.getId().equals(attacker.getId()) && (rules.contains("this creature attacks") || rules.contains(source.getName().toLowerCase(Locale.ROOT) + " attacks"))) {
            return true;
        }
        if (rules.contains("you attack") || rules.contains("one or more creatures you control attack")) {
            return true;
        }
        if (rules.contains("creature you control attacks") || rules.contains("creatures you control attack")) {
            return true;
        }
        for (SubType subtype : attacker.getSubtype(game)) {
            if (subtype.getSubTypeSet() == SubTypeSet.CreatureType && rules.contains(subtype.toString().toLowerCase(Locale.ROOT))) {
                return true;
            }
        }
        return false;
    }

    private static boolean stateEffectCanApplyToAttacker(Permanent source, Permanent attacker, String rules, Game game) {
        if (rules.contains("attacking creatures you control") || rules.contains("creatures you control that are attacking")) {
            return true;
        }
        if (rules.contains("creatures attacking your opponents")) {
            return true;
        }
        for (SubType subtype : attacker.getSubtype(game)) {
            if (subtype.getSubTypeSet() == SubTypeSet.CreatureType && rules.contains(subtype.toString().toLowerCase(Locale.ROOT))) {
                return true;
            }
        }
        return source.getId().equals(attacker.getId()) && rules.contains("while attacking");
    }

    private static int parsePowerBonus(String rules) {
        int best = 0;
        for (int i = 0; i < rules.length() - 4; i++) {
            if (rules.charAt(i) != '+') {
                continue;
            }
            int end = i + 1;
            while (end < rules.length() && Character.isDigit(rules.charAt(end))) {
                end++;
            }
            if (end == i + 1 || end + 1 >= rules.length() || rules.charAt(end) != '/' || rules.charAt(end + 1) != '+') {
                continue;
            }
            try {
                best = Math.max(best, Integer.parseInt(rules.substring(i + 1, end)));
            } catch (NumberFormatException ignored) {
            }
        }
        return best;
    }

    private static String rules(Permanent permanent, Game game) {
        try {
            return permanent.getAbilities(game).getRules()
                    .stream()
                    .map(rule -> rule.toLowerCase(Locale.ROOT))
                    .collect(Collectors.joining(" "));
        } catch (RuntimeException e) {
            return "";
        }
    }

    public static final class Projection {
        private final boolean grantsDoubleStrike;
        private final int powerBonus;
        private final int value;
        private final List<String> reasons;

        private Projection(boolean grantsDoubleStrike, int powerBonus, int value, List<String> reasons) {
            this.grantsDoubleStrike = grantsDoubleStrike;
            this.powerBonus = powerBonus;
            this.value = value;
            this.reasons = reasons;
        }

        public static Projection none() {
            return new Projection(false, 0, 0, new ArrayList<>());
        }

        public boolean grantsDoubleStrike() {
            return grantsDoubleStrike;
        }

        public int getPowerBonus() {
            return powerBonus;
        }

        public int getValue() {
            return value;
        }

        public List<String> getReasons() {
            return reasons;
        }

        private Projection withDoubleStrike(String reason) {
            List<String> nextReasons = new ArrayList<>(reasons);
            nextReasons.add(reason);
            return new Projection(true, powerBonus, value + 12, nextReasons);
        }

        private Projection withPowerBonus(int bonus, String reason) {
            List<String> nextReasons = new ArrayList<>(reasons);
            nextReasons.add(reason);
            return new Projection(grantsDoubleStrike, Math.max(powerBonus, bonus), value + bonus * 2, nextReasons);
        }

        private Projection withValue(int addedValue, String reason) {
            List<String> nextReasons = new ArrayList<>(reasons);
            nextReasons.add(reason);
            return new Projection(grantsDoubleStrike, powerBonus, value + addedValue, nextReasons);
        }

        private Projection plus(Projection other) {
            if (other == null) {
                return this;
            }
            List<String> nextReasons = new ArrayList<>(reasons);
            nextReasons.addAll(other.reasons);
            return new Projection(
                    grantsDoubleStrike || other.grantsDoubleStrike,
                    Math.max(powerBonus, other.powerBonus),
                    value + other.value,
                    nextReasons
            );
        }
    }
}
