package org.mage.test.cards.single.blb;

import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class BelloBardOfTheBramblesTest extends CardTestPlayerBase {

    private static final String bello = "Bello, Bard of the Brambles";
    private static final String ashaya = "Ashaya, Soul of the Wild"; // Has type addition for lands
    private static final String cityOnFire = "City on Fire"; // Is a 4+ cmc non-creature, non-aura enchantment
    private static final String thranDynamo = "Thran Dynamo"; // Is a 4+ cmc non-creature, non-equipment artifact
    private static final String aggravatedAssault = "Aggravated Assault"; // Is a 3 cmc non-creature, non-aura enchantment
    private static final String abandonedSarcophagus = "Abandoned Sarcophagus"; // Is a 3 cmc non-creature, non-equipment artifact
    private static final String bearUmbra = "Bear Umbra"; // Is a 4 cmc non-creature, aura enchantment
    private static final String tangleweave = "Tangleweave Armor"; // Is a 4 cmc non-creature, equipment artifact
    private static final String forest = "Forest";
    private static final String mountain = "Mountain";
    private static final String bear = "Grizzly Bears";

    // During your turn, each non-Equipment artifact and non-Aura enchantment you control with mana value 4 or greater is a 4/4 Elemental creature in addition to its other types and has indestructible, haste, and "Whenever this creature deals combat damage to a player, draw a card."
    // City on Fire should become a 4/4 Elemental creature with indestructible, haste, and "Whenever this creature deals combat damage to a player, draw a card."
    // Thran Dynamo should become a 4/4 Elemental creature with indestructible, haste, and "Whenever this creature deals combat damage to a player, draw a card."
    @Test
    public void testBello() {
        initBelloTest();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bello);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        ensurePermanentHasBelloEffects(cityOnFire);
        ensurePermanentHasBelloEffects(thranDynamo);
    }

    // Ensures that overlapping land and creature type addition effects are handled properly
    // This was an issue encountered between Ashaya and Bello
    // While both were on the field, creatures weren't Forests, and artifacts and enchantements that met Bello's criteria weren't 4/4 Elementals
    @Test
    public void testBelloTypeAddition(){
        initBelloTest();
        addCard(Zone.BATTLEFIELD, playerA, ashaya);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bello);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // Assert that Ashaya is not affected by Bello
        assertType(ashaya, CardType.CREATURE, SubType.ELEMENTAL);
        assertType(ashaya, CardType.LAND, SubType.FOREST);
        assertAbility(playerA, ashaya, new GreenManaAbility(), true);
        ensurePermanentDoesNotHaveBelloEffects(ashaya);

        // Assert that City on Fire is affected by Bello and Ashaya
        assertType(cityOnFire, CardType.LAND, SubType.FOREST);
        ensurePermanentHasBelloEffects(cityOnFire);
    }

    // Ensures that Bello does not affect Equipment artifacts
    // Tangleweave Armor should not be affected by Bello
    @Test
    public void testBelloEquipment(){
        initBelloTest();
        addCard(Zone.BATTLEFIELD, playerA, tangleweave);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bello);
        setStopAt(1, PhaseStep.END_TURN);

        execute();

        // Assert that Equipment is not affected by Bello
        ensurePermanentDoesNotHaveBelloEffects(tangleweave);
    }

    // Ensures that Bello does not affect Aura enchantments
    // Aura should not be affected by Bello
    @Test
    public void testBelloAura(){
        initBelloTest();
        addCard(Zone.BATTLEFIELD, playerA, bear);
        addCard(Zone.HAND, playerA, bearUmbra);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bearUmbra, bear);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, bello);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // Assert that Aura is not affected by Bello
        assertAttachedTo(playerA, bearUmbra, bear, true);
        ensurePermanentDoesNotHaveBelloEffects(bearUmbra);
    }

    // Ensures that Bello does not affect less-than-3 cmc non-creature, non-aura enchantments
    // Aggravated Assault should not be affected by Bello
    @Test
    public void testBelloLessThanFourCmcEnchantment(){
        initBelloTest();
        addCard(Zone.BATTLEFIELD, playerA, aggravatedAssault);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bello);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // Assert that Aggravated Assault is not affected by Bello
        ensurePermanentDoesNotHaveBelloEffects(aggravatedAssault);
    }

    // Ensures that Bello does not affect less-than-3 cmc non-creature, non-equipment artifacts
    // Abandoned Sarcophagus should not be affected by Bello
    @Test
    public void testBelloLessThanFourCmcArtifact(){
        initBelloTest();
        addCard(Zone.BATTLEFIELD, playerA, abandonedSarcophagus);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bello);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        // Assert that Abandoned Sarcophagus is not affected by Bello
        ensurePermanentDoesNotHaveBelloEffects(abandonedSarcophagus);
    }

    private void initBelloTest(){
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, forest, 10);
        addCard(Zone.BATTLEFIELD, playerA, mountain, 10);
        addCard(Zone.BATTLEFIELD, playerA, cityOnFire);
        addCard(Zone.BATTLEFIELD, playerA, thranDynamo);

        addCard(Zone.HAND, playerA, bello);
    }

    private void ensurePermanentHasBelloEffects(String permanentName){
        assertType(permanentName, CardType.CREATURE, SubType.ELEMENTAL);
        assertPowerToughness(playerA, permanentName, 4, 4);
        assertAbility(playerA, permanentName, IndestructibleAbility.getInstance(), true);
        assertAbility(playerA, permanentName, HasteAbility.getInstance(), true);
        assertAbility(playerA, permanentName, new DealsCombatDamageToAPlayerTriggeredAbility(new DrawCardSourceControllerEffect(1), false), true);
    }

    private void ensurePermanentDoesNotHaveBelloEffects(String permanentName){
        assertAbility(playerA, permanentName, IndestructibleAbility.getInstance(), false);
        assertAbility(playerA, permanentName, HasteAbility.getInstance(), false);
        assertAbility(playerA, permanentName, new DealsCombatDamageToAPlayerTriggeredAbility(new DrawCardSourceControllerEffect(1), false), false);
    }


}
