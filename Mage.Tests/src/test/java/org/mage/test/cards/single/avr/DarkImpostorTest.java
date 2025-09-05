package org.mage.test.cards.single.avr;

import mage.abilities.common.SimpleActivatedAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class DarkImpostorTest extends CardTestPlayerBase {

    /*
    Dark Impostor
    {2}{B}
    Creature — Vampire Assassin

    {4}{B}{B}: Exile target creature and put a +1/+1 counter on this creature.

    This creature has all activated abilities of all creature cards exiled with it.
    2/2
     */
    public static final String darkImposter = "Dark Impostor";
    /*
    Deathrite Shaman
    {B/G}
    Creature — Elf Shaman

    {T}: Exile target land card from a graveyard. Add one mana of any color.

    {B}, {T}: Exile target instant or sorcery card from a graveyard. Each opponent loses 2 life.

    {G}, {T}: Exile target creature card from a graveyard. You gain 2 life.

    1/2
     */
    public static final String deathriteShaman = "Deathrite Shaman";

    @Test
    public void testDarkImpostor() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, darkImposter);
        addCard(Zone.BATTLEFIELD, playerA, deathriteShaman);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{4}{B}{B}");
        addTarget(playerA, deathriteShaman);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAbilityCount(playerA, darkImposter, SimpleActivatedAbility.class, 4); // own ability + 3 other from deathrite
    }
}
