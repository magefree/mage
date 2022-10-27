package org.mage.test.cards.mana;

import mage.abilities.mana.ManaOptions;
import mage.constants.ManaType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import static org.mage.test.utils.ManaOptionsTestUtils.assertManaOptions;

/**
 * @author escplan9, JayDi85
 */
public class NagaVitalistTest extends CardTestPlayerBase {

    /*
    Naga Vitalist 1G
    Creature - Naga Druid 1/2
    T: Add one mana of any type that a land you control could produce.
     */
    private final String nagaVitalist = "Naga Vitalist";

    /*
        Gift of Paradise 2G
        Enchantment - Aura
        Enchant - Land
        When Gift of Paradise enters the battlefield, you gain 3 life.
        Enchanted land has "T: Add two mana of any one color."
     */
    private final String giftParadise = "Gift of Paradise";

    @Test
    public void nagaVitalist_GiftOfParadiseCanAnyColor() {
        // Mana pools don't empty as steps and phases end.
        addCard(Zone.BATTLEFIELD, playerA, "Upwelling");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.HAND, playerA, giftParadise);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);

        // manual mana cost, cause auto cost can get swamp to pay
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, giftParadise, "Swamp");

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        Assert.assertEquals("mana variations don't fit", 1, manaOptions.size());
        assertManaOptions("{Any}{Any}", manaOptions);
    }

    public void nagaVitalist_GiftOfParadisesLandCanGiveAnyColorToNaga_Setup(int giftCastTurn, int nagaManaTapTurn, String nagaManaTapColor) {
        // test errors on enchanted ability do not apply for "any mana search" on different steps

        addCard(Zone.BATTLEFIELD, playerA, "Upwelling");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.HAND, playerA, giftParadise);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, nagaVitalist, 1);

        // cast and enchant swamp land to any color
        activateManaAbility(giftCastTurn, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}");
        activateManaAbility(giftCastTurn, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}");
        activateManaAbility(giftCastTurn, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}");
        castSpell(giftCastTurn, PhaseStep.PRECOMBAT_MAIN, playerA, giftParadise, "Swamp");

        // activate red mana (by any from enchanted land)
        activateManaAbility(nagaManaTapTurn, PhaseStep.POSTCOMBAT_MAIN, playerA, "{T}: Add one mana of any");
        setChoice(playerA, nagaManaTapColor);

        setStopAt(nagaManaTapTurn, PhaseStep.POSTCOMBAT_MAIN);
        execute();
    }

    @Test
    public void nagaVitalist_GiftOfParadisesLandCanGiveAnyColorToNaga_SameStep1() {
        nagaVitalist_GiftOfParadisesLandCanGiveAnyColorToNaga_Setup(1, 1, "Red");

        //logger.info(playerA.getManaPool().getMana().toString());
        //logger.info(playerA.getManaAvailable(currentGame).toString());
        //for(Permanent perm: currentGame.getBattlefield().getAllActivePermanents(playerA.getId())){
        //    logger.info(perm.getIdName() + ": " + perm.getAbilities().toString());
        //}
        assertTapped("Forest", true);
        assertTapped(giftParadise, false);
        assertTapped("Swamp", false);
        assertTapped(nagaVitalist, true);
        Assert.assertEquals(1, playerA.getManaPool().get(ManaType.RED));
    }

    @Test
    public void nagaVitalist_GiftOfParadisesLandCanGiveAnyColorToNaga_DiffStep1() {
        nagaVitalist_GiftOfParadisesLandCanGiveAnyColorToNaga_Setup(1, 2, "Red");

        assertTapped("Forest", true);
        assertTapped(giftParadise, false);
        assertTapped("Swamp", false);
        assertTapped(nagaVitalist, true);
        Assert.assertEquals(1, playerA.getManaPool().get(ManaType.RED));
    }

    @Test
    public void nagaVitalist_GiftOfParadisesLandCanGiveAnyColorToNaga_SameStep3() {
        nagaVitalist_GiftOfParadisesLandCanGiveAnyColorToNaga_Setup(3, 3, "Red");

        assertTapped("Forest", true);
        assertTapped(giftParadise, false);
        assertTapped("Swamp", false);
        assertTapped(nagaVitalist, true);
        Assert.assertEquals(1, playerA.getManaPool().get(ManaType.RED));
    }

    @Test
    public void nagaVitalist_GiftOfParadisesLandCanGiveAnyColorToNaga_DiffStep2() {
        nagaVitalist_GiftOfParadisesLandCanGiveAnyColorToNaga_Setup(3, 4, "Red");

        assertTapped("Forest", true);
        assertTapped(giftParadise, false);
        assertTapped("Swamp", false);
        assertTapped(nagaVitalist, true);
        Assert.assertEquals(1, playerA.getManaPool().get(ManaType.RED));
    }

    /*
     Reported bug (issue #3315)
    Naga Vitalist could not produce any color mana with a Gift of Paradise enchanted on a forest. All lands on board were forests.
     */
    @Test
    public void nagaVitalist_InteractionGiftOfParadise() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, nagaVitalist);
        addCard(Zone.BATTLEFIELD, playerA, "Upwelling"); // mana pools do not empty at the end of phases or turns
        addCard(Zone.HAND, playerA, giftParadise);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, giftParadise, "Forest");

        activateManaAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add one mana of any");
        setChoice(playerA, "Red");

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerA, 23); // gift of paradise ETB
        assertTapped(nagaVitalist, true);
        assertTapped(giftParadise, false);
        assertTapped("Forest", false);
        Assert.assertEquals("one red mana has to be in the mana pool", 1, playerA.getManaPool().get(ManaType.RED));
    }
}
