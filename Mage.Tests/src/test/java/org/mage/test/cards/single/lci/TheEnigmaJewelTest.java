package org.mage.test.cards.single.lci;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 *
 * @author Jmlundeen
 */
public class TheEnigmaJewelTest extends CardTestPlayerBase {

    /*
    The Enigma Jewel
    {U}
    Legendary Artifact
    The Enigma Jewel enters the battlefield tapped.
    {T}: Add {C}{C}. Spend this mana only to activate abilities.
    Craft with four or more nonlands with activated abilities {8}{U}
    Locus of Enlightenment
    Legendary Artifact
    Locus of Enlightenment has each activated ability of the exiled cards used to craft it. You may activate each of those abilities only once each turn.
    Whenever you activate an ability that isn't a mana ability, copy it. You may choose new targets for the copy.
    */
    private static final String theEnigmaJewel = "The Enigma Jewel";
    private static final String locusOfEnlightenment = "Locus of Enlightenment";

    /*
    Scavenging Ooze
    {1}{G}
    Creature - Ooze
    {G}: Exile target card from a graveyard. If it was a creature card, put a +1/+1 counter on Scavenging Ooze and you gain 1 life.
    2/2
    */
    private static final String scavengingOoze = "Scavenging Ooze";

    /*
    Aetherflame Wall
    {1}{R}
    Creature - Wall
    Defender
    &AElig;therflame Wall can block creatures with shadow as though they didn't have shadow.
    {R}: &AElig;therflame Wall gets +1/+0 until end of turn.
    0/4
    */
    private static final String aetherflameWall = "Aetherflame Wall";

    /*
    Ancient Silverback
    {4}{G}{G}
    Creature - Ape
    {G}: Regenerate Ancient Silverback. <i>(The next time this creature would be destroyed this turn, it isn't. Instead tap it, remove all damage from it, and remove it from combat.)</i>
    6/5
    */
    private static final String ancientSilverback = "Ancient Silverback";

    /*
    Ancient Hellkite
    {4}{R}{R}{R}
    Creature - Dragon
    Flying
    {R}: Ancient Hellkite deals 1 damage to target creature defending player controls. Activate this ability only if Ancient Hellkite is attacking.
    6/6
    */
    private static final String ancientHellkite = "Ancient Hellkite";

    @Test
    public void testTheEnigmaJewelCraft() {
        addCard(Zone.BATTLEFIELD, playerA, theEnigmaJewel);
        addCard(Zone.BATTLEFIELD, playerA, scavengingOoze);
        addCard(Zone.BATTLEFIELD, playerA, aetherflameWall);
        addCard(Zone.BATTLEFIELD, playerA, ancientSilverback);
        addCard(Zone.BATTLEFIELD, playerA, ancientHellkite);
        addCard(Zone.BATTLEFIELD, playerA, "Taiga", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Island");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Craft with");
        addTarget(playerA, scavengingOoze + "^" + aetherflameWall + "^" + ancientSilverback + "^" + ancientHellkite);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        Permanent permanent = getPermanent(locusOfEnlightenment, playerA);
        assertNotNull(permanent);
        assertEquals(4, permanent.getAbilities(currentGame).getActivatedAbilities(Zone.BATTLEFIELD).size());
    }
}