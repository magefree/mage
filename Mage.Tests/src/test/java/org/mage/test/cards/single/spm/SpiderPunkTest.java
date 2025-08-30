package org.mage.test.cards.single.spm;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.RiotAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.target.TargetPlayer;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author Jmlundeen
 */
public class SpiderPunkTest extends CardTestPlayerBase {

    /*
    Spider-Punk
    {1}{R}
    Legendary Creature - Spider Human Hero
    Riot
    Other Spiders you control have riot.
    Spells and abilities can't be countered.
    Damage can't be prevented.
    2/1
    */
    private static final String spiderPunk = "Spider-Punk";

    /*
    Counterspell
    {U}{U}
    Instant
    Counter target spell.
    */
    private static final String counterspell = "Counterspell";

    /*
    Disallow
    {1}{U}{U}
    Instant
    Counter target spell, activated ability, or triggered ability.
    */
    private static final String disallow = "Disallow";

    /*
    Giant Spider
    {3}{G}
    Creature - Spider
    Reach <i>(This creature can block creatures with flying.)</i>
    2/4
    */
    private static final String giantSpider = "Giant Spider";


    @Test
    public void testSpiderPunk() {
        setStrictChooseMode(true);

        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(2), new TapSourceCost());
        ability.addTarget(new TargetPlayer(1));
        addCustomCardWithAbility("{T} deal damage", playerA, ability);
        addCard(Zone.BATTLEFIELD, playerA, spiderPunk);
        setChoice(playerA, "No"); // Haste - Spider-Punk
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 5);
        addCard(Zone.HAND, playerA, giantSpider);
        addCard(Zone.HAND, playerB, counterspell);
        addCard(Zone.HAND, playerB, disallow);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, giantSpider);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, counterspell, giantSpider);

        setChoice(playerA, "No"); // Haste

        attack(1, playerA, giantSpider);
        attack(1, playerA, spiderPunk);

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{T}: {this} deals 2 damage", playerB);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, disallow);
        addTarget(playerB, "stack ability");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 2 - 2 - 2);
        assertAbilityCount(playerA, giantSpider, RiotAbility.class, 1);
    }
}