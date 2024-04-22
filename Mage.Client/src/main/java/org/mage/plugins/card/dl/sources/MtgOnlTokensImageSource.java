package org.mage.plugins.card.dl.sources;

import org.apache.log4j.Logger;
import org.mage.plugins.card.dl.DownloadServiceInfo;
import org.mage.plugins.card.images.CardDownloadData;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO: web site reworked and tokens doesn't work anymore,
 *  but it can be used to download a proxy tokens (tokens that was miss by wizards),
 *  see https://www.mtg.onl/mtg-missing-tokens/
 *
 * @author spjspj
 */
public enum MtgOnlTokensImageSource implements CardImageSource {

    instance;
    private static final Logger logger = Logger.getLogger(MtgOnlTokensImageSource.class);
    private static int maxTimes = 0;

    @Override
    public String getSourceName() {
        return "http://mtg.onl/token-list/tokens/";
    }

    @Override
    public float getAverageSize() {
        return 26.7f;
    }

    @Override
    public String getNextHttpImageUrl() {
        if (copyUrlToImage == null) {
            setupLinks();
        }

        for (String key : copyUrlToImageDone.keySet()) {
            if (copyUrlToImageDone.get(key) < maxTimes) {
                copyUrlToImageDone.put(key, maxTimes);
                return key;
            }
        }
        if (maxTimes < 2) {
            maxTimes++;
        }
        for (String key : copyUrlToImageDone.keySet()) {
            if (copyUrlToImageDone.get(key) < maxTimes) {
                copyUrlToImageDone.put(key, maxTimes);
                return key;
            }
        }
        return null;
    }

    @Override
    public String getFileForHttpImage(String httpImageUrl) {
        if (httpImageUrl != null) {
            return copyUrlToImage.get(httpImageUrl);
        }
        return null;
    }

    @Override
    public boolean prepareDownloadList(DownloadServiceInfo downloadServiceInfo, List<CardDownloadData> downloadList) {
        return true;
    }

    @Override
    public CardImageUrls generateCardUrl(CardDownloadData card) throws Exception {
        return null;
    }

    Map<String, String> copyUrlToImage = null;
    Map<String, String> copyImageToUrl = null;
    Map<String, Integer> copyUrlToImageDone = null;

    private void setupLinks() {
        if (copyUrlToImage != null) {
            return;
        }
        copyUrlToImage = new HashMap<>();
        copyImageToUrl = new HashMap<>();
        copyUrlToImageDone = new HashMap<>();
        copyUrlToImage.put("Angel_B_3_3.jpg", "ANGEL.B.ANGEL.CREATURE.3.3.full.jpg");
        copyUrlToImage.put("Angel_W_3_3.jpg", "ANGEL.W.ANGEL.CREATURE.3.3.full.jpg");
        copyUrlToImage.put("Angel_W_4_4.jpg", "ANGEL.W.ANGEL.CREATURE.4.4.full.jpg");
        copyUrlToImage.put("Ape_G_2_2.jpg", "APE.G.APE.CREATURE.2.2.full.jpg");
        copyUrlToImage.put("Ape_G_3_3.jpg", "APE.G.APE.CREATURE.3.3.full.jpg");
        copyUrlToImage.put("Ashaya_the_Awoken_World_G_4_4.jpg", "ASHAYATHEAWOKENWORLD.G.ELEMENTAL.CREATURE.4.4.full.jpg");
        copyUrlToImage.put("Assassin_B_1_1.jpg", "ASSASSIN.B.ASSASSIN.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Assembly-Worker_2_2.jpg", "ASSEMBLYWORKER..ASSEMBLYWORKER.ARTIFACTCREATURE.2.2.full.jpg");
        copyUrlToImage.put("Avatar_W_y_y.jpg", "AVATAR.W.AVATAR.CREATURE.S.S.full.jpg");
        copyUrlToImage.put("Bat_B_1_1.jpg", "BAT.B.BAT.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Bat_B_1_2.jpg", "BAT.B.BAT.CREATURE.1.2.full.jpg");
        copyUrlToImage.put("Bear_G_2_2.jpg", "BEAR.G.BEAR.CREATURE.2.2.full.jpg");
        copyUrlToImage.put("Bear_G_4_4.jpg", "BEAR.G.BEAR.CREATURE.4.4.full.jpg");
        copyUrlToImage.put("Beast_B_3_3.jpg", "BEAST.B.BEAST.CREATURE.3.3.full.jpg");
        copyUrlToImage.put("Beast_G_3_3.jpg", "BEAST.G.BEAST.CREATURE.3.3.full.jpg");
        copyUrlToImage.put("Beast_G_2_2.jpg", "BEAST.G.BEAST.CREATURE.2.2.full.jpg");
        copyUrlToImage.put("Beast_G_4_4.jpg", "BEAST.G.BEAST.CREATURE.4.4.full.jpg");
        copyUrlToImage.put("Beast_G_5_5.jpg", "BEAST.G.BEAST.CREATURE.5.5.full.jpg");
        copyUrlToImage.put("Beast_RGW_8_8.jpg", "BEAST.WRG.BEAST.CREATURE.8.8.full.jpg");
        copyUrlToImage.put("Bird_Soldier_W_1_1.jpg", "BIRDSOLDIER.W.BIRD.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Bird_U_1_1.jpg", "BIRD.U.BIRD.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Bird_U_2_2.jpg", "BIRD.U.BIRD.CREATURE.2.2.full.jpg");
        copyUrlToImage.put("Bird_U_2_2_Enchantment.jpg", "BIRD.U.BIRD.ENCHANTMENTCREATURE.2.2.full.jpg");
        copyUrlToImage.put("Bird_WU_1_1.jpg", "BIRD.WU.BIRD.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Bird_W_1_1.jpg", "BIRD.W.BIRD.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Bird_W_3_3.jpg", "BIRD.W.BIRD.CREATURE.3.3.full.jpg");
        copyUrlToImage.put("Bird_W_3_4.jpg", "BIRD.W.BIRD.CREATURE.3.4.full.jpg");
        copyUrlToImage.put("Boar_G_2_2.jpg", "BOAR.G.BOAR.CREATURE.2.2.full.jpg");
        copyUrlToImage.put("Boar_G_3_3.jpg", "BOAR.G.BOAR.CREATURE.3.3.full.jpg");
        copyUrlToImage.put("Butterfly_G_1_1.jpg", "BUTTERFLY.G.INSECT.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Camarid_U_1_1.jpg", "CAMARID.U.CAMARID.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Caribou_W_0_1.jpg", "CARIBOU.W.CARIBOU.CREATURE.0.1.full.jpg");
        copyUrlToImage.put("Carnivore_R_3_1.jpg", "CARNIVORE.R.BEAST.CREATURE.3.1.full.jpg");
        copyUrlToImage.put("Cat_B_2_1.jpg", "CAT.B.CAT.CREATURE.2.1.full.jpg");
        copyUrlToImage.put("Cat_G_1_1.jpg", "CAT.G.CAT.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Cat_R_1_1.jpg", "CAT.R.CAT.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Cat_Soldier_W_1_1.jpg", "CATSOLDIER.W.CATSOLDIER.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Cat_W_2_2.jpg", "CAT.W.CAT.CREATURE.2.2.full.jpg");
        copyUrlToImage.put("Cat_Warrior_G_2_2.jpg", "CATWARRIOR.G.CATWARRIOR.CREATURE.2.2.full.jpg");
        copyUrlToImage.put("Centaur_G_3_3.jpg", "CENTAUR.G.CENTAUR.CREATURE.3.3.full.jpg");
        copyUrlToImage.put("Centaur_G_3_3_Enchantment.jpg", "CENTAUR.G.CENTAUR.ENCHANTMENTCREATURE.3.3.full.jpg");
        copyUrlToImage.put("Centaur_G_3_3_protection.jpg", "CENTAUR.G.CENTAUR.CREATURE.3.3a.full.jpg");
        copyUrlToImage.put("Citizen_W_1_1.jpg", "CITIZEN.W.CITIZEN.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Cleric_WB_1_1.jpg", "CLERIC.WB.CLERIC.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Cleric_W_2_1.jpg", "CLERIC.W.CLERIC.CREATUREENCHANTMENT.2.1.full.jpg");
        copyUrlToImage.put("Cloud_Sprite_U_1_1.jpg", "CLOUDSPRITE.U.FAERIE.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Clue.jpg", "CLUE..CLUE.ARTIFACT.0.0.full.jpg");
        copyUrlToImage.put("Construct_1_1.jpg", "CONSTRUCT..CONSTRUCT.ARTIFACTCREATURE.1.1.full.jpg");
        copyUrlToImage.put("Construct_6_12.jpg", "CONSTRUCT..CONSTRUCT.CREATUREARTIFACT.6.12.full.jpg");
        copyUrlToImage.put("Demon_B_5_5.jpg", "DEMON.B.DEMON.CREATURE.5.5.full.jpg");
        copyUrlToImage.put("Demon_B_y_y.jpg", "DEMON.B.DEMON.CREATURE.S.S.full.jpg");
        copyUrlToImage.put("Devil_R_1_1.jpg", "DEVIL.R.DEVIL.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Djinn_5_5.jpg", "DJINN..DJINN.ARTIFACTCREATURE.5.5.full.jpg");
        copyUrlToImage.put("Djinn_Monk_U_2_2.jpg", "DJINNMONK.U.DJINNMONK.CREATURE.2.2.full.jpg");
        copyUrlToImage.put("Dragon_RG_1_1.jpg", "DRAGON.RG.DRAGON.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Dragon_R_2_2.jpg", "DRAGON.R.DRAGON.CREATURE.2.2.full.jpg");
        copyUrlToImage.put("Dragon_R_4_4.jpg", "DRAGON.R.DRAGON.CREATURE.4.4.full.jpg");
        copyUrlToImage.put("Dragon_R_5_5.jpg", "DRAGON.R.DRAGON.CREATURE.5.5.full.jpg");
        copyUrlToImage.put("Dragon_R_6_6.jpg", "DRAGON.R.DRAGON.CREATURE.6.6.full.jpg");
        copyUrlToImage.put("Dragon_Spirit_U_5_5.jpg", "DRAGONSPIRIT.U.DRAGONSPIRIT.CREATURE.5.5.full.jpg");
        copyUrlToImage.put("Drake_UG_2_2.jpg", "DRAKE.UG.DRAKE.CREATURE.2.2.full.jpg");
        copyUrlToImage.put("Drake_U_2_2.jpg", "DRAKE.U.DRAKE.CREATURE.2.2.full.jpg");
        copyUrlToImage.put("Eldrazi_10_10.jpg", "ELDRAZI..ELDRAZI.CREATURE.10.10.full.jpg");
        copyUrlToImage.put("Eldrazi_Scion_1_1.jpg", "ELDRAZISCION..ELDRAZISCION.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Eldrazi_Spawn_0_1.jpg", "ELDRAZISPAWN..ELDRAZISPAWN.CREATURE.0.1.full.jpg");
        copyUrlToImage.put("Elemental_BR_5_5.jpg", "ELEMENTAL.BR.ELEMENTAL.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Elemental_GW_y_y.jpg", "ELEMENTAL.WG.ELEMENTAL.CREATURE.S.S.full.jpg");
        copyUrlToImage.put("Elemental_G_2_2.jpg", "ELEMENTAL.G.ELEMENTAL.CREATURE.2.2.full.jpg");
        copyUrlToImage.put("Elemental_R_3_1.jpg", "ELEMENTAL.R.ELEMENTAL.CREATURE.3.1.full.jpg");
        copyUrlToImage.put("Elemental_G_4_4.jpg", "ELEMENTAL.G.ELEMENTAL.CREATURE.4.4.full.jpg");
        copyUrlToImage.put("Elemental_G_5_3.jpg", "ELEMENTAL.G.ELEMENTAL.CREATURE.5.3.full.jpg");
        copyUrlToImage.put("Elemental_G_7_7.jpg", "ELEMENTAL.G.ELEMENTAL.CREATURE.7.7.full.jpg");
        copyUrlToImage.put("Elemental_R_7_1.jpg", "ELEMENTAL.R.ELEMENTAL.CREATURE.7.1.full.jpg");
        copyUrlToImage.put("Elemental_Shaman_R_3_1.jpg", "ELEMENTALSHAMAN.R.ELEMENTALSHAMAN.CREATURE.3.1.full.jpg");
        copyUrlToImage.put("Elephant_G_3_3.jpg", "ELEPHANT.G.ELEPHANT.CREATURE.3.3.full.jpg");
        copyUrlToImage.put("Elf_Druid_G_1_1.jpg", "ELFDRUID.G.ELFDRUID.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Elf_G_1_1.jpg", "ELFWARRIOR.G.ELFWARRIOR.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Elf_Warrior_GW_1_1.jpg", "ELFWARRIOR.WG.ELFWARRIOR.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Elf_Warrior_G_1_1.jpg", "ELFWARRIOR.G.ELFWARRIOR.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Faerie_Rogue_B_1_1.jpg", "FAERIEROGUE.B.FAERIEROGUE.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Faerie_Rogue_UB_1_1.jpg", "FAERIEROGUE.UB.FAERIEROGUE.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Faerie_U_1_1.jpg", "FAERIE.U.FAERIE.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Festering_Goblin_B_1_1.jpg", "FESTERINGGOBLIN.B.ZOMBIEGOBLIN.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Fish_U_3_3.jpg", "FISH.U.FISH.CREATURE.3.3.full.jpg");
        copyUrlToImage.put("Frog_Lizard_G_3_3.jpg", "FROGLIZARD.G.FROGLIZARD.CREATURE.3.3.full.jpg");
        copyUrlToImage.put("Gargoyle_3_4.jpg", "GARGOYLE..GARGOYLE.CREATUREARTIFACT.3.4.full.jpg");
        copyUrlToImage.put("Germ_B_0_0.jpg", "GERM.B.GERM.CREATURE.0.0.full.jpg");
        copyUrlToImage.put("Giant_R_4_4.jpg", "GIANT.R.GIANT.CREATURE.4.4.full.jpg");
        copyUrlToImage.put("Giant_Warrior_RG_4_4.jpg", "GIANTWARRIOR.RG.GIANTWARRIOR.CREATURE.4.4.full.jpg");
        copyUrlToImage.put("Giant_Warrior_W_5_5.jpg", "GIANTWARRIOR.W.GIANTWARRIOR.CREATURE.5.5.full.jpg");
        copyUrlToImage.put("Gnome_1_1.jpg", "GNOME..GNOME.ARTIFACTCREATURE.1.1.full.jpg");
        copyUrlToImage.put("Goat_W_0_1.jpg", "GOAT.W.GOAT.CREATURE.0.1.full.jpg");
        copyUrlToImage.put("Goblin_R_1_1.jpg", "GOBLIN.R.GOBLIN.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Goblin_R_2_1.jpg", "GOBLIN.R.GOBLIN.CREATURE.2.1.full.jpg");
        copyUrlToImage.put("Goblin_Rogue_B_1_1.jpg", "GOBLINROGUE.B.GOBLINROGUE.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Goblin_Scout_R_1_1.jpg", "GOBLINSCOUT.R.GOBLINSCOUT.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Goblin_Soldier_RW_1_1.jpg", "GOBLINSOLDIER.WR.GOBLINSOLDIER.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Goblin_Warrior_RG_1_1.jpg", "GOBLINWARRIOR.RG.GOBLINWARRIOR.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Gold_.jpg", "GOLD...ARTIFACT.0.0.full.jpg");
        copyUrlToImage.put("Goldmeadow_Harrier_W_1_1.jpg", "GOLDMEADOWHARRIER.W.KITHKINSOLDIER.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Golem_3_3.jpg", "GOLEM..GOLEM.ARTIFACTCREATURE.3.3.full.jpg");
        copyUrlToImage.put("Golem_3_3_Enchantment.jpg", "GOLEM..GOLEM.ENCHANTMENTARTIFACTCREATURE.3.3.full.jpg");
        copyUrlToImage.put("Golem_9_9.jpg", "GOLEM..GOLEM.ARTIFACTCREATURE.9.9.full.jpg");
        copyUrlToImage.put("Graveborn_BR_3_1.jpg", "GRAVEBORN.BR.GRAVEBORN.CREATURE.3.1.full.jpg");
        copyUrlToImage.put("Griffin_W_2_2.jpg", "GRIFFIN.W.GRIFFIN.CREATURE.2.2.full.jpg");
        copyUrlToImage.put("Harpy_B_1_1.jpg", "HARPY.B.HARPY.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Hellion_R_4_4.jpg", "HELLION.R.HELLION.CREATURE.4.4.full.jpg");
        copyUrlToImage.put("Hippo_G_1_1.jpg", "HIPPO.G.HIPPO.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Homunculus_U_0_1.jpg", "HOMUNCULUS.U.HOMUNCULUS.CREATUREARTIFACT.0.1.full.jpg");
        copyUrlToImage.put("Homunculus_U_2_2.jpg", "HOMUNCULUS.U.HOMUNCULUS.CREATURE.2.2.full.jpg");
        copyUrlToImage.put("Hornet_1_1.jpg", "HORNET..INSECT.ARTIFACTCREATURE.1.1.full.jpg");
        copyUrlToImage.put("Horror_B_4_4.jpg", "HORROR.B.HORROR.CREATURE.4.4.full.jpg");
        copyUrlToImage.put("Horror_B_X_X.jpg", "HORROR.B.HORROR.CREATURE.X.X.full.jpg");
        copyUrlToImage.put("Horror_UB_1_1.jpg", "HORROR.UB.HORROR.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Horror_X_X.jpg", "HORROR..HORROR.ARTIFACTCREATURE.X.X.full.jpg");
        copyUrlToImage.put("Hound_G_1_1.jpg", "HOUND.G.HOUND.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Human_Cleric_WB_1_1.jpg", "HUMANCLERIC.WB.HUMANCLERIC.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Human_R_1_1.jpg", "HUMAN.R.HUMAN.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Human_Soldier_W_1_1.jpg", "HUMANSOLDIER.W.HUMANSOLDIER.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Human_W_1_1.jpg", "HUMAN.W.HUMAN.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Hydra_G_X_X.jpg", "HYDRA.G.HYDRA.CREATURE.X.X.full.jpg");
        copyUrlToImage.put("Illusion_U_1_1.jpg", "ILLUSION.U.ILLUSION.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Illusion_U_2_2.jpg", "ILLUSION.U.ILLUSION.CREATURE.2.2.full.jpg");
        copyUrlToImage.put("Insect_G_1_1.jpg", "INSECT.G.INSECT.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Insect_G_6_1.jpg", "INSECT.G.INSECT.CREATURE.6.1.full.jpg");
        copyUrlToImage.put("Kaldra_4_4.jpg", "KALDRA..AVATAR.CREATURE.4.4.full.jpg");
        copyUrlToImage.put("Kavu_B_3_3.jpg", "KAVU.B.KAVU.CREATURE.3.3.full.jpg");
        copyUrlToImage.put("Kelp_U_0_1.jpg", "KELP.U.PLANTWALL.CREATURE.0.1.full.jpg");
        copyUrlToImage.put("Kithkin_Soldier_W_1_1.jpg", "KITHKINSOLDIER.W.KITHKINSOLDIER.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Knight_Ally_W_2_2.jpg", "KNIGHTALLY.W.KNIGHTALLY.CREATURE.2.2.full.jpg");
        copyUrlToImage.put("Knight_B_2_2.jpg", "KNIGHT.B.KNIGHT.CREATURE.2.2.full.jpg");
        copyUrlToImage.put("Knight_W_1_1.jpg", "KNIGHT.W.KNIGHT.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Knight_W_2_2.jpg", "KNIGHT.W.KNIGHT.CREATURE.2.2.full.jpg");
        copyUrlToImage.put("Kobolds_of_Kher_Keep_R_0_1.jpg", "KOBOLDSOFKHERKEEP.R.KOBOLD.CREATURE.0.1.full.jpg");
        copyUrlToImage.put("Kor_Ally_W_1_1.jpg", "KORALLY.W.KORALLY.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Kor_Soldier_W_1_1.jpg", "KORSOLDIER.W.KORSOLDIER.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Kraken_U_9_9.jpg", "KRAKEN.U.KRAKEN.CREATURE.9.9.full.jpg");
        copyUrlToImage.put("Landmine_.jpg", "LANDMINE...ARTIFACT.0.0.full.jpg");
        copyUrlToImage.put("Lightning_Ranger_R_5_1.jpg", "LIGHTNINGRAGER.R.ELEMENTAL.CREATURE.5.1.full.jpg");
        copyUrlToImage.put("Lizard_G_2_2.jpg", "LIZARD.G.LIZARD.CREATURE.2.2.full.jpg");
        copyUrlToImage.put("Llanowar_Elves_G_1_1.jpg", "LLANOWARELVES.G.ELFDRUID.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Manifest_2_2.jpg", "MANIFEST.....full.jpg");
        copyUrlToImage.put("Marit_Lage_B_20_20.jpg", "MARITLAGE.B.AVATAR.CREATURE.20.20.full.jpg");
        copyUrlToImage.put("Merfolk_U_1_1.jpg", "MERFOLK.U.MERFOLK.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Merfolk_Wizard_U_1_1.jpg", "MERFOLKWIZARD.U.MERFOLKWIZARD.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Metallic_Sliver_1_1.jpg", "METALLICSLIVER..SLIVER.CREATUREARTIFACT.1.1.full.jpg");
        copyUrlToImage.put("Minion_B_1_1.jpg", "MINION.B.MINION.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Minion_B_X_X.jpg", "MINION.B.MINION.CREATURE.X.X.full.jpg");
        copyUrlToImage.put("Minor_Demon_BR_1_1.jpg", "MINORDEMON.BR.DEMON.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Minotaur_R_2_3.jpg", "MINOTAUR.R.MINOTAUR.CREATURE.2.3.full.jpg");
        copyUrlToImage.put("Monk_W_1_1.jpg", "MONK.W.MONK.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Morph_2_2.jpg", "MORPH.....full.jpg");
        copyUrlToImage.put("Myr_1_1.jpg", "MYR..MYR.CREATUREARTIFACT.1.1.full.jpg");
        copyUrlToImage.put("Octopus_U_8_8.jpg", "OCTOPUS.U.OCTOPUS.CREATURE.8.8.full.jpg");
        copyUrlToImage.put("Ogre_R_3_3.jpg", "OGRE.R.OGRE.CREATURE.3.3.full.jpg");
        copyUrlToImage.put("Ogre_R_4_4.jpg", "OGRE.R.OGRE.CREATURE.4.4.full.jpg");
        copyUrlToImage.put("Ooze_G_1_1.jpg", "OOZE.G.OOZE.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Ooze_G_2_2.jpg", "OOZE.G.OOZE.CREATURE.2.2.full.jpg");
        copyUrlToImage.put("Ooze_G_3_3.jpg", "OOZE.G.OOZE.CREATURE.3.3.full.jpg");
        copyUrlToImage.put("Orb_U_X_X.jpg", "ORB.U.ORB.CREATURE.X.X.jpg.full.jpg");
        copyUrlToImage.put("Pegasus_W_1_1.jpg", "PEGASUS.W.PEGASUS.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Pentavite_1_1.jpg", "PENTAVITE..PENTAVITE.ARTIFACTCREATURE.1.1.full.jpg");
        copyUrlToImage.put("Pest_0_1.jpg", "PEST..PEST.ARTIFACTCREATURE.0.1.full.jpg");
        copyUrlToImage.put("Pincher_2_2.jpg", "PINCHER..PINCHER.CREATURE.2.2.full.jpg");
        copyUrlToImage.put("Plant_G_0_1.jpg", "PLANT.G.PLANT.CREATURE.0.1.full.jpg");
        copyUrlToImage.put("Plant_G_1_1.jpg", "PLANT.G.PLANT.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Rat_B_1_1.jpg", "RAT.B.RAT.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Reflection_W_2_2.jpg", "REFLECTION.W.REFLECTION.CREATURE.2.2.full.jpg");
        copyUrlToImage.put("Rhino_G_4_4.jpg", "RHINO.G.RHINO.CREATURE.4.4.full.jpg");
        copyUrlToImage.put("Rukh_R_4_4.jpg", "BIRD.R.BIRD.CREATURE.4.4.full.jpg");
        copyUrlToImage.put("Sand_1_1.jpg", "SAND..SAND.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Sand_Warrior_RGW_1_1.jpg", "SANDWARRIOR.WRG.SANDWARRIOR.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Saporling_G_1_1.jpg", "SAPROLING.G.SAPROLING.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Satyr_RG_2_2.jpg", "SATYR.RG.SATYR.CREATURE.2.2.full.jpg");
        copyUrlToImage.put("Serf_B_0_1.jpg", "SERF.B.SERF.CREATURE.0.1.full.jpg");
        copyUrlToImage.put("Shapeshifter_1_1.jpg", "SHAPESHIFTER..SHAPESHIFTER.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Skeleton_B_1_1.jpg", "SKELETON.B.SKELETON.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Sliver_1_1.jpg", "SLIVER..SLIVER.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Snake_1_1.jpg", "SNAKE..SNAKE.ARTIFACTCREATURE.1.1.full.jpg");
        copyUrlToImage.put("Snake_B_1_1.jpg", "SNAKE.B.SNAKE.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Snake_GB_1_1.jpg", "SNAKE.BG.SNAKE.ENCHANTMENTCREATURE.1.1.full.jpg");
        copyUrlToImage.put("Snake_GU_1_1.jpg", "SNAKE.UG.SNAKE.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Snake_G_1_1.jpg", "SNAKE.G.SNAKE.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Soldier_Ally_W_1_1.jpg", "SOLDIERALLY.W.SOLDIERALLY.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Soldier_RW_1_1.jpg", "SOLDIER.WR.SOLDIER.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Soldier_R_1_1.jpg", "SOLDIER.R.SOLDIER.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Soldier_W_1_1.jpg", "SOLDIER.W.SOLDIER.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Soldier_W_1_1_Enchantment.jpg", "SOLDIER.W.SOLDIER.ENCHANTMENTCREATURE.1.1.full.jpg");
        copyUrlToImage.put("Spark_Elemental_R_3_1.jpg", "SPARKELEMENTAL.R.ELEMENTAL.CREATURE.3.1.full.jpg");
        copyUrlToImage.put("Spawn_2_2.jpg", "SPAWN..SPAWN.ARTIFACTCREATURE.2.2.full.jpg");
        copyUrlToImage.put("Sphinx_U_4_4.jpg", "SPHINX.U.SPHINX.CREATURE.4.4.full.jpg");
        copyUrlToImage.put("Spider_B_2_4.jpg", "SPIDER.B.SPIDER.CREATURE.2.4.full.jpg");
        copyUrlToImage.put("Spider_G_1_2.jpg", "SPIDER.G.SPIDER.CREATURE.1.2.full.jpg");
        copyUrlToImage.put("Spider_G_1_3.jpg", "SPIDER.G.SPIDER.ENCHANTMENTCREATURE.1.3.full.jpg");
        copyUrlToImage.put("Spike_G_1_1.jpg", "SPIKE.G.SPIKE.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Spirit_1_1.jpg", "SPIRIT..SPIRIT.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Spirit_U_1_1.jpg", "SPIRIT.U.SPIRIT.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Spirit_WB_1_1.jpg", "SPIRIT.WB.SPIRIT.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Spirit_WB_y_y.jpg", "SPIRIT.WB.SPIRIT.ENCHANTMENTCREATURE.S.S.full.jpg");
        copyUrlToImage.put("Spirit_W_1_1.jpg", "SPIRIT.W.SPIRIT.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Spirit_W_3_3.jpg", "SPIRIT.W.SPIRIT.CREATURE.3.3.full.jpg");
        copyUrlToImage.put("Spirit_Warror_BG_y_y.jpg", "SPIRITWARRIOR.BG.SPIRITWARRIOR.CREATURE.S.S.full.jpg");
        copyUrlToImage.put("Squid_U_1_1.jpg", "SQUID.U.SQUID.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Squirrel_G_1_1.jpg", "SQUIRREL.G.SQUIRREL.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Stoneforged_Blade_.jpg", "STONEFORGEDBLADE..EQUIPMENT.ARTIFACT.0.0.full.jpg");
        copyUrlToImage.put("Tetravite_1_1.jpg", "TETRAVITE..TETRAVITE.CREATUREARTIFACT.1.1.full.jpg");
        copyUrlToImage.put("Thopter_1_1.jpg", "THOPTER..THOPTER.ARTIFACTCREATURE.1.1.full.jpg");
        copyUrlToImage.put("Thopter_U_1_1.jpg", "THOPTER.U.THOPTER.CREATUREARTIFACT.1.1.full.jpg");
        copyUrlToImage.put("Thrull_B_0_1.jpg", "THRULL.B.THRULL.CREATURE.0.1.full.jpg");
        copyUrlToImage.put("Thrull_B_1_1.jpg", "THRULL.B.THRULL.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Treefolk_G_X_X.jpg", "TREEFOLK.G.TREEFOLK.CREATURE.X.X.full.jpg");
        copyUrlToImage.put("Treefolk_Shaman_G_2_5.jpg", "TREEFOLKSHAMAN.G.TREEFOLKSHAMAN.CREATURE.2.5.full.jpg");
        copyUrlToImage.put("Treefolk_Warrior_G_y_y.jpg", "TREEFOLKWARRIOR.G.TREEFOLKWARRIOR.CREATURE.S.S.full.jpg");
        copyUrlToImage.put("Triskelavite_1_1.jpg", "TRISKELAVITE..TRISKELAVITE.ARTIFACTCREATURE.1.1.full.jpg");
        copyUrlToImage.put("Tuktuk_the_Returned_5_5.jpg", "TUKTUKTHERETURNED..GOBLIN.ARTIFACTCREATURE.5.5.full.jpg");
        copyUrlToImage.put("Urami_B_5_5.jpg", "URAMI.B.DEMONSPIRIT.CREATURE.5.5.full.jpg");
        copyUrlToImage.put("Vampire_B_1_1.jpg", "VAMPIRE.B.VAMPIRE.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Vampire_B_2_2.jpg", "VAMPIRE.B.VAMPIRE.CREATURE.2.2.full.jpg");
        copyUrlToImage.put("Vampire_B_X_X.jpg", "VAMPIRE.B.VAMPIRE.CREATURE.X.X.full.jpg");
        copyUrlToImage.put("Vampire_Knight_B_1_1.jpg", "VAMPIREKNIGHT.B.VAMPIREKNIGHT.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Voja_GW_2_2.jpg", "VOJA.WG.WOLF.CREATURE.2.2.full.jpg");
        copyUrlToImage.put("Wall_U_5_5.jpg", "WALL.U.WALL.CREATURE.5.5.full.jpg");
        copyUrlToImage.put("Warrior_B_2_1.jpg", "WARRIOR.B.WARRIOR.CREATURE.2.1.full.jpg");
        copyUrlToImage.put("Warrior_R_1_1.jpg", "WARRIOR.R.WARRIOR.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Warrior_W_1_1.jpg", "WARRIOR.W.WARRIOR.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Wasp_1_1.jpg", "WASP..INSECT.ARTIFACTCREATURE.1.1.full.jpg");
        copyUrlToImage.put("Weird_U_3_3.jpg", "WEIRD.U.WEIRD.CREATURE.3.3.full.jpg");
        copyUrlToImage.put("Whale_U_6_6.jpg", "WHALE.U.WHALE.CREATURE.6.6.full.jpg");
        copyUrlToImage.put("Wirefly_2_2.jpg", "WIREFLY..INSECT.ARTIFACTCREATURE.2.2.full.jpg");
        copyUrlToImage.put("Wolf_G_2_2.jpg", "WOLF.G.WOLF.CREATURE.2.2.full.jpg");
        copyUrlToImage.put("Wood_G_0_1.jpg", "WOOD.G.WALL.CREATURE.0.1.full.jpg");
        copyUrlToImage.put("Worm_BG_1_1.jpg", "WORM.BG.WORM.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("Wurm_3_3.jpg", "WURM..WURM.ARTIFACTCREATURE.3.3.full.jpg");
        copyUrlToImage.put("Wurm_B_6_6.jpg", "WURM.B.WURM.CREATURE.6.6.full.jpg");
        copyUrlToImage.put("Wurm_G_6_6.jpg", "WURM.G.WURM.CREATURE.6.6.full.jpg");
        copyUrlToImage.put("Zombie_B_2_2.jpg", "ZOMBIE.B.ZOMBIE.CREATURE.2.2.full.jpg");
        copyUrlToImage.put("Zombie_B_2_2_Enchantment.jpg", "ZOMBIE.B.ZOMBIE.ENCHANTMENTCREATURE.2.2.full.jpg");
        copyUrlToImage.put("Zombie_B_5_5.jpg", "ZOMBIEGIANT.B.ZOMBIEGIANT.CREATURE.5.5.full.jpg");
        copyUrlToImage.put("Zombie_B_X_X.jpg", "ZOMBIE.B.ZOMBIE.CREATURE.X.X.full.jpg");
        copyUrlToImage.put("Zombie_Horror_B_X_X.jpg", "ZOMBIEHORROR.B.ZOMBIEHORROR.CREATURE.X.X.full.jpg");
        copyUrlToImage.put("Zombie_U_X_X.jpg", "ZOMBIE.U.ZOMBIE.CREATURE.X.X.full.jpg");
        copyUrlToImage.put("Zombie_Wizard_UB_1_1.jpg", "ZOMBIEWIZARD.UB.ZOMBIEWIZARD.CREATURE.1.1.full.jpg");

        for (String key : copyUrlToImage.keySet()) {
            copyUrlToImageDone.put(key, maxTimes);
            copyImageToUrl.put(copyUrlToImage.get(key), key);
        }
    }

    @Override
    public CardImageUrls generateTokenUrl(CardDownloadData card) throws IOException {
        if (copyUrlToImage == null) {
            setupLinks();
        }
        return null;
    }

    @Override
    public int getTotalImages() {
        if (copyUrlToImage == null) {
            setupLinks();
        }
        if (copyUrlToImage != null) {
            return copyImageToUrl.size();
        }
        return -1;
    }

    @Override
    public boolean isTokenSource() {
        return true;
    }

    @Override
    public boolean isCardSource() {
        return false;
    }

    @Override
    public void doPause(String httpImageUrl) {
    }

    @Override
    public boolean isCardImageProvided(String setCode, String cardName) {
        return false;
    }

    @Override
    public boolean isTokenImageProvided(String setCode, String cardName, Integer tokenNumber) {
        return true;
    }
}
