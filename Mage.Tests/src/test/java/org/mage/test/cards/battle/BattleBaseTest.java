package org.mage.test.cards.battle;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.EntersBattlefieldWithCountersAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import org.junit.jupiter.api.Assertions;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBaseWithAIHelps;

/**
 * @author TheElk801
 */
public class BattleBaseTest extends CardTestPlayerBaseWithAIHelps {

    protected static final String belenon = "Invasion of Belenon";
    protected static final String warAnthem = "Belenon War Anthem";
    protected static final String kaladesh = "Invasion of Kaladesh";
    protected static final String bear = "Grizzly Bears";
    protected static final String bearWithFlyingAndVigilance = "Abbey Griffin";
    protected static final String confiscate = "Confiscate";
    protected static final String impact = "Explosive Impact";
    protected static final String stifle = "Stifle";
    protected static final String fayden = "Dack Fayden";
    protected static final String typelessBattle = "Typeless Battle";

    protected void assertBattle(Player controller, Player protector, String name) {
        assertPermanentCount(controller, name, 1);
        Permanent permanent = getPermanent(name);
        Assertions.assertTrue(
                permanent.isProtectedBy(protector.getId()),
                "Battle " + name + " should be protected by " + protector.getName()
        );
    }

    /**
     * Adds to hand a battle card without battle types (no printed one exists yet), cast for {1}
     *
     * @param extraAbility optional, can be null
     */
    protected void addTypelessBattleToHand(TestPlayer player, int defense, Ability extraAbility) {
        SpellAbility spell = new SpellAbility(new ManaCostsImpl<>("{1}"), typelessBattle, Zone.HAND);
        Ability ability = new EntersBattlefieldWithCountersAbility(CounterType.DEFENSE.createInstance(defense));
        if (extraAbility != null) {
            ability.addSubAbility(extraAbility);
        }
        addCustomCardWithSpell(player, spell, ability, CardType.BATTLE);
    }
}
