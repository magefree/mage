package org.mage.test.cards.enchantments;

import mage.constants.EmptyNames;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * 303.4f
 * If an Aura is entering the battlefield under a player’s control by any means other than by resolving as an Aura
 * spell, and the effect putting it onto the battlefield doesn’t specify the object or player the Aura will enchant,
 * that player chooses what it will enchant as the Aura enters the battlefield. The player must choose a legal object
 * or player according to the Aura’s enchant ability and any other applicable effects.
 * <p>
 * 303.4g
 * If an Aura is entering the battlefield and there is no legal object or player for it to enchant, the Aura remains
 * in its current zone, unless that zone is the stack. In that case, the Aura is put into its owner’s graveyard instead
 * of entering the battlefield. If the Aura is a token, it isn’t created.
 * <p>
 * 708.2a
 * If a face-up permanent is turned face down by a spell or ability that doesn’t list any characteristics for that
 * object, it becomes a 2/2 face-down creature with no text, no name, no subtypes, and no mana cost. A permanent that
 * enters the battlefield face down also has these characteristics unless otherwise specified by the effect that put it
 * onto the battlefield face down or allowed it to be cast face down. These values are the copiable values of that
 * object’s characteristics.
 * <p>
 * 712.14a
 * If a spell or ability puts a double-faced card onto the battlefield “transformed” or “converted,” it enters the
 * battlefield with its back face up. If a player is instructed to put a card that isn’t a double-faced card onto the
 * battlefield transformed or converted, that card stays in its current zone.
 *
 * @author JayDi85
 */
public class AuraPutOntoBattlefieldTest extends CardTestPlayerBase {

    @Test
    public void test_PutOntoBattlefield_GlasswingGrace() {
        // Return all enchantment cards from your graveyard to the battlefield.
        addCard(Zone.HAND, playerA, "Replenish"); // {3}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        //
        addCard(Zone.GRAVEYARD, playerA, "Glasswing Grace"); // modal double-faced aura, +2/+2, flying, lifelink
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Replenish");
        setChoice(playerA, "Grizzly Bears"); // creature to enchant

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAttachedTo(playerA, "Glasswing Grace", "Grizzly Bears", true);
        assertPowerToughness(playerA, "Grizzly Bears", 2 + 2, 2 + 2);
    }

    @Test
    public void test_PutOntoBattlefield_JourneyToEternity() {
        // Return all artifact and enchantment cards from all graveyards to the battlefield under their owners' control.
        addCard(Zone.HAND, playerA, "Open the Vaults"); // {4}{W}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);
        //
        addCard(Zone.GRAVEYARD, playerA, "Journey to Eternity"); // transforming double-faced aura, enchant creature you control
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Open the Vaults");
        setChoice(playerA, "Grizzly Bears"); // creature to enchant

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAttachedTo(playerA, "Journey to Eternity", "Grizzly Bears", true);
    }

    @Test
    public void test_PutOntoBattlefieldFromLibrary_StrengthOfTheHarvest() {
        // https://github.com/magefree/mage/issues/15547

        // When Academy Rector dies, you may exile it. If you do, search your library for an enchantment card,
        // put that card onto the battlefield, then shuffle.
        addCard(Zone.BATTLEFIELD, playerA, "Academy Rector");
        //
        addCustomEffect_TargetDestroy(playerA);
        addCard(Zone.LIBRARY, playerA, "Strength of the Harvest"); // modal double-faced aura, +1/+1 for each creature and/or enchantment you control
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target destroy", "Academy Rector");
        setChoice(playerA, true); // exile Academy Rector
        addTarget(playerA, "Strength of the Harvest"); // enchantment to put
        setChoice(playerA, "Grizzly Bears"); // creature to enchant

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, "Academy Rector", 1);
        assertAttachedTo(playerA, "Strength of the Harvest", "Grizzly Bears", true);
        assertPowerToughness(playerA, "Grizzly Bears", 2 + 2, 2 + 2); // bear and aura
    }

    @Test
    public void test_PutOntoBattlefieldWithoutLegalObject_GlasswingGrace() {
        // Return all enchantment cards from your graveyard to the battlefield.
        addCard(Zone.HAND, playerA, "Replenish"); // {3}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        //
        addCard(Zone.GRAVEYARD, playerA, "Glasswing Grace"); // modal double-faced aura, enchant creature

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Replenish");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // no creature to enchant, so the aura remains in the graveyard
        assertPermanentCount(playerA, "Glasswing Grace", 0);
        assertGraveyardCount(playerA, "Glasswing Grace", 1);
    }

    @Test
    public void test_PutOntoBattlefieldAgain_HolyStrength() {
        // Return each Aura card from your graveyard to the battlefield. Only creatures can be enchanted this way.
        addCard(Zone.HAND, playerA, "Retether"); // {3}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        // Return all enchantment cards from your graveyard to the battlefield.
        addCard(Zone.HAND, playerA, "Replenish"); // {3}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        //
        addCustomEffect_TargetDestroy(playerA);
        addCard(Zone.GRAVEYARD, playerA, "Holy Strength"); // aura, enchant creature, +1/+2
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion"); // lion 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Retether");
        setChoice(playerA, "Grizzly Bears"); // creature to enchant
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after first return", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Holy Strength", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target destroy", "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkGraveyardCount("aura without creature", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Holy Strength", 1);

        // the second return must ask for a new creature
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Replenish");
        setChoice(playerA, "Silvercoat Lion"); // creature to enchant

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAttachedTo(playerA, "Holy Strength", "Silvercoat Lion", true);
        assertPowerToughness(playerA, "Silvercoat Lion", 2 + 1, 2 + 2);
    }

    @Test
    public void test_PutOntoBattlefieldAttached_AcademyResearchers() {
        // When Academy Researchers enters, you may put an Aura card from your hand onto the battlefield attached to Academy Researchers.
        addCard(Zone.HAND, playerA, "Academy Researchers"); // {1}{U}{U}, 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        //
        addCard(Zone.HAND, playerA, "Glasswing Grace"); // modal double-faced aura, enchant creature, +2/+2, flying, lifelink

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Academy Researchers");
        setChoice(playerA, true); // put an aura
        setChoice(playerA, "Glasswing Grace"); // aura to put

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAttachedTo(playerA, "Glasswing Grace", "Academy Researchers", true);
        assertPowerToughness(playerA, "Academy Researchers", 2 + 2, 2 + 2);
    }

    @Test
    public void test_PutOntoBattlefieldAttached_ArcanumWings() {
        // Aura swap {2}{U} (Exchange this Aura with an Aura card in your hand.)
        addCard(Zone.HAND, playerA, "Arcanum Wings"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2 + 3);
        //
        addCard(Zone.HAND, playerA, "Glasswing Grace"); // modal double-faced aura, enchant creature, +2/+2, flying, lifelink
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Arcanum Wings", "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Aura swap");
        setChoice(playerA, "Glasswing Grace"); // aura to exchange with

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, "Arcanum Wings", 1);
        assertAttachedTo(playerA, "Glasswing Grace", "Grizzly Bears", true);
        assertPowerToughness(playerA, "Grizzly Bears", 2 + 2, 2 + 2);
    }

    @Test
    public void test_PutOntoBattlefieldAttached_AuratouchedMage() {
        // When Auratouched Mage enters, search your library for an Aura card that could enchant it. If Auratouched
        // Mage is still on the battlefield, put that Aura card onto the battlefield attached to it.
        addCard(Zone.HAND, playerA, "Auratouched Mage"); // {5}{W}, 3/3
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);
        //
        addCard(Zone.LIBRARY, playerA, "Glasswing Grace"); // modal double-faced aura, enchant creature, +2/+2, flying, lifelink

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Auratouched Mage");
        addTarget(playerA, "Glasswing Grace"); // aura to search

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAttachedTo(playerA, "Glasswing Grace", "Auratouched Mage", true);
        assertPowerToughness(playerA, "Auratouched Mage", 3 + 2, 3 + 2);
    }

    @Test
    public void test_PutOntoBattlefieldAttached_BoonweaverGiant() {
        // When Boonweaver Giant enters, you may search your graveyard, hand, and/or library for an Aura card
        // and put it onto the battlefield attached to Boonweaver Giant.
        addCard(Zone.HAND, playerA, "Boonweaver Giant"); // {6}{W}, 4/4
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);
        //
        addCard(Zone.GRAVEYARD, playerA, "Glasswing Grace"); // modal double-faced aura, enchant creature, +2/+2, flying, lifelink

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Boonweaver Giant");
        setChoice(playerA, true); // search for an aura
        setChoice(playerA, true); // search graveyard
        setChoice(playerA, "Glasswing Grace"); // aura to put

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAttachedTo(playerA, "Glasswing Grace", "Boonweaver Giant", true);
        assertPowerToughness(playerA, "Boonweaver Giant", 4 + 2, 4 + 2);
    }

    @Test
    public void test_PutOntoBattlefieldAttached_BrunaLightOfAlabaster() {
        // Whenever Bruna, Light of Alabaster attacks or blocks, you may attach to it any number of Auras on the
        // battlefield and you may put onto the battlefield attached to it any number of Aura cards that could enchant
        // it from your graveyard and/or hand.
        addCard(Zone.BATTLEFIELD, playerA, "Bruna, Light of Alabaster"); // 5/5
        //
        addCard(Zone.GRAVEYARD, playerA, "Glasswing Grace"); // modal double-faced aura, enchant creature, +2/+2, flying, lifelink

        attack(1, playerA, "Bruna, Light of Alabaster");
        setChoice(playerA, true); // use the trigger
        setChoice(playerA, true); // attach from graveyard
        setChoice(playerA, "Glasswing Grace"); // aura to put

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertAttachedTo(playerA, "Glasswing Grace", "Bruna, Light of Alabaster", true);
        assertLife(playerB, 20 - 5 - 2);
    }

    @Test
    public void test_PutOntoBattlefieldAttached_CassHandOfVengeance() {
        // Whenever Cass or another creature you control dies, if it was enchanted or equipped, return any number of
        // Aura cards that were attached to it from your graveyard to the battlefield attached to target creature
        addCard(Zone.BATTLEFIELD, playerA, "Cass, Hand of Vengeance"); // 4/3
        //
        addCustomEffect_TargetDestroy(playerA);
        addCard(Zone.HAND, playerA, "Glasswing Grace"); // {3}{W/B}{W/B}, modal double-faced aura, enchant creature, +2/+2, flying, lifelink
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion"); // lion 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Glasswing Grace", "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target destroy", "Grizzly Bears");
        addTarget(playerA, "Silvercoat Lion"); // creature for the returned auras
        addTarget(playerA, "Glasswing Grace"); // aura to return

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAttachedTo(playerA, "Glasswing Grace", "Silvercoat Lion", true);
        assertPowerToughness(playerA, "Silvercoat Lion", 2 + 2, 2 + 2);
    }

    @Test
    public void test_PutOntoBattlefieldAttached_DanithaBenaliasHope() {
        // When Danitha, Benalia's Hope enters, you may put an Aura or Equipment card from your hand or graveyard
        // onto the battlefield attached to Danitha.
        addCard(Zone.HAND, playerA, "Danitha, Benalia's Hope"); // {4}{W}, 4/4
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        //
        addCard(Zone.GRAVEYARD, playerA, "Glasswing Grace"); // modal double-faced aura, enchant creature, +2/+2, flying, lifelink

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Danitha, Benalia's Hope");
        setChoice(playerA, true); // put an aura
        setChoice(playerA, "Glasswing Grace"); // aura to put

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAttachedTo(playerA, "Glasswing Grace", "Danitha, Benalia's Hope", true);
        assertPowerToughness(playerA, "Danitha, Benalia's Hope", 4 + 2, 4 + 2);
    }

    @Test
    public void test_PutOntoBattlefieldAttached_Evershrike() {
        // {X}{W/B}{W/B}: Return Evershrike from your graveyard to the battlefield. You may put an Aura card with
        // mana value X or less from your hand onto the battlefield attached to it. If you don't, exile Evershrike.
        addCard(Zone.GRAVEYARD, playerA, "Evershrike"); // 2/2, gets +2/+2 for each Aura attached to it
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5 + 2);
        //
        addCard(Zone.HAND, playerA, "Glasswing Grace"); // modal double-faced aura, enchant creature, +2/+2, flying, lifelink

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{X}{W/B}{W/B}: Return");
        setChoice(playerA, "X=5");
        setChoice(playerA, true); // put an aura
        setChoice(playerA, "Glasswing Grace"); // aura to put

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAttachedTo(playerA, "Glasswing Grace", "Evershrike", true);
        assertPowerToughness(playerA, "Evershrike", 2 + 2 + 2, 2 + 2 + 2);
    }

    @Test
    public void test_PutOntoBattlefieldAttached_ForumFilibuster() {
        // At the beginning of your upkeep, create a 2/1 white and black Inkling creature token with flying. When you
        // do, return up to one target Aura or Equipment card from your graveyard to the battlefield attached to that token.
        addCard(Zone.BATTLEFIELD, playerA, "Forum Filibuster");
        //
        addCard(Zone.GRAVEYARD, playerA, "Glasswing Grace"); // modal double-faced aura, enchant creature, +2/+2, flying, lifelink

        addTarget(playerA, "Glasswing Grace"); // aura to return

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertAttachedTo(playerA, "Glasswing Grace", "Inkling Token", true);
        assertPowerToughness(playerA, "Inkling Token", 2 + 2, 1 + 2);
    }

    @Test
    public void test_PutOntoBattlefieldAttached_HakimLoreweaver() {
        // {U}{U}: Return target Aura card from your graveyard to the battlefield attached to Hakim, Loreweaver.
        // Activate only during your upkeep and only if Hakim isn't enchanted.
        addCard(Zone.BATTLEFIELD, playerA, "Hakim, Loreweaver"); // 2/4
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        addCard(Zone.GRAVEYARD, playerA, "Glasswing Grace"); // modal double-faced aura, enchant creature, +2/+2, flying, lifelink

        activateAbility(1, PhaseStep.UPKEEP, playerA, "{U}{U}: Return", "Glasswing Grace");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertAttachedTo(playerA, "Glasswing Grace", "Hakim, Loreweaver", true);
        assertPowerToughness(playerA, "Hakim, Loreweaver", 2 + 2, 4 + 2);
    }

    @Test
    public void test_PutOntoBattlefieldAttached_HolyAvenger() {
        // Whenever equipped creature deals combat damage, you may put an Aura card from your hand onto the battlefield
        // attached to it.
        addCard(Zone.BATTLEFIELD, playerA, "Holy Avenger");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3); // for equip {2}{W}
        //
        addCard(Zone.HAND, playerA, "Glasswing Grace"); // modal double-faced aura, enchant creature, +2/+2, flying, lifelink
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        attack(1, playerA, "Grizzly Bears");
        setChoice(playerA, "Glasswing Grace"); // aura to put after the first strike damage

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertAttachedTo(playerA, "Glasswing Grace", "Grizzly Bears", true);
        assertLife(playerB, 20 - 2 - (2 + 2)); // double strike: first strike damage, then with the aura
    }

    @Test
    public void test_PutOntoBattlefieldAttached_IridescentDrake() {
        // When Iridescent Drake enters, put target Aura card from a graveyard onto the battlefield under your control
        // attached to Iridescent Drake.
        addCard(Zone.HAND, playerA, "Iridescent Drake"); // {3}{U}, 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        //
        addCard(Zone.GRAVEYARD, playerA, "Glasswing Grace"); // modal double-faced aura, enchant creature, +2/+2, flying, lifelink

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Iridescent Drake");
        addTarget(playerA, "Glasswing Grace"); // aura to put

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAttachedTo(playerA, "Glasswing Grace", "Iridescent Drake", true);
        assertPowerToughness(playerA, "Iridescent Drake", 2 + 2, 2 + 2);
    }

    @Test
    public void test_PutOntoBattlefieldAttached_LiberatedLivestock() {
        // When Liberated Livestock dies, create a 1/1 white Cat creature token with lifelink, a 1/1 white Bird creature
        // token with flying, and a 2/4 white Ox creature token. For each of those tokens, you may put an Aura card
        // from your hand and/or graveyard onto the battlefield attached to it.
        addCard(Zone.BATTLEFIELD, playerA, "Liberated Livestock"); // 4/6
        //
        addCustomEffect_TargetDestroy(playerA);
        addCard(Zone.GRAVEYARD, playerA, "Glasswing Grace"); // modal double-faced aura, enchant creature, +2/+2, flying, lifelink

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target destroy", "Liberated Livestock");
        addTarget(playerA, "Glasswing Grace"); // aura for the first token

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAttachedTo(playerA, "Glasswing Grace", "Cat Token", true);
        assertPowerToughness(playerA, "Cat Token", 1 + 2, 1 + 2);
    }

    @Test
    public void test_PutOntoBattlefieldAttached_LightPawsEmperorsVoice() {
        // Whenever an Aura you control enters, if you cast it, you may search your library for an Aura card with mana
        // value less than or equal to that Aura and with a different name than each Aura you control, put that card
        // onto the battlefield attached to Light-Paws, Emperor's Voice, then shuffle.
        addCard(Zone.BATTLEFIELD, playerA, "Light-Paws, Emperor's Voice"); // 2/2
        //
        addCard(Zone.HAND, playerA, "Ancestral Mask"); // {2}{G}, aura
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.LIBRARY, playerA, "Strength of the Harvest"); // {2}{G/W}, modal double-faced aura, enchant creature

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ancestral Mask", "Light-Paws, Emperor's Voice");
        setChoice(playerA, true); // search
        addTarget(playerA, "Strength of the Harvest"); // aura to put

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAttachedTo(playerA, "Strength of the Harvest", "Light-Paws, Emperor's Voice", true);
    }

    @Test
    public void test_PutOntoBattlefieldAttached_MantleOfTheAncients() {
        // When Mantle of the Ancients enters, return any number of target Aura and/or Equipment cards from your
        // graveyard to the battlefield attached to enchanted creature.
        addCard(Zone.HAND, playerA, "Mantle of the Ancients"); // {3}{W}{W}, aura, enchant creature you control
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        //
        addCard(Zone.GRAVEYARD, playerA, "Glasswing Grace"); // modal double-faced aura, enchant creature, +2/+2, flying, lifelink
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mantle of the Ancients", "Grizzly Bears");
        addTarget(playerA, "Glasswing Grace"); // aura to return

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAttachedTo(playerA, "Glasswing Grace", "Grizzly Bears", true);
        assertPowerToughness(playerA, "Grizzly Bears", 2 + 2 + 2, 2 + 2 + 2); // aura + mantle's +1/+1 for 2 auras
    }

    @Test
    public void test_PutOntoBattlefieldAttached_NomadMythmaker() {
        // {W}, {T}: Put target Aura card from a graveyard onto the battlefield under your control attached to a
        // creature you control.
        addCard(Zone.BATTLEFIELD, playerA, "Nomad Mythmaker"); // 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        //
        addCard(Zone.GRAVEYARD, playerA, "Glasswing Grace"); // modal double-faced aura, enchant creature, +2/+2, flying, lifelink
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{W}, {T}: Put", "Glasswing Grace");
        setChoice(playerA, "Grizzly Bears"); // creature to attach to

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAttachedTo(playerA, "Glasswing Grace", "Grizzly Bears", true);
        assertPowerToughness(playerA, "Grizzly Bears", 2 + 2, 2 + 2);
    }

    @Test
    public void test_PutOntoBattlefieldAttached_OneLastJob() {
        // + {1} -- Return target Aura or Equipment card from your graveyard to the battlefield attached to a creature
        // you control.
        addCard(Zone.HAND, playerA, "One Last Job"); // {2}{W} + {1}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3 + 1);
        //
        addCard(Zone.GRAVEYARD, playerA, "Glasswing Grace"); // modal double-faced aura, enchant creature, +2/+2, flying, lifelink
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "One Last Job", "Glasswing Grace");
        setModeChoice(playerA, "3");
        setModeChoice(playerA, TestPlayer.MODE_SKIP);
        setChoice(playerA, "Grizzly Bears"); // creature to attach to

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAttachedTo(playerA, "Glasswing Grace", "Grizzly Bears", true);
        assertPowerToughness(playerA, "Grizzly Bears", 2 + 2, 2 + 2);
    }

    @Test
    public void test_PutOntoBattlefieldAttached_Retether() {
        // Return each Aura card from your graveyard to the battlefield. Only creatures can be enchanted this way.
        addCard(Zone.HAND, playerA, "Retether"); // {3}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        //
        addCard(Zone.GRAVEYARD, playerA, "Journey to Eternity"); // transforming double-faced aura, enchant creature you control
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Retether");
        setChoice(playerA, "Grizzly Bears"); // creature to enchant

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAttachedTo(playerA, "Journey to Eternity", "Grizzly Bears", true);
    }

    @Test
    public void test_PutOntoBattlefieldAttached_SovereignsOfLostAlara() {
        // Whenever a creature you control attacks alone, you may search your library for an Aura card that could
        // enchant that creature, put it onto the battlefield attached to that creature, then shuffle.
        addCard(Zone.BATTLEFIELD, playerA, "Sovereigns of Lost Alara"); // exalted
        //
        addCard(Zone.LIBRARY, playerA, "Glasswing Grace"); // modal double-faced aura, enchant creature, +2/+2, flying, lifelink
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2

        attack(1, playerA, "Grizzly Bears");
        setChoice(playerA, "Whenever a creature you control attacks alone, you may search"); // trigger order
        setChoice(playerA, true); // search
        addTarget(playerA, "Glasswing Grace"); // aura to put

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertAttachedTo(playerA, "Glasswing Grace", "Grizzly Bears", true);
        assertLife(playerB, 20 - (2 + 1 + 2)); // bear + exalted + aura
    }

    @Test
    public void test_PutOntoBattlefieldAttached_StormHerald() {
        // When Storm Herald enters, return any number of Aura cards from your graveyard to the battlefield attached to
        // creatures you control. Exile those Auras at the beginning of your next end step.
        addCard(Zone.HAND, playerA, "Storm Herald"); // {2}{R}, 3/2
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        //
        addCard(Zone.GRAVEYARD, playerA, "Glasswing Grace"); // modal double-faced aura, enchant creature, +2/+2, flying, lifelink
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Storm Herald");
        addTarget(playerA, "Glasswing Grace"); // aura to return
        setChoice(playerA, "Grizzly Bears"); // creature to attach to
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after return", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Glasswing Grace", 1);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        // exiled at the next end step
        assertPermanentCount(playerA, "Glasswing Grace", 0);
        assertExileCount(playerA, "Glasswing Grace", 1);
    }

    @Test
    public void test_PutOntoBattlefieldAttached_ArachnusSpinner() {
        // Tap an untapped Spider you control: Search your graveyard and/or library for a card named Arachnus Web and
        // put it onto the battlefield attached to target creature. If you search your library this way, shuffle it.
        addCard(Zone.BATTLEFIELD, playerA, "Arachnus Spinner"); // spider 5/7
        //
        addCard(Zone.GRAVEYARD, playerA, "Arachnus Web"); // aura, enchant creature, can't attack or block
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // bear 2/2

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tap an untapped Spider", "Grizzly Bears");
        setChoice(playerA, "Arachnus Spinner"); // spider to tap
        setChoice(playerA, true); // search graveyard
        setChoice(playerA, "Arachnus Web"); // aura to put

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAttachedTo(playerB, "Arachnus Web", "Grizzly Bears", true);
    }

    @Test
    public void test_PutOntoBattlefieldAttached_DragonShadow() {
        // When a creature with mana value 6 or greater enters, you may return Dragon Shadow from your graveyard
        // to the battlefield attached to that creature.
        addCard(Zone.GRAVEYARD, playerA, "Dragon Shadow"); // aura, enchant creature, +1/+0, fear
        //
        addCard(Zone.HAND, playerA, "Craw Wurm"); // {4}{G}{G}, 6/4
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Craw Wurm");
        setChoice(playerA, true); // return the aura

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAttachedTo(playerA, "Dragon Shadow", "Craw Wurm", true);
        assertPowerToughness(playerA, "Craw Wurm", 6 + 1, 4);
    }

    @Test
    public void test_PutOntoBattlefieldAttached_EaglesRescue() {
        // {2}{W/U}{W/U}: Return this card from your graveyard to the battlefield attached to target creature you
        // control with power 1 or less. Activate only as a sorcery.
        addCard(Zone.GRAVEYARD, playerA, "Eagle's Rescue"); // aura, enchant creature, +2/+2, flying
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Memnite"); // 1/1

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}{W/U}{W/U}: Return", "Memnite");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAttachedTo(playerA, "Eagle's Rescue", "Memnite", true);
        assertPowerToughness(playerA, "Memnite", 1 + 2, 1 + 2);
    }

    @Test
    public void test_PutOntoBattlefieldAttached_GryffsBoon() {
        // {3}{W}: Return Gryff's Boon from your graveyard to the battlefield attached to target creature.
        // Activate only as a sorcery.
        addCard(Zone.GRAVEYARD, playerA, "Gryff's Boon"); // aura, enchant creature, +1/+0, flying
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}{W}: Return", "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAttachedTo(playerA, "Gryff's Boon", "Grizzly Bears", true);
        assertPowerToughness(playerA, "Grizzly Bears", 2 + 1, 2);
    }

    @Test
    public void test_PutOntoBattlefieldAttached_ReinsOfTheVinesteed() {
        // When enchanted creature dies, you may return Reins of the Vinesteed from your graveyard to the battlefield
        // attached to a creature that shares a creature type with that creature.
        addCard(Zone.HAND, playerA, "Reins of the Vinesteed"); // {3}{G}, aura, enchant creature, +2/+2
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        //
        addCustomEffect_TargetDestroy(playerA);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Runeclaw Bear"); // bear 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Reins of the Vinesteed", "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "target destroy", "Grizzly Bears");
        setChoice(playerA, true); // return the aura
        setChoice(playerA, "Runeclaw Bear"); // creature to enchant

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAttachedTo(playerA, "Reins of the Vinesteed", "Runeclaw Bear", true);
        assertPowerToughness(playerA, "Runeclaw Bear", 2 + 2, 2 + 2);
    }

    @Test
    public void test_PutOntoBattlefieldAttached_TravelingPlague() {
        // When enchanted creature leaves the battlefield, that creature's controller returns Traveling Plague from
        // its owner's graveyard to the battlefield.
        addCard(Zone.HAND, playerA, "Traveling Plague"); // {3}{B}{B}, aura, enchant creature
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        //
        addCustomEffect_TargetDestroy(playerA);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // bear 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion"); // lion 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Traveling Plague", "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "target destroy", "Grizzly Bears");
        setChoice(playerB, "Silvercoat Lion"); // creature to enchant

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAttachedTo(playerB, "Traveling Plague", "Silvercoat Lion", true);
    }

    @Test
    public void test_PutOntoBattlefieldAttachedToEquipment_RohirrimChargers() {
        // You may exert Rohirrim Chargers as it attacks.
        // Whenever you exert a creature, reveal cards from the top of your library until you reveal an Equipment
        // card. Put that card onto the battlefield attached to that creature, then put the rest on the bottom of
        // your library in a random order.
        addCard(Zone.BATTLEFIELD, playerA, "Rohirrim Chargers"); // 4/4
        //
        addCard(Zone.LIBRARY, playerA, "Bonesplitter"); // equipment, +2/+0

        attack(1, playerA, "Rohirrim Chargers");
        setChoice(playerA, true); // exert

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAttachedTo(playerA, "Bonesplitter", "Rohirrim Chargers", true);
        assertPowerToughness(playerA, "Rohirrim Chargers", 4 + 2, 4);
        assertLife(playerB, 20 - 4 - 2);
    }

    @Test
    public void test_PutOntoBattlefieldAttachedToEquipment_RunedCrown() {
        // When Runed Crown enters, you may search your library, hand, and/or graveyard for a Rune card and put it
        // onto the battlefield attached to Runed Crown. If you search your library this way, shuffle it.
        addCard(Zone.HAND, playerA, "Runed Crown"); // {3}, equipment
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 3);
        //
        addCard(Zone.HAND, playerA, "Rune of Might"); // aura, enchant permanent, draw a card on enters

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Runed Crown");
        setChoice(playerA, false); // search graveyard
        setChoice(playerA, true); // search hand
        setChoice(playerA, "Rune of Might"); // rune to put

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAttachedTo(playerA, "Rune of Might", "Runed Crown", true);
        assertHandCount(playerA, 1);
    }

    @Test
    public void test_PutOntoBattlefieldAttachedToPlayer_BitterheartWitch() {
        // When Bitterheart Witch dies, you may search your library for a Curse card, put it onto the battlefield
        // attached to target player, then shuffle.
        addCard(Zone.BATTLEFIELD, playerA, "Bitterheart Witch"); // 1/2
        //
        addCustomEffect_TargetDestroy(playerA);
        addCard(Zone.LIBRARY, playerA, "Curse of Leeches"); // transforming double-faced curse, enchant player

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target destroy", "Bitterheart Witch");
        addTarget(playerA, playerB); // player for the curse
        setChoice(playerA, true); // search
        addTarget(playerA, "Curse of Leeches"); // curse to put

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        Assert.assertEquals("must be attached to the player", playerB.getId(), getPermanent("Curse of Leeches").getAttachedTo());
        Assert.assertTrue("player must be enchanted", playerB.getAttachments().contains(getPermanent("Curse of Leeches").getId()));
    }

    @Test
    public void test_PutOntoBattlefieldAttachedToPlayer_CurseOfMisfortunes() {
        // At the beginning of your upkeep, you may search your library for a Curse card that doesn't have the same
        // name as a Curse attached to enchanted player, put it onto the battlefield attached to that player, then shuffle.
        addCard(Zone.HAND, playerA, "Curse of Misfortunes"); // {4}{B}, curse, enchant player
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        //
        addCard(Zone.LIBRARY, playerA, "Curse of Leeches"); // transforming double-faced curse, enchant player

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of Misfortunes", playerB);

        setChoice(playerA, true); // search on upkeep of turn 3
        addTarget(playerA, "Curse of Leeches"); // curse to put

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        Assert.assertEquals("must be attached to the player", playerB.getId(), getPermanent("Curse of Leeches").getAttachedTo());
        Assert.assertTrue("player must be enchanted", playerB.getAttachments().contains(getPermanent("Curse of Leeches").getId()));
    }

    @Test
    public void test_PutOntoBattlefieldAttachedToPlayer_LyndeCheerfulTormentor() {
        // Whenever a Curse is put into your graveyard from the battlefield, return it to the battlefield attached to
        // you at the beginning of the next end step.
        addCard(Zone.BATTLEFIELD, playerA, "Lynde, Cheerful Tormentor");
        //
        addCustomEffect_TargetDestroy(playerA);
        addCard(Zone.HAND, playerA, "Curse of Leeches"); // {2}{B}, transforming double-faced curse, enchant player
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);

        setChoice(playerA, false); // nothing to attach to an opponent on upkeep of turn 1

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Curse of Leeches", playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target destroy", "Curse of Leeches");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        Assert.assertEquals("must be attached to the player", playerA.getId(), getPermanent("Curse of Leeches").getAttachedTo());
        Assert.assertTrue("player must be enchanted", playerA.getAttachments().contains(getPermanent("Curse of Leeches").getId()));
    }

    @Test
    public void test_CastFaceDown_GiftOfDoom() {
        // Morph—Sacrifice another creature.
        addCard(Zone.HAND, playerA, "Gift of Doom"); // {4}{B}, aura, enchant creature, face down cast for {3}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2

        // face down spell resolves as a 2/2 creature without subtypes, so it isn't an Aura
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Gift of Doom using Morph");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, EmptyNames.FACE_DOWN_CREATURE.getTestCommand(), 1);
        assertPowerToughness(playerA, EmptyNames.FACE_DOWN_CREATURE.getTestCommand(), 2, 2);
        assertAttachedTo(playerA, EmptyNames.FACE_DOWN_CREATURE.getTestCommand(), "Grizzly Bears", false);
    }

    @Test
    public void test_PutOntoBattlefieldTransformedAttachedToPlayer_AccursedWitch() {
        // When Accursed Witch dies, return it to the battlefield transformed under your control attached to target opponent.
        addCard(Zone.BATTLEFIELD, playerA, "Accursed Witch"); // 4/2, back face is curse Infectious Curse, enchant player
        //
        addCustomEffect_TargetDestroy(playerA);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target destroy", "Accursed Witch");
        addTarget(playerA, playerB); // opponent for the curse

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        Assert.assertEquals("must be attached to the player", playerB.getId(), getPermanent("Infectious Curse").getAttachedTo());
        Assert.assertTrue("player must be enchanted", playerB.getAttachments().contains(getPermanent("Infectious Curse").getId()));
    }

    @Test
    public void test_PutOntoBattlefieldTransformedAttachedToPlayer_RadiantGrace() {
        // When enchanted creature dies, return Radiant Grace to the battlefield transformed under your control
        // attached to target opponent.
        addCard(Zone.HAND, playerA, "Radiant Grace"); // {W}, aura, back face is curse Radiant Restraints, enchant player
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        //
        addCustomEffect_TargetDestroy(playerA);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion"); // lion 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Radiant Grace", "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "target destroy", "Grizzly Bears");
        addTarget(playerA, playerB); // opponent for the curse

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        Assert.assertEquals("must be attached to the player", playerB.getId(), getPermanent("Radiant Restraints").getAttachedTo());
        Assert.assertTrue("player must be enchanted", playerB.getAttachments().contains(getPermanent("Radiant Restraints").getId()));
    }

    @Test
    public void test_PutOntoBattlefieldTransformedAttachedToPlayerWithoutCreatures_RadiantGrace() {
        // When enchanted creature dies, return Radiant Grace to the battlefield transformed under your control
        // attached to target opponent.
        addCard(Zone.HAND, playerA, "Radiant Grace"); // {W}, aura, back face is curse Radiant Restraints, enchant player
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        //
        addCustomEffect_TargetDestroy(playerA);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Radiant Grace", "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "target destroy", "Grizzly Bears");
        addTarget(playerA, playerB); // opponent for the curse

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        Assert.assertEquals("must be attached to the player", playerB.getId(), getPermanent("Radiant Restraints").getAttachedTo());
        Assert.assertTrue("player must be enchanted", playerB.getAttachments().contains(getPermanent("Radiant Restraints").getId()));
    }

    @Test
    public void test_PutOntoBattlefieldTransformedAttached_VengefulStrangler() {
        // When Vengeful Strangler dies, return it to the battlefield transformed under your control attached to
        // target creature or planeswalker an opponent controls.
        addCard(Zone.BATTLEFIELD, playerA, "Vengeful Strangler"); // 2/1, back face is aura Strangling Grasp
        //
        addCustomEffect_TargetDestroy(playerA);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // bear 2/2

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target destroy", "Vengeful Strangler");
        addTarget(playerA, "Grizzly Bears"); // creature for the aura

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAttachedTo(playerB, "Strangling Grasp", "Grizzly Bears", true);
    }
}
