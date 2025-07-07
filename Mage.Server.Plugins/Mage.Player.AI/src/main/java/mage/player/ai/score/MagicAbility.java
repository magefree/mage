package mage.player.ai.score;

import mage.abilities.Ability;
import mage.abilities.keyword.*;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO: outdated, replace by edh or commander brackets ability score
 * @author nantuko
 */
public final class MagicAbility {

    private static Map<String, Integer> scores = new HashMap<String, Integer>() {{
        put(DeathtouchAbility.getInstance().getRule(), 60);
        put(DefenderAbility.getInstance().getRule(), -100);
        put(DoubleStrikeAbility.getInstance().getRule(), 100);
        put(DoubleStrikeAbility.getInstance().getRule(), 100);
        put(new ExaltedAbility().getRule(), 10);
        put(FirstStrikeAbility.getInstance().getRule(), 50);
        put(FlashAbility.getInstance().getRule(), 20);
        put(FlyingAbility.getInstance().getRule(), 50);
        put(new ForestwalkAbility().getRule(), 10);
        put(HasteAbility.getInstance().getRule(), 20);
        put(IndestructibleAbility.getInstance().getRule(), 150);
        put(InfectAbility.getInstance().getRule(), 60);
        put(IntimidateAbility.getInstance().getRule(), 50);
        put(new IslandwalkAbility().getRule(), 10);
        put(new MountainwalkAbility().getRule(), 10);
        put(new PlainswalkAbility().getRule(), 10);
        put(ReachAbility.getInstance().getRule(), 20);
        put(ShroudAbility.getInstance().getRule(), 60);
        put(new SwampwalkAbility().getRule(), 10);
        put(TrampleAbility.getInstance().getRule(), 30);
        put(new CantBeBlockedSourceAbility().getRule(), 100);
        put(VigilanceAbility.getInstance().getRule(), 20);
        put(WitherAbility.getInstance().getRule(), 30);
        // gatecrash
        put(new EvolveAbility().getRule(), 50);
        put(new ExtortAbility().getRule(), 30);


    }};

    public static int getAbilityScore(Ability ability) {
        if (!scores.containsKey(ability.getRule())) {
            //System.err.println("Couldn't find ability score: " + ability.getClass().getSimpleName() + " - " + ability.toString());
            //TODO: add handling protection from ..., levelup, kicker, etc. abilities
            return 2; // more abilities - more score in any use cases
        }
        return scores.get(ability.getRule());
    }
}
