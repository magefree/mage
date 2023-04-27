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

  @Test
  public void ManaCost() {
    addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
    addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
    addCard(Zone.HAND, playerA, jadeOrb);
    castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, jadeOrb);
    execute();
    assertPermanentCount(playerA, 4);
  }

  @Test
  public void ManaAbility() {
    addCard(Zone.HAND, playerA, "Ancient Bronze Dragon");
    addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
    addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
    addCard(Zone.BATTLEFIELD, playerA, jadeOrb);
    castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ancient Bronze Dragon");
    execute();
    assertPermanentCount(playerA, 8);
    assertTapped(jadeOrb, true);
  }

  @Test
  public void ManaUsedEffects() {
    addCard(Zone.HAND, playerA, "Arcades, the Strategist");
    addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
    addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
    addCard(Zone.BATTLEFIELD, playerA, jadeOrb);
    castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Arcades, the Strategist");
    execute();
    setStopAt(1, PhaseStep.END_COMBAT);
    assertPermanentCount(playerA, 5);
    assertTapped(jadeOrb, true);
    assertCounterCount("Arcades, the Strategist", CounterType.P1P1, 1);
    assertAbility(playerA, "Arcades, the Strategist", HexproofAbility.getInstance(), true);
  }

  @Test
  public void HexproofDropOff() {
    addCard(Zone.HAND, playerA, "Arcades, the Strategist");
    addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
    addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
    addCard(Zone.BATTLEFIELD, playerA, jadeOrb);
    castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Arcades, the Strategist");
    execute();
    setStopAt(1, PhaseStep.END_COMBAT);
    assertPermanentCount(playerA, 5);
    assertTapped(jadeOrb, true);
    assertAbility(playerA, "Arcades, the Strategist", HexproofAbility.getInstance(), true);
    setStopAt(4, PhaseStep.UPKEEP);
    execute();
    assertAbility(playerA, "Arcades, the Strategist", HexproofAbility.getInstance(), false);
  }

  @Test
  public void TwoOrbs() {
    addCard(Zone.HAND, playerA, "Arcades, the Strategist");
    addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
    addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
    addCard(Zone.BATTLEFIELD, playerA, jadeOrb, 2);
    castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Arcades, the Strategist");
    execute();
    setStopAt(1, PhaseStep.END_COMBAT);
    assertPermanentCount(playerA, 5);
    assertTapped(jadeOrb, true);
    assertCounterCount("Arcades, the Strategist", CounterType.P1P1, 2);
    assertAbility(playerA, "Arcades, the Strategist", HexproofAbility.getInstance(), true);
  }

  @Test
  public void MowuAndMaskwoodNexusCombo() {
    addCard(Zone.HAND, playerA, "Mowu, Loyal Companion");
    addCard(Zone.BATTLEFIELD, playerA, "Maskwood Nexus", 1);
    addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
    addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
    addCard(Zone.BATTLEFIELD, playerA, jadeOrb);
    castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mowu, Loyal Companion");
    execute();
    setStopAt(1, PhaseStep.END_COMBAT);
    assertTapped(jadeOrb, true);
    assertCounterCount("Mowu, Loyal Companion", CounterType.P1P1, 2);
    assertAbility(playerA, "Mowu, Loyal Companion", HexproofAbility.getInstance(), true);
  }
}
