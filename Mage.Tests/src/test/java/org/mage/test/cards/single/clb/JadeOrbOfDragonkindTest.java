package org.mage.test.cards.single.clb;

import mage.abilities.keyword.HexproofAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.j.JadeOrbOfDragonkind Jade Orb of Dragonkind}
 * {2}{G}
 * Artifact
 * {T}: Add {G}. When you spend this mana to cast a Dragon creature spell, it enters the battlefield with an additional +1/+1 counter on it and gains hexproof until your next turn.
 * @author NicolasCamachoP, Zelane
 */

public class JadeOrbOfDragonkindTest extends CardTestPlayerBase {
  private static final String jadeOrb = "Jade Orb of Dragonkind";
  private static final String arcades = "Arcades, the Strategist";
  private static final String mowu = "Mowu, Loyal Companion";

  @Test
  public void manaAbility() {
    addCard(Zone.HAND, playerA, "Ancient Bronze Dragon");
    addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
    addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
    addCard(Zone.BATTLEFIELD, playerA, jadeOrb);
    castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ancient Bronze Dragon");

    setStrictChooseMode(true);
    setStopAt(1, PhaseStep.BEGIN_COMBAT);
    execute();

    assertPermanentCount(playerA, 8);
    assertTapped(jadeOrb, true);
  }

  @Test
  public void manaUsedEffects() {
    addCard(Zone.HAND, playerA, arcades);
    addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
    addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
    addCard(Zone.BATTLEFIELD, playerA, jadeOrb);
    addCard(Zone.BATTLEFIELD, playerA, "Kronch Wrangler");
    // Whenever a creature with power 4 or greater enters the battlefield under your control, put a +1/+1 counter on Kronch Wrangler.

    castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, arcades);

    setStrictChooseMode(true);
    setStopAt(1, PhaseStep.END_COMBAT);
    execute();

    assertPermanentCount(playerA, 6);
    assertTapped(jadeOrb, true);
    assertCounterCount(arcades, CounterType.P1P1, 1);
    assertPowerToughness(playerA, "Kronch Wrangler", 3, 2);
    assertAbility(playerA, arcades, HexproofAbility.getInstance(), true);
  }

  @Test
  public void hexproofDropOff() {
    addCard(Zone.HAND, playerA, arcades);
    addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
    addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
    addCard(Zone.BATTLEFIELD, playerA, jadeOrb);
    castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, arcades);

    setStrictChooseMode(true);
    setStopAt(1, PhaseStep.END_COMBAT);
    execute();

    assertPermanentCount(playerA, 5);
    assertTapped(jadeOrb, true);
    assertAbility(playerA, arcades, HexproofAbility.getInstance(), true);

    setStopAt(4, PhaseStep.UPKEEP);
    execute();

    assertAbility(playerA, arcades, HexproofAbility.getInstance(), false);
  }

  @Test
  public void twoOrbs() {
    addCard(Zone.HAND, playerA, arcades);
    addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
    addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
    addCard(Zone.BATTLEFIELD, playerA, jadeOrb, 2);
    castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, arcades);
    //setChoice(playerA, "When you spend"); // order identical triggers

    setStrictChooseMode(false); // trigger order is problematic
    setStopAt(1, PhaseStep.END_COMBAT);
    execute();

    assertPermanentCount(playerA, 5);
    assertTapped(jadeOrb, true);
    assertCounterCount(arcades, CounterType.P1P1, 2);
    assertAbility(playerA, arcades, HexproofAbility.getInstance(), true);
  }

  @Test
  public void comboMowuAndMaskwoodNexus() {
    addCard(Zone.HAND, playerA, mowu);
    addCard(Zone.BATTLEFIELD, playerA, "Maskwood Nexus", 1);
    addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
    addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
    addCard(Zone.BATTLEFIELD, playerA, jadeOrb);
    castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, mowu);

    setStrictChooseMode(true);
    setStopAt(1, PhaseStep.END_COMBAT);
    execute();
    
    assertTapped(jadeOrb, true);
    assertCounterCount(mowu, CounterType.P1P1, 2);
    assertAbility(playerA, mowu, HexproofAbility.getInstance(), true);
  }
}
