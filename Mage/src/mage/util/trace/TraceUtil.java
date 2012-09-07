package mage.util.trace;

import mage.Constants;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.StaticAbility;
import mage.abilities.effects.ContinuousEffectsList;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.UnblockableAbility;
import mage.game.Game;
import mage.game.combat.Combat;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;
import org.apache.log4j.Logger;

import java.util.UUID;

/**
 * @author magenoxx_at_gmail.com
 */
public class TraceUtil {
    
    private static final Logger log = Logger.getLogger(TraceUtil.class);

    /**
     * This method is intended to catch various bugs with combat.
     *
     * One of them (possibly the most annoying) is when creature without flying or reach blocks creature with flying.
     * No test managed to reproduce it, but it happens in the games time to time and was reported by different players.
     *
     * The idea: is to catch such cases manually and print out as much information from game state that may help as possible.
     */
    public static void traceCombatIfNeeded(Game game, Combat combat) {
        // trace non-flying vs flying
        for (CombatGroup group : combat.getGroups()) {
            for (UUID attackerId : group.getAttackers()) {
                Permanent attacker = game.getPermanent(attackerId);
                if (attacker != null) {
                    if (hasFlying(attacker)) {
                        for (UUID blockerId : group.getBlockers()) {
                            Permanent blocker = game.getPermanent(blockerId);
                            if (blocker != null && !hasFlying(blocker) && !hasReach(blocker)) {
                                log.warn("Found non-flying non-reach creature blocking creature with flying");
                                traceCombat(game, attacker, blocker);
                            }
                        }
                    }
                    if (hasUnblockable(attacker)) {
                        if (group.getBlockers().size() > 0) {
                            Permanent blocker = game.getPermanent(group.getBlockers().get(0));
                            if (blocker != null) {
                                log.warn("Found unblockable creature blocked by some other creature");
                                traceCombat(game, attacker, blocker);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * We need this to check Flying existence in not-common way: by instanceof.
     * @return
     */
    private static boolean hasFlying(Permanent permanent) {
        for (Ability ability : permanent.getAbilities()) {
            if (ability instanceof FlyingAbility) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasReach(Permanent permanent) {
        for (Ability ability : permanent.getAbilities()) {
            if (ability instanceof ReachAbility) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasUnblockable(Permanent permanent) {
        for (Ability ability : permanent.getAbilities()) {
            if (ability instanceof UnblockableAbility) {
                return true;
            }
        }
        return false;
    }
    
    private static void traceCombat(Game game, Permanent attacker, Permanent blocker) {
        String uuid = "[" + UUID.randomUUID() + "] ";
        log.error(uuid+"Tracing game state...");
        log.error(uuid+blocker.getName() + " could block " + attacker.getName());

        log.error(uuid);
        log.error(uuid+"Attacker abilities: ");
        for (Ability ability : attacker.getAbilities()) {
            log.error(uuid+"     " + ability.toString() + ", id=" + ability.getId());
        }

        log.error(uuid+"Blocker abilities: ");
        for (Ability ability : blocker.getAbilities()) {
            log.error(uuid+"     " + ability.toString() + ", id=" + ability.getId());
        }

        log.error(uuid);
        log.error(uuid+"Flying ability id: " + FlyingAbility.getInstance().getId());
        log.error(uuid+"Reach ability id: " + ReachAbility.getInstance().getId());
        log.error(uuid);

        log.error(uuid+"Restriction effects:");
        Ability ability = attacker.getAbilities().size() > 0 ? attacker.getAbilities().get(0) : null;
        ContinuousEffectsList<RestrictionEffect> restrictionEffects = (ContinuousEffectsList<RestrictionEffect>) game.getContinuousEffects().getRestrictionEffects();
        for (RestrictionEffect effect : restrictionEffects) {
            log.error(uuid+"    " + effect);
            log.error(uuid+"        id=" + effect.getId());
            log.error(uuid+"        applies to attacker=" + effect.applies(attacker, ability, game));
            log.error(uuid+"        applies to blocker=" + effect.applies(blocker, ability, game));
        }

        traceForPermanent(game, attacker, uuid, restrictionEffects);
        traceForPermanent(game, blocker, uuid, restrictionEffects);

        log.error(uuid);
    }

    private static void traceForPermanent(Game game, Permanent permanent, String uuid, ContinuousEffectsList<RestrictionEffect> restrictionEffects) {
        Ability ability;
        for (RestrictionEffect effect: restrictionEffects) {
            ability = restrictionEffects.getAbility(effect.getId());
            if (!(ability instanceof StaticAbility) || ability.isInUseableZone(game, permanent, false)) {
                log.error(uuid+"        ability=" + ability + ", applies_to_attacker=" + effect.applies(permanent, ability, game));
            } else {
                boolean usable = ability.isInUseableZone(game, permanent, false);
                log.error(uuid+"        instanceof: " + (ability instanceof StaticAbility) + ", ability=" + ability);
                log.error(uuid+"        usable: " + usable + ", ability=" + ability);
                if (!usable) {
                    Constants.Zone zone = ability.getZone();
                    log.error(uuid+"        zone: " + zone);
                    MageObject object = game.getObject(ability.getSourceId());
                    log.error(uuid+"        object: " + object);
                    if (object != null) {
                        log.error(uuid + "        contains:" + object.getAbilities().contains(ability));
                    }
                    Constants.Zone test = game.getState().getZone(ability.getSourceId());
                    log.error(uuid+"        test_zone: " + test);
                }
            }
        }
    }

    public static void trace(String msg) {
        log.info(msg);
    }
}
