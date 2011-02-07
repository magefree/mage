package mage.player.ai.ma;

import mage.abilities.Ability;
import mage.abilities.StaticAbility;
import mage.abilities.keyword.*;
import mage.cards.basiclands.Plains;

import java.util.HashMap;
import java.util.Map;
import java.util.zip.Inflater;

/**
 * @author nantuko
 */
public class MagicAbility {

	private static Map<String, Integer> scores = new HashMap<String, Integer>() {{
		scores.put(DeathtouchAbility.getInstance().getRule(), 60);
		scores.put(DefenderAbility.getInstance().getRule(), -100);
		scores.put(DoubleStrikeAbility.getInstance().getRule(), 100);
		scores.put(DoubleStrikeAbility.getInstance().getRule(), 100);
		scores.put(new ExaltedAbility().getRule(), 10);
		scores.put(FirstStrikeAbility.getInstance().getRule(), 50);
		scores.put(FlashAbility.getInstance().getRule(), 0);
		scores.put(FlyingAbility.getInstance().getRule(), 50);
		scores.put(new ForestwalkAbility().getRule(), 10);
		scores.put(HasteAbility.getInstance().getRule(), 0);
		scores.put(IndestructibleAbility.getInstance().getRule(), 150);
		scores.put(InfectAbility.getInstance().getRule(), 60);
		scores.put(IntimidateAbility.getInstance().getRule(), 50);
		scores.put(new IslandwalkAbility().getRule(), 10);
		scores.put(new MountainwalkAbility().getRule(), 10);
		scores.put(new PlainswalkAbility().getRule(), 10);
		scores.put(ReachAbility.getInstance().getRule(), 20);
		scores.put(ShroudAbility.getInstance().getRule(), 60);
		scores.put(new SwampwalkAbility().getRule(), 10);
		scores.put(TrampleAbility.getInstance().getRule(), 30);
		scores.put(UnblockableAbility.getInstance().getRule(), 100);
		scores.put(VigilanceAbility.getInstance().getRule(), 20);
		scores.put(WitherAbility.getInstance().getRule(), 30);
	}};

	public static int getAbilityScore(Ability ability) {
		if (!scores.containsKey(ability.getRule())) {
			System.err.println("Couldn't find ability score: " + ability.getRule());
			//TODO: add handling protection from ..., levelup, kicker, etc. abilities
		}
		return scores.get(ability.getRule());
	}
}
