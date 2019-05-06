package org.mage.test.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.Effect;
import mage.cards.*;
import mage.target.Target;
import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author JayDi85 & ZeldaZach
 */
public class ExportJsonGameplayDataTest {

    private static final Logger logger = Logger.getLogger(ExportJsonGameplayDataTest.class);
    private static final boolean MTGJSON_WRITE_TO_FILES = false;

    @Test
    @Ignore
    /**
     * It's export code example for https://github.com/mtgjson/mtgjson4
     */
    public void exportTest() {
        List<Card> cards = new ArrayList<>();
        Collection<ExpansionSet> sets = Sets.getInstance().values();
        for (ExpansionSet set : sets) {
            for (ExpansionSet.SetCardInfo setInfo : set.getSetCardInfo()) {
                // catch cards creation errors and report (e.g. on wrong card code)
                try {
                    Card card = CardImpl.createCard(setInfo.getCardClass(), new CardSetInfo(setInfo.getName(), set.getCode(),
                            setInfo.getCardNumber(), setInfo.getRarity(), setInfo.getGraphicInfo()));
                    if (card != null) {
                        cards.add(card);
                    }
                } catch (Throwable e) {
                    logger.error("Can't create card " + setInfo.getName() + ": " + e.getMessage(), e);
                }
            }

            JsonObject res = new JsonObject();

            for (Card card : cards) {
                try {
                    JsonObject resCard = new JsonObject();
                    res.add(card.getName(), resCard);

                    JsonArray resAbilities = new JsonArray();
                    resCard.add("abilities", resAbilities);
                    for (Ability ability : card.getAbilities()) {
                        JsonObject resAbility = new JsonObject();
                        resAbilities.add(resAbility);

                        // basic
                        resAbility.addProperty("cost", ability.getManaCosts().getText());
                        resAbility.addProperty("name", ability.toString());
                        resAbility.addProperty("class", ability.getClass().getSimpleName());
                        //resAbility.addProperty("rule", ability.getRule());

                        // modes
                        JsonArray resModes = new JsonArray();
                        resAbility.add("modes", resModes);
                        for (Mode mode : ability.getModes().values()) {
                            JsonObject resMode = new JsonObject();
                            resModes.add(resMode);

                            // basic
                            //resMode.addProperty("name", mode.toString());

                            // effects
                            JsonArray resEffects = new JsonArray();
                            resMode.add("effects", resEffects);
                            for (Effect effect : mode.getEffects()) {
                                JsonObject resEffect = new JsonObject();
                                resEffects.add(resEffect);

                                resEffect.addProperty("class", effect.getClass().getSimpleName());
                                resEffect.addProperty("outcome", effect.getOutcome().toString());
                                resEffect.addProperty("text", effect.getText(mode));
                            }
                            if (resEffects.size() == 0) {
                                resMode.remove("effects");
                            }

                            // targets
                            JsonArray resTargets = new JsonArray();
                            resMode.add("targets", resTargets);
                            for (Target target : mode.getTargets()) {
                                JsonObject resTarget = new JsonObject();
                                resTargets.add(resTarget);

                                resTarget.addProperty("name", target.getTargetName());
                                resTarget.addProperty("class", target.getClass().getSimpleName());
                                resTarget.addProperty("min", target.getMinNumberOfTargets());
                                resTarget.addProperty("max", target.getMaxNumberOfTargets());
                            }
                            if (resTargets.size() == 0) {
                                resMode.remove("targets");
                            }

                            if (resMode.get("effects") == null && resMode.get("targets") == null) {
                                resModes.remove(resMode);
                            }
                        }
                        if (resModes.size() == 0) {
                            resAbility.remove("modes");
                        }
                    }
                } catch (Throwable e) {
                    logger.error("Inner error for " + card.getName() + ": " + e.getMessage(), e);
                    break;
                }
            }

            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            if (MTGJSON_WRITE_TO_FILES) {
                String filePath = System.getProperty("user.dir") + "/json/" + set.getCode() + ".json";
                File outputFile = new File(filePath);
                final boolean mkdirs = outputFile.getParentFile().mkdirs();
                try (Writer writer =
                             new BufferedWriter(
                                     new OutputStreamWriter(
                                             new FileOutputStream(outputFile, false), StandardCharsets.UTF_8
                                     )
                             )
                ) {
                    writer.write(gson.toJson(res));
                    System.out.println("Wrote " + set.getCode() + " to file");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                //System.out.println(gson.toJson(res));
            }
        }
    }
}