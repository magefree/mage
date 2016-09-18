/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */
package org.mage.plugins.card.dl.sources;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import org.mage.plugins.card.images.CardDownloadData;
import org.mage.plugins.card.images.DownloadPictures;

/**
 *
 * @author spjspj
 */
public class GrabbagImageSource implements CardImageSource {

    private static final Logger logger = Logger.getLogger(GrabbagImageSource.class);
    private static CardImageSource instance = new GrabbagImageSource();
    private static int maxTimes = 0;

    public static CardImageSource getInstance() {
        if (instance == null) {
            instance = new GrabbagImageSource();
        }
        return instance;
    }

    @Override
    public String getSourceName() {
        return "http://magiccards.info/scans/en/" ;
    }

    @Override
    public Float getAverageSize() {
        return 26.7f;
    }

    @Override
    public String getNextHttpImageUrl() {
        if (copyUrlToImage == null) {
            setupLinks();
        }
        try { 
            Thread.sleep(2000);
        } catch (InterruptedException ex) {            
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
    public String generateURL(CardDownloadData card) throws Exception {
        if (copyUrlToImage == null) {
            setupLinks();
        }
        return null;
    }

    HashMap<String, String> copyUrlToImage = null;
    HashMap<String, String> copyImageToUrl = null;
    HashMap<String, Integer> copyUrlToImageDone = null;

    private void setupLinks() {
        if (copyUrlToImage != null) {
            return;
        }
        copyUrlToImage = new HashMap<String, String>();
        copyImageToUrl = new HashMap<String, String>();
        copyUrlToImageDone = new HashMap<String, Integer>();

        //http://anonymouse.org/cgi-bin/anon-www.cgi/http://magiccards.info/scans/en/arena/42.jpg
        copyUrlToImage.put("apac/1.jpg", "APAC.zip/APAC/Forest 3.full.jpg");
        copyUrlToImage.put("apac/10.jpg",  "APAC.zip/APAC/Swamp.5.full.jpg");
        copyUrlToImage.put("apac/11.jpg", "APAC.zip/APAC/Forest 2.full.jpg");
        copyUrlToImage.put("apac/12.jpg",  "APAC.zip/APAC/Island.7.full.jpg");
        copyUrlToImage.put("apac/13.jpg",  "APAC.zip/APAC/Mountain.8.full.jpg");
        copyUrlToImage.put("apac/14.jpg",  "APAC.zip/APAC/Plains.4.full.jpg");
        copyUrlToImage.put("apac/15.jpg", "APAC.zip/APAC/Swamp 2.full.jpg");
        copyUrlToImage.put("apac/15.jpg", "APAC.zip/APAC/Swamp.15.full.jpg");
        copyUrlToImage.put("apac/2.jpg", "APAC.zip/APAC/Island 2.full.jpg");
        copyUrlToImage.put("apac/2.jpg", "APAC.zip/APAC/Island.2.full.jpg");
        copyUrlToImage.put("apac/3.jpg",  "APAC.zip/APAC/Mountain.3.full.jpg");
        copyUrlToImage.put("apac/4.jpg", "APAC.zip/APAC/Plains 2.full.jpg");
        copyUrlToImage.put("apac/4.jpg", "APAC.zip/APAC/Plains.9.full.jpg");
        copyUrlToImage.put("apac/5.jpg",  "APAC.zip/APAC/Swamp.10.full.jpg");
        copyUrlToImage.put("apac/6.jpg", "APAC.zip/APAC/Forest 1.full.jpg");
        copyUrlToImage.put("apac/7.jpg",  "APAC.zip/APAC/Island.12.full.jpg");
        copyUrlToImage.put("apac/8.jpg",  "APAC.zip/APAC/Mountain.13.full.jpg");
        copyUrlToImage.put("apac/9.jpg",  "APAC.zip/APAC/Plains.14.full.jpg");
        copyUrlToImage.put("arena/1.jpg", "ARENA.zip/ARENA/Plains 8.full.jpg");
        copyUrlToImage.put("arena/1.jpg", "ARENA.zip/ARENA/Plains.1.full.jpg");
        copyUrlToImage.put("arena/10.jpg", "ARENA.zip/ARENA/Swamp 8.full.jpg");
        copyUrlToImage.put("arena/10.jpg", "ARENA.zip/ARENA/Swamp.3.full.jpg");
        copyUrlToImage.put("arena/11.jpg", "ARENA.zip/ARENA/Mountain 8.full.jpg");
        copyUrlToImage.put("arena/11.jpg", "ARENA.zip/ARENA/Mountain.4.full.jpg");
        copyUrlToImage.put("arena/12.jpg", "ARENA.zip/ARENA/Forest 9.full.jpg");
        copyUrlToImage.put("arena/13.jpg", "ARENA.zip/ARENA/Pouncing Jaguar.full.jpg");
        copyUrlToImage.put("arena/14.jpg", "ARENA.zip/ARENA/Skittering Skirge.full.jpg");
        copyUrlToImage.put("arena/15.jpg", "ARENA.zip/ARENA/Rewind.full.jpg");
        copyUrlToImage.put("arena/16.jpg", "ARENA.zip/ARENA/Karn, Silver Golem.full.jpg");
        copyUrlToImage.put("arena/17.jpg", "ARENA.zip/ARENA/Duress.full.jpg");
        copyUrlToImage.put("arena/18.jpg", "ARENA.zip/ARENA/Uktabi Orangutan.full.jpg");
        copyUrlToImage.put("arena/19.jpg", "ARENA.zip/ARENA/Chill.full.jpg");
        copyUrlToImage.put("arena/2.jpg", "ARENA.zip/ARENA/Island 9.full.jpg");
        copyUrlToImage.put("arena/2.jpg", "ARENA.zip/ARENA/Island.39.full.jpg");
        copyUrlToImage.put("arena/20.jpg", "ARENA.zip/ARENA/Pillage.full.jpg");
        copyUrlToImage.put("arena/21.jpg", "ARENA.zip/ARENA/Enlightened Tutor.full.jpg");
        copyUrlToImage.put("arena/22.jpg", "ARENA.zip/ARENA/Stupor.full.jpg");
        copyUrlToImage.put("arena/23.jpg",  "ARENA.zip/ARENA/Plains.75.full.jpg");
        copyUrlToImage.put("arena/24.jpg",  "ARENA.zip/ARENA/Island.9.full.jpg");
        copyUrlToImage.put("arena/25.jpg",  "ARENA.zip/ARENA/Swamp.77.full.jpg");
        copyUrlToImage.put("arena/26.jpg",  "ARENA.zip/ARENA/Mountain.70.full.jpg");
        copyUrlToImage.put("arena/27.jpg", "ARENA.zip/ARENA/Forest 8.full.jpg");
        copyUrlToImage.put("arena/28.jpg", "ARENA.zip/ARENA/Creeping Mold.full.jpg");
        copyUrlToImage.put("arena/29.jpg", "ARENA.zip/ARENA/Dismiss.full.jpg");
        copyUrlToImage.put("arena/3.jpg",  "ARENA.zip/ARENA/Swamp.69.full.jpg");
        copyUrlToImage.put("arena/30.jpg", "ARENA.zip/ARENA/Fling.full.jpg");
        copyUrlToImage.put("arena/31.jpg", "ARENA.zip/ARENA/Empyrial Armor.full.jpg");
        copyUrlToImage.put("arena/32.jpg",  "ARENA.zip/ARENA/Plains.67.full.jpg");
        copyUrlToImage.put("arena/33.jpg",  "ARENA.zip/ARENA/Island.76.full.jpg");
        copyUrlToImage.put("arena/34.jpg",  "ARENA.zip/ARENA/Swamp.55.full.jpg");
        copyUrlToImage.put("arena/35.jpg", "ARENA.zip/ARENA/Mountain 6.full.jpg");
        copyUrlToImage.put("arena/35.jpg", "ARENA.zip/ARENA/Mountain.78.full.jpg");
        copyUrlToImage.put("arena/36.jpg",  "ARENA.zip/ARENA/Forest.71.full.jpg");
        copyUrlToImage.put("arena/37.jpg", "ARENA.zip/ARENA/Diabolic Edict.full.jpg");
        copyUrlToImage.put("arena/38.jpg", "ARENA.zip/ARENA/Gaea's Blessing.full.jpg");
        copyUrlToImage.put("arena/39.jpg",  "ARENA.zip/ARENA/Island.68.full.jpg");
        copyUrlToImage.put("arena/4.jpg", "ARENA.zip/ARENA/Mountain 5.full.jpg");
        copyUrlToImage.put("arena/40.jpg", "ARENA.zip/ARENA/Forest 6.full.jpg");
        copyUrlToImage.put("arena/40.jpg", "ARENA.zip/ARENA/Forest.5.full.jpg");
        copyUrlToImage.put("arena/41.jpg", "ARENA.zip/ARENA/Man-o'-War.full.jpg");
        copyUrlToImage.put("arena/42.jpg", "ARENA.zip/ARENA/Arc Lightning.full.jpg");
        copyUrlToImage.put("arena/43.jpg", "ARENA.zip/ARENA/Dauthi Slayer.full.jpg");
        copyUrlToImage.put("arena/44.jpg", "ARENA.zip/ARENA/Mana Leak.full.jpg");
        copyUrlToImage.put("arena/45.jpg",  "ARENA.zip/ARENA/Plains.53.full.jpg");
        copyUrlToImage.put("arena/46.jpg",  "ARENA.zip/ARENA/Island.54.full.jpg");
        copyUrlToImage.put("arena/47.jpg",  "ARENA.zip/ARENA/Swamp.47.full.jpg");
        copyUrlToImage.put("arena/48.jpg",  "ARENA.zip/ARENA/Mountain.48.full.jpg");
        copyUrlToImage.put("arena/49.jpg",  "ARENA.zip/ARENA/Forest.57.full.jpg");
        copyUrlToImage.put("arena/5.jpg",  "ARENA.zip/ARENA/Forest.40.full.jpg");
        copyUrlToImage.put("arena/50.jpg", "ARENA.zip/ARENA/Skirk Marauder.full.jpg");
        copyUrlToImage.put("arena/51.jpg", "ARENA.zip/ARENA/Elvish Aberration.full.jpg");
        copyUrlToImage.put("arena/52.jpg", "ARENA.zip/ARENA/Bonesplitter.full.jpg");
        copyUrlToImage.put("arena/53.jpg",  "ARENA.zip/ARENA/Plains.45.full.jpg");
        copyUrlToImage.put("arena/54.jpg",  "ARENA.zip/ARENA/Island.46.full.jpg");
        copyUrlToImage.put("arena/55.jpg",  "ARENA.zip/ARENA/Swamp.34.full.jpg");
        copyUrlToImage.put("arena/56.jpg",  "ARENA.zip/ARENA/Mountain.35.full.jpg");
        copyUrlToImage.put("arena/57.jpg",  "ARENA.zip/ARENA/Forest.36.full.jpg");
        copyUrlToImage.put("arena/58.jpg", "ARENA.zip/ARENA/Darksteel Ingot.full.jpg");
        copyUrlToImage.put("arena/59.jpg", "ARENA.zip/ARENA/Serum Visions.full.jpg");
        copyUrlToImage.put("arena/6.jpg", "ARENA.zip/ARENA/Disenchant.full.jpg");
        copyUrlToImage.put("arena/60.jpg", "ARENA.zip/ARENA/Glacial Ray.full.jpg");
        copyUrlToImage.put("arena/61.jpg", "ARENA.zip/ARENA/Circle of Protection: Art.full.jpg");
        copyUrlToImage.put("arena/62.jpg", "ARENA.zip/ARENA/Mise.full.jpg");
        copyUrlToImage.put("arena/63.jpg", "ARENA.zip/ARENA/Booster Tutor.full.jpg");
        copyUrlToImage.put("arena/64.jpg", "ARENA.zip/ARENA/Goblin Mime.full.jpg");
        copyUrlToImage.put("arena/65.jpg", "ARENA.zip/ARENA/Granny's Payback.full.jpg");
        copyUrlToImage.put("arena/66.jpg", "ARENA.zip/ARENA/Ashnod's Coupon.full.jpg");
        copyUrlToImage.put("arena/67.jpg",  "ARENA.zip/ARENA/Plains.32.full.jpg");
        copyUrlToImage.put("arena/68.jpg",  "ARENA.zip/ARENA/Island.33.full.jpg");
        copyUrlToImage.put("arena/69.jpg",  "ARENA.zip/ARENA/Swamp.25.full.jpg");
        copyUrlToImage.put("arena/7.jpg", "ARENA.zip/ARENA/Fireball.full.jpg");
        copyUrlToImage.put("arena/70.jpg",  "ARENA.zip/ARENA/Mountain.26.full.jpg");
        copyUrlToImage.put("arena/71.jpg",  "ARENA.zip/ARENA/Forest.27.full.jpg");
        copyUrlToImage.put("arena/72.jpg", "ARENA.zip/ARENA/Genju of the Spires.full.jpg");
        copyUrlToImage.put("arena/73.jpg", "ARENA.zip/ARENA/Okina Nightwatch.full.jpg");
        copyUrlToImage.put("arena/74.jpg", "ARENA.zip/ARENA/Skyknight Legionnaire.full.jpg");
        copyUrlToImage.put("arena/75.jpg",  "ARENA.zip/ARENA/Plains.23.full.jpg");
        copyUrlToImage.put("arena/76.jpg",  "ARENA.zip/ARENA/Island.24.full.jpg");
        copyUrlToImage.put("arena/77.jpg",  "ARENA.zip/ARENA/Swamp.10.full.jpg");
        copyUrlToImage.put("arena/78.jpg",  "ARENA.zip/ARENA/Mountain.11.full.jpg");
        copyUrlToImage.put("arena/79.jpg",  "ARENA.zip/ARENA/Forest.12.full.jpg");
        copyUrlToImage.put("arena/8.jpg", "ARENA.zip/ARENA/Plains 1.full.jpg");
        copyUrlToImage.put("arena/8.jpg", "ARENA.zip/ARENA/Plains.8.full.jpg");
        copyUrlToImage.put("arena/80.jpg", "ARENA.zip/ARENA/Castigate.full.jpg");
        copyUrlToImage.put("arena/81.jpg", "ARENA.zip/ARENA/Wee Dragonauts.full.jpg");
        copyUrlToImage.put("arena/82.jpg", "ARENA.zip/ARENA/Coiling Oracle.full.jpg");
        copyUrlToImage.put("arena/83.jpg", "ARENA.zip/ARENA/Surging Flame.full.jpg");
        copyUrlToImage.put("arena/9.jpg",  "ARENA.zip/ARENA/Island.2.full.jpg");
        copyUrlToImage.put("euro/1.jpg",  "EURO.zip/EURO/Forest.6.full.jpg");
        copyUrlToImage.put("euro/10.jpg",  "EURO.zip/EURO/Swamp.5.full.jpg");
        copyUrlToImage.put("euro/11.jpg", "EURO.zip/EURO/Forest 2.full.jpg");
        copyUrlToImage.put("euro/11.jpg", "EURO.zip/EURO/Forest.1.full.jpg");
        copyUrlToImage.put("euro/12.jpg",  "EURO.zip/EURO/Island.7.full.jpg");
        copyUrlToImage.put("euro/13.jpg",  "EURO.zip/EURO/Mountain.8.full.jpg");
        copyUrlToImage.put("euro/14.jpg",  "EURO.zip/EURO/Plains.4.full.jpg");
        copyUrlToImage.put("euro/15.jpg", "EURO.zip/EURO/Swamp 2.full.jpg");
        copyUrlToImage.put("euro/15.jpg", "EURO.zip/EURO/Swamp.15.full.jpg");
        copyUrlToImage.put("euro/2.jpg", "EURO.zip/EURO/Island 2.full.jpg");
        copyUrlToImage.put("euro/2.jpg", "EURO.zip/EURO/Island.2.full.jpg");
        copyUrlToImage.put("euro/3.jpg",  "EURO.zip/EURO/Mountain.3.full.jpg");
        copyUrlToImage.put("euro/4.jpg", "EURO.zip/EURO/Plains 2.full.jpg");
        copyUrlToImage.put("euro/4.jpg", "EURO.zip/EURO/Plains.9.full.jpg");
        copyUrlToImage.put("euro/5.jpg",  "EURO.zip/EURO/Swamp.10.full.jpg");
        copyUrlToImage.put("euro/6.jpg",  "EURO.zip/EURO/Forest.11.full.jpg");
        copyUrlToImage.put("euro/7.jpg",  "EURO.zip/EURO/Island.12.full.jpg");
        copyUrlToImage.put("euro/8.jpg",  "EURO.zip/EURO/Mountain.13.full.jpg");
        copyUrlToImage.put("euro/9.jpg",  "EURO.zip/EURO/Plains.14.full.jpg");
        copyUrlToImage.put("exp/1.jpg", "EXP.zip/EXP/Prairie Stream.full.jpg");
        copyUrlToImage.put("exp/10.jpg", "EXP.zip/EXP/Temple Garden.full.jpg");
        copyUrlToImage.put("exp/11.jpg", "EXP.zip/EXP/Godless Shrine.full.jpg");
        copyUrlToImage.put("exp/12.jpg", "EXP.zip/EXP/Steam Vents.full.jpg");
        copyUrlToImage.put("exp/13.jpg", "EXP.zip/EXP/Overgrown Tomb.full.jpg");
        copyUrlToImage.put("exp/14.jpg", "EXP.zip/EXP/Sacred Foundry.full.jpg");
        copyUrlToImage.put("exp/15.jpg", "EXP.zip/EXP/Breeding Pool.full.jpg");
        copyUrlToImage.put("exp/16.jpg", "EXP.zip/EXP/Flooded Strand.full.jpg");
        copyUrlToImage.put("exp/17.jpg", "EXP.zip/EXP/Polluted Delta.full.jpg");
        copyUrlToImage.put("exp/18.jpg", "EXP.zip/EXP/Bloodstained Mire.full.jpg");
        copyUrlToImage.put("exp/19.jpg", "EXP.zip/EXP/Wooded Foothills.full.jpg");
        copyUrlToImage.put("exp/2.jpg", "EXP.zip/EXP/Sunken Hollow.full.jpg");
        copyUrlToImage.put("exp/20.jpg", "EXP.zip/EXP/Windswept Heath.full.jpg");
        copyUrlToImage.put("exp/21.jpg", "EXP.zip/EXP/Marsh Flats.full.jpg");
        copyUrlToImage.put("exp/22.jpg", "EXP.zip/EXP/Scalding Tarn.full.jpg");
        copyUrlToImage.put("exp/23.jpg", "EXP.zip/EXP/Verdant Catacombs.full.jpg");
        copyUrlToImage.put("exp/24.jpg", "EXP.zip/EXP/Arid Mesa.full.jpg");
        copyUrlToImage.put("exp/25.jpg", "EXP.zip/EXP/Misty Rainforest.full.jpg");
        copyUrlToImage.put("exp/3.jpg", "EXP.zip/EXP/Smoldering Marsh.full.jpg");
        copyUrlToImage.put("exp/4.jpg", "EXP.zip/EXP/Cinder Glade.full.jpg");
        copyUrlToImage.put("exp/5.jpg", "EXP.zip/EXP/Canopy Vista.full.jpg");
        copyUrlToImage.put("exp/6.jpg", "EXP.zip/EXP/Hallowed Fountain.full.jpg");
        copyUrlToImage.put("exp/7.jpg", "EXP.zip/EXP/Watery Grave.full.jpg");
        copyUrlToImage.put("exp/8.jpg", "EXP.zip/EXP/Blood Crypt.full.jpg");
        copyUrlToImage.put("exp/9.jpg", "EXP.zip/EXP/Stomping Ground.full.jpg");
        copyUrlToImage.put("fnmp/1.jpg", "FNMP.zip/FNMP/River Boa.full.jpg");
        copyUrlToImage.put("fnmp/10.jpg", "FNMP.zip/FNMP/Stone Rain.full.jpg");
        copyUrlToImage.put("fnmp/100.jpg", "FNMP.zip/FNMP/Thirst for Knowledge.full.jpg");
        copyUrlToImage.put("fnmp/101.jpg", "FNMP.zip/FNMP/Serrated Arrows.full.jpg");
        copyUrlToImage.put("fnmp/102.jpg", "FNMP.zip/FNMP/Isochron Scepter.full.jpg");
        copyUrlToImage.put("fnmp/103.jpg", "FNMP.zip/FNMP/Shrapnel Blast.full.jpg");
        copyUrlToImage.put("fnmp/104.jpg", "FNMP.zip/FNMP/Magma Jet.full.jpg");
        copyUrlToImage.put("fnmp/105.jpg", "FNMP.zip/FNMP/Myr Enforcer.full.jpg");
        copyUrlToImage.put("fnmp/106.jpg", "FNMP.zip/FNMP/Kitchen Finks.full.jpg");
        copyUrlToImage.put("fnmp/107.jpg", "FNMP.zip/FNMP/Merrow Reejerey.full.jpg");
        copyUrlToImage.put("fnmp/108.jpg", "FNMP.zip/FNMP/Wren's Run Vanquisher.full.jpg");
        copyUrlToImage.put("fnmp/109.jpg", "FNMP.zip/FNMP/Mulldrifter.full.jpg");
        copyUrlToImage.put("fnmp/11.jpg", "FNMP.zip/FNMP/Llanowar Elves.full.jpg");
        copyUrlToImage.put("fnmp/110.jpg", "FNMP.zip/FNMP/Murderous Redcap.full.jpg");
        copyUrlToImage.put("fnmp/111.jpg", "FNMP.zip/FNMP/Lightning Greaves.full.jpg");
        copyUrlToImage.put("fnmp/112.jpg", "FNMP.zip/FNMP/Watchwolf.full.jpg");
        copyUrlToImage.put("fnmp/113.jpg", "FNMP.zip/FNMP/Browbeat.full.jpg");
        copyUrlToImage.put("fnmp/114.jpg", "FNMP.zip/FNMP/Oblivion Ring.full.jpg");
        copyUrlToImage.put("fnmp/115.jpg", "FNMP.zip/FNMP/Sakura-Tribe Elder.full.jpg");
        copyUrlToImage.put("fnmp/116.jpg", "FNMP.zip/FNMP/Tidehollow Sculler.full.jpg");
        copyUrlToImage.put("fnmp/117.jpg", "FNMP.zip/FNMP/Ghostly Prison.full.jpg");
        copyUrlToImage.put("fnmp/118.jpg", "FNMP.zip/FNMP/Ancient Ziggurat.full.jpg");
        copyUrlToImage.put("fnmp/119.jpg", "FNMP.zip/FNMP/Bloodbraid Elf.full.jpg");
        copyUrlToImage.put("fnmp/12.jpg", "FNMP.zip/FNMP/Swords to Plowshares.full.jpg");
        copyUrlToImage.put("fnmp/120.jpg", "FNMP.zip/FNMP/Cloudpost.full.jpg");
        copyUrlToImage.put("fnmp/121.jpg", "FNMP.zip/FNMP/Elvish Visionary.full.jpg");
        copyUrlToImage.put("fnmp/122.jpg", "FNMP.zip/FNMP/Anathemancer.full.jpg");
        copyUrlToImage.put("fnmp/123.jpg", "FNMP.zip/FNMP/Krosan Grip.full.jpg");
        copyUrlToImage.put("fnmp/124.jpg", "FNMP.zip/FNMP/Qasali Pridemage.full.jpg");
        copyUrlToImage.put("fnmp/125.jpg", "FNMP.zip/FNMP/Rift Bolt.full.jpg");
        copyUrlToImage.put("fnmp/126.jpg", "FNMP.zip/FNMP/Gatekeeper of Malakir.full.jpg");
        copyUrlToImage.put("fnmp/127.jpg", "FNMP.zip/FNMP/Wild Nacatl.full.jpg");
        copyUrlToImage.put("fnmp/128.jpg", "FNMP.zip/FNMP/Everflowing Chalice.full.jpg");
        copyUrlToImage.put("fnmp/129.jpg", "FNMP.zip/FNMP/Spellstutter Sprite.full.jpg");
        copyUrlToImage.put("fnmp/13.jpg", "FNMP.zip/FNMP/Ophidian.full.jpg");
        copyUrlToImage.put("fnmp/130.jpg", "FNMP.zip/FNMP/Wall of Omens.full.jpg");
        copyUrlToImage.put("fnmp/131.jpg", "FNMP.zip/FNMP/Artisan of Kozilek.full.jpg");
        copyUrlToImage.put("fnmp/132.jpg", "FNMP.zip/FNMP/Squadron Hawk.full.jpg");
        copyUrlToImage.put("fnmp/133.jpg", "FNMP.zip/FNMP/Rhox War Monk.full.jpg");
        copyUrlToImage.put("fnmp/134.jpg", "FNMP.zip/FNMP/Jace's Ingenuity.full.jpg");
        copyUrlToImage.put("fnmp/135.jpg", "FNMP.zip/FNMP/Cultivate.full.jpg");
        copyUrlToImage.put("fnmp/136.jpg", "FNMP.zip/FNMP/Teetering Peaks.full.jpg");
        copyUrlToImage.put("fnmp/137.jpg", "FNMP.zip/FNMP/Contagion Clasp.full.jpg");
        copyUrlToImage.put("fnmp/138.jpg", "FNMP.zip/FNMP/Go for the Throat.full.jpg");
        copyUrlToImage.put("fnmp/139.jpg", "FNMP.zip/FNMP/Savage Lands.full.jpg");
        copyUrlToImage.put("fnmp/14.jpg", "FNMP.zip/FNMP/Jackal Pup.full.jpg");
        copyUrlToImage.put("fnmp/140.jpg", "FNMP.zip/FNMP/Glistener Elf.full.jpg");
        copyUrlToImage.put("fnmp/141.jpg", "FNMP.zip/FNMP/Despise.full.jpg");
        copyUrlToImage.put("fnmp/142.jpg", "FNMP.zip/FNMP/Tectonic Edge.full.jpg");
        copyUrlToImage.put("fnmp/143.jpg", "FNMP.zip/FNMP/Dismember.full.jpg");
        copyUrlToImage.put("fnmp/144.jpg", "FNMP.zip/FNMP/Ancient Grudge.full.jpg");
        copyUrlToImage.put("fnmp/145.jpg", "FNMP.zip/FNMP/Acidic Slime.full.jpg");
        copyUrlToImage.put("fnmp/146.jpg", "FNMP.zip/FNMP/Forbidden Alchemy.full.jpg");
        copyUrlToImage.put("fnmp/147.jpg", "FNMP.zip/FNMP/Avacyn's Pilgrim.full.jpg");
        copyUrlToImage.put("fnmp/148.jpg", "FNMP.zip/FNMP/Lingering Souls.full.jpg");
        copyUrlToImage.put("fnmp/149.jpg", "FNMP.zip/FNMP/Evolving Wilds.full.jpg");
        copyUrlToImage.put("fnmp/15.jpg", "FNMP.zip/FNMP/Quirion Ranger.full.jpg");
        copyUrlToImage.put("fnmp/150.jpg", "FNMP.zip/FNMP/Pillar of Flame.full.jpg");
        copyUrlToImage.put("fnmp/151.jpg", "FNMP.zip/FNMP/Gitaxian Probe.full.jpg");
        copyUrlToImage.put("fnmp/152.jpg", "FNMP.zip/FNMP/Searing Spear.full.jpg");
        copyUrlToImage.put("fnmp/153.jpg", "FNMP.zip/FNMP/Reliquary Tower.full.jpg");
        copyUrlToImage.put("fnmp/154.jpg", "FNMP.zip/FNMP/Farseek.full.jpg");
        copyUrlToImage.put("fnmp/155.jpg", "FNMP.zip/FNMP/Call of the Conclave.full.jpg");
        copyUrlToImage.put("fnmp/156.jpg", "FNMP.zip/FNMP/Judge's Familiar.full.jpg");
        copyUrlToImage.put("fnmp/157.jpg", "FNMP.zip/FNMP/Izzet Charm.full.jpg");
        copyUrlToImage.put("fnmp/158.jpg", "FNMP.zip/FNMP/Rakdos Cackler.full.jpg");
        copyUrlToImage.put("fnmp/159.jpg", "FNMP.zip/FNMP/Dimir Charm.full.jpg");
        copyUrlToImage.put("fnmp/16.jpg", "FNMP.zip/FNMP/Carnophage.full.jpg");
        copyUrlToImage.put("fnmp/160.jpg", "FNMP.zip/FNMP/Experiment One.full.jpg");
        copyUrlToImage.put("fnmp/161.jpg", "FNMP.zip/FNMP/Ghor-Clan Rampager.full.jpg");
        copyUrlToImage.put("fnmp/162.jpg", "FNMP.zip/FNMP/Grisly Salvage.full.jpg");
        copyUrlToImage.put("fnmp/163.jpg", "FNMP.zip/FNMP/Sin Collector.full.jpg");
        copyUrlToImage.put("fnmp/164.jpg", "FNMP.zip/FNMP/Warleader's Helix.full.jpg");
        copyUrlToImage.put("fnmp/165.jpg", "FNMP.zip/FNMP/Elvish Mystic.full.jpg");
        copyUrlToImage.put("fnmp/166.jpg", "FNMP.zip/FNMP/Banisher Priest.full.jpg");
        copyUrlToImage.put("fnmp/167.jpg", "FNMP.zip/FNMP/Encroaching Wastes.full.jpg");
        copyUrlToImage.put("fnmp/168.jpg", "FNMP.zip/FNMP/Tormented Hero.full.jpg");
        copyUrlToImage.put("fnmp/169.jpg", "FNMP.zip/FNMP/Dissolve.full.jpg");
        copyUrlToImage.put("fnmp/17.jpg", "FNMP.zip/FNMP/Impulse.full.jpg");
        copyUrlToImage.put("fnmp/170.jpg", "FNMP.zip/FNMP/Magma Spray.full.jpg");
        copyUrlToImage.put("fnmp/171.jpg", "FNMP.zip/FNMP/Bile Blight.full.jpg");
        copyUrlToImage.put("fnmp/172.jpg", "FNMP.zip/FNMP/Banishing Light.full.jpg");
        copyUrlToImage.put("fnmp/173.jpg", "FNMP.zip/FNMP/Fanatic of Xenagos.full.jpg");
        copyUrlToImage.put("fnmp/174.jpg", "FNMP.zip/FNMP/Brain Maggot.full.jpg");
        copyUrlToImage.put("fnmp/175.jpg", "FNMP.zip/FNMP/Stoke the Flames.full.jpg");
        copyUrlToImage.put("fnmp/176.jpg", "FNMP.zip/FNMP/Frenzied Goblin.full.jpg");
        copyUrlToImage.put("fnmp/177.jpg", "FNMP.zip/FNMP/Disdainful Stroke.full.jpg");
        copyUrlToImage.put("fnmp/178.jpg", "FNMP.zip/FNMP/Hordeling Outburst.full.jpg");
        copyUrlToImage.put("fnmp/179.jpg", "FNMP.zip/FNMP/Suspension Field.full.jpg");
        copyUrlToImage.put("fnmp/18.jpg", "FNMP.zip/FNMP/Fireblast.full.jpg");
        copyUrlToImage.put("fnmp/180.jpg", "FNMP.zip/FNMP/Abzan Beastmaster.full.jpg");
        copyUrlToImage.put("fnmp/181.jpg", "FNMP.zip/FNMP/Frost Walker.full.jpg");
        copyUrlToImage.put("fnmp/182.jpg", "FNMP.zip/FNMP/Path to Exile.full.jpg");
        copyUrlToImage.put("fnmp/183.jpg", "FNMP.zip/FNMP/Serum Visions.full.jpg");
        copyUrlToImage.put("fnmp/184.jpg", "FNMP.zip/FNMP/Orator of Ojutai.full.jpg");
        copyUrlToImage.put("fnmp/185.jpg", "FNMP.zip/FNMP/Ultimate Price.full.jpg");
        copyUrlToImage.put("fnmp/186.jpg", "FNMP.zip/FNMP/Roast.full.jpg");
        copyUrlToImage.put("fnmp/187.jpg", "FNMP.zip/FNMP/Anticipate.full.jpg");
        copyUrlToImage.put("fnmp/188.jpg", "FNMP.zip/FNMP/Nissa's Pilgrimage.full.jpg");
        copyUrlToImage.put("fnmp/189.jpg", "FNMP.zip/FNMP/Clash of Wills.full.jpg");
        copyUrlToImage.put("fnmp/19.jpg", "FNMP.zip/FNMP/Soltari Priest.full.jpg");
        copyUrlToImage.put("fnmp/190.jpg", "FNMP.zip/FNMP/Smash to Smithereens.full.jpg");
        copyUrlToImage.put("fnmp/191.jpg", "FNMP.zip/FNMP/Blighted Fen.full.jpg");
        copyUrlToImage.put("fnmp/2.jpg", "FNMP.zip/FNMP/Terror.full.jpg");
        copyUrlToImage.put("fnmp/20.jpg", "FNMP.zip/FNMP/Albino Troll.full.jpg");
        copyUrlToImage.put("fnmp/21.jpg", "FNMP.zip/FNMP/Dissipate.full.jpg");
        copyUrlToImage.put("fnmp/22.jpg", "FNMP.zip/FNMP/Black Knight.full.jpg");
        copyUrlToImage.put("fnmp/23.jpg", "FNMP.zip/FNMP/Wall of Blossoms.full.jpg");
        copyUrlToImage.put("fnmp/24.jpg", "FNMP.zip/FNMP/Fireslinger.full.jpg");
        copyUrlToImage.put("fnmp/25.jpg", "FNMP.zip/FNMP/Drain Life.full.jpg");
        copyUrlToImage.put("fnmp/26.jpg", "FNMP.zip/FNMP/Aura of Silence.full.jpg");
        copyUrlToImage.put("fnmp/27.jpg", "FNMP.zip/FNMP/Forbid.full.jpg");
        copyUrlToImage.put("fnmp/28.jpg", "FNMP.zip/FNMP/Spike Feeder.full.jpg");
        copyUrlToImage.put("fnmp/29.jpg", "FNMP.zip/FNMP/Mogg Fanatic.full.jpg");
        copyUrlToImage.put("fnmp/3.jpg", "FNMP.zip/FNMP/Longbow Archer.full.jpg");
        copyUrlToImage.put("fnmp/30.jpg", "FNMP.zip/FNMP/White Knight.full.jpg");
        copyUrlToImage.put("fnmp/31.jpg", "FNMP.zip/FNMP/Disenchant.full.jpg");
        copyUrlToImage.put("fnmp/32.jpg", "FNMP.zip/FNMP/Bottle Gnomes.full.jpg");
        copyUrlToImage.put("fnmp/33.jpg", "FNMP.zip/FNMP/Muscle Sliver.full.jpg");
        copyUrlToImage.put("fnmp/34.jpg", "FNMP.zip/FNMP/Crystalline Sliver.full.jpg");
        copyUrlToImage.put("fnmp/35.jpg", "FNMP.zip/FNMP/Capsize.full.jpg");
        copyUrlToImage.put("fnmp/36.jpg", "FNMP.zip/FNMP/Priest of Titania.full.jpg");
        copyUrlToImage.put("fnmp/37.jpg", "FNMP.zip/FNMP/Goblin Bombardment.full.jpg");
        copyUrlToImage.put("fnmp/38.jpg", "FNMP.zip/FNMP/Scragnoth.full.jpg");
        copyUrlToImage.put("fnmp/39.jpg", "FNMP.zip/FNMP/Smother.full.jpg");
        copyUrlToImage.put("fnmp/4.jpg", "FNMP.zip/FNMP/Volcanic Geyser.full.jpg");
        copyUrlToImage.put("fnmp/40.jpg", "FNMP.zip/FNMP/Whipcorder.full.jpg");
        copyUrlToImage.put("fnmp/41.jpg", "FNMP.zip/FNMP/Sparksmith.full.jpg");
        copyUrlToImage.put("fnmp/42.jpg", "FNMP.zip/FNMP/Krosan Tusker.full.jpg");
        copyUrlToImage.put("fnmp/43.jpg", "FNMP.zip/FNMP/Withered Wretch.full.jpg");
        copyUrlToImage.put("fnmp/44.jpg", "FNMP.zip/FNMP/Willbender.full.jpg");
        copyUrlToImage.put("fnmp/45.jpg", "FNMP.zip/FNMP/Slice and Dice.full.jpg");
        copyUrlToImage.put("fnmp/46.jpg", "FNMP.zip/FNMP/Silver Knight.full.jpg");
        copyUrlToImage.put("fnmp/47.jpg", "FNMP.zip/FNMP/Krosan Warchief.full.jpg");
        copyUrlToImage.put("fnmp/48.jpg", "FNMP.zip/FNMP/Lightning Rift.full.jpg");
        copyUrlToImage.put("fnmp/49.jpg", "FNMP.zip/FNMP/Carrion Feeder.full.jpg");
        copyUrlToImage.put("fnmp/5.jpg", "FNMP.zip/FNMP/Mind Warp.full.jpg");
        copyUrlToImage.put("fnmp/50.jpg", "FNMP.zip/FNMP/Treetop Village.full.jpg");
        copyUrlToImage.put("fnmp/51.jpg", "FNMP.zip/FNMP/Accumulated Knowledge.full.jpg");
        copyUrlToImage.put("fnmp/52.jpg", "FNMP.zip/FNMP/Avalanche Riders.full.jpg");
        copyUrlToImage.put("fnmp/53.jpg", "FNMP.zip/FNMP/Reanimate.full.jpg");
        copyUrlToImage.put("fnmp/54.jpg", "FNMP.zip/FNMP/Mother of Runes.full.jpg");
        copyUrlToImage.put("fnmp/55.jpg", "FNMP.zip/FNMP/Brainstorm.full.jpg");
        copyUrlToImage.put("fnmp/56.jpg", "FNMP.zip/FNMP/Rancor.full.jpg");
        copyUrlToImage.put("fnmp/57.jpg", "FNMP.zip/FNMP/Seal of Cleansing.full.jpg");
        copyUrlToImage.put("fnmp/58.jpg", "FNMP.zip/FNMP/Flametongue Kavu.full.jpg");
        copyUrlToImage.put("fnmp/59.jpg", "FNMP.zip/FNMP/Blastoderm.full.jpg");
        copyUrlToImage.put("fnmp/6.jpg", "FNMP.zip/FNMP/Shock.full.jpg");
        copyUrlToImage.put("fnmp/60.jpg", "FNMP.zip/FNMP/Cabal Therapy.full.jpg");
        copyUrlToImage.put("fnmp/61.jpg", "FNMP.zip/FNMP/Fact or Fiction.full.jpg");
        copyUrlToImage.put("fnmp/62.jpg", "FNMP.zip/FNMP/Juggernaut.full.jpg");
        copyUrlToImage.put("fnmp/63.jpg", "FNMP.zip/FNMP/Circle of Protection: Red.full.jpg");
        copyUrlToImage.put("fnmp/64.jpg", "FNMP.zip/FNMP/Kird Ape.full.jpg");
        copyUrlToImage.put("fnmp/65.jpg", "FNMP.zip/FNMP/Duress.full.jpg");
        copyUrlToImage.put("fnmp/66.jpg", "FNMP.zip/FNMP/Counterspell.full.jpg");
        copyUrlToImage.put("fnmp/67.jpg", "FNMP.zip/FNMP/Icy Manipulator.full.jpg");
        copyUrlToImage.put("fnmp/68.jpg", "FNMP.zip/FNMP/Elves of Deep Shadow.full.jpg");
        copyUrlToImage.put("fnmp/69.jpg", "FNMP.zip/FNMP/Armadillo Cloak.full.jpg");
        copyUrlToImage.put("fnmp/7.jpg", "FNMP.zip/FNMP/Staunch Defenders.full.jpg");
        copyUrlToImage.put("fnmp/70.jpg", "FNMP.zip/FNMP/Terminate.full.jpg");
        copyUrlToImage.put("fnmp/71.jpg", "FNMP.zip/FNMP/Lobotomy.full.jpg");
        copyUrlToImage.put("fnmp/72.jpg", "FNMP.zip/FNMP/Goblin Warchief.full.jpg");
        copyUrlToImage.put("fnmp/73.jpg", "FNMP.zip/FNMP/Wild Mongrel.full.jpg");
        copyUrlToImage.put("fnmp/74.jpg", "FNMP.zip/FNMP/Chainer's Edict.full.jpg");
        copyUrlToImage.put("fnmp/75.jpg", "FNMP.zip/FNMP/Circular Logic.full.jpg");
        copyUrlToImage.put("fnmp/76.jpg", "FNMP.zip/FNMP/Astral Slide.full.jpg");
        copyUrlToImage.put("fnmp/77.jpg", "FNMP.zip/FNMP/Arrogant Wurm.full.jpg");
        copyUrlToImage.put("fnmp/78a.jpg", "FNMP.zip/FNMP/Life (Life/Death.full.jpg");
        copyUrlToImage.put("fnmp/78b.jpg", "FNMP.zip/FNMP/Death (Life/Death).full.jpg");
        copyUrlToImage.put("fnmp/79a.jpg", "FNMP.zip/FNMP/Fire (Fire/Ice).full.jpg");
        copyUrlToImage.put("fnmp/79b.jpg", "FNMP.zip/FNMP/Ice (Fire/Ice).full.jpg");
        copyUrlToImage.put("fnmp/8.jpg", "FNMP.zip/FNMP/Giant Growth.full.jpg");
        copyUrlToImage.put("fnmp/80.jpg", "FNMP.zip/FNMP/Firebolt.full.jpg");
        copyUrlToImage.put("fnmp/81.jpg", "FNMP.zip/FNMP/Deep Analysis.full.jpg");
        copyUrlToImage.put("fnmp/82.jpg", "FNMP.zip/FNMP/Gerrard's Verdict.full.jpg");
        copyUrlToImage.put("fnmp/83.jpg", "FNMP.zip/FNMP/Basking Rootwalla.full.jpg");
        copyUrlToImage.put("fnmp/84.jpg", "FNMP.zip/FNMP/Wonder.full.jpg");
        copyUrlToImage.put("fnmp/85.jpg", "FNMP.zip/FNMP/Goblin Legionnaire.full.jpg");
        copyUrlToImage.put("fnmp/86.jpg", "FNMP.zip/FNMP/Engineered Plague.full.jpg");
        copyUrlToImage.put("fnmp/87.jpg", "FNMP.zip/FNMP/Goblin Ringleader.full.jpg");
        copyUrlToImage.put("fnmp/88.jpg", "FNMP.zip/FNMP/Wing Shards.full.jpg");
        copyUrlToImage.put("fnmp/89.jpg", "FNMP.zip/FNMP/Cabal Coffers.full.jpg");
        copyUrlToImage.put("fnmp/9.jpg", "FNMP.zip/FNMP/Prodigal Sorcerer.full.jpg");
        copyUrlToImage.put("fnmp/90.jpg", "FNMP.zip/FNMP/Roar of the Wurm.full.jpg");
        copyUrlToImage.put("fnmp/91.jpg", "FNMP.zip/FNMP/Force Spike.full.jpg");
        copyUrlToImage.put("fnmp/92.jpg", "FNMP.zip/FNMP/Remand.full.jpg");
        copyUrlToImage.put("fnmp/93.jpg", "FNMP.zip/FNMP/Tormod's Crypt.full.jpg");
        copyUrlToImage.put("fnmp/94.jpg", "FNMP.zip/FNMP/Eternal Witness.full.jpg");
        copyUrlToImage.put("fnmp/95.jpg", "FNMP.zip/FNMP/Tendrils of Agony.full.jpg");
        copyUrlToImage.put("fnmp/96.jpg", "FNMP.zip/FNMP/Pendelhaven.full.jpg");
        copyUrlToImage.put("fnmp/97.jpg", "FNMP.zip/FNMP/Resurrection.full.jpg");
        copyUrlToImage.put("fnmp/98.jpg", "FNMP.zip/FNMP/Wall of Roots.full.jpg");
        copyUrlToImage.put("fnmp/99.jpg", "FNMP.zip/FNMP/Desert.full.jpg");
        copyUrlToImage.put("gpx/1.jpg", "GPX.zip/GPX/Spiritmonger.full.jpg");
        copyUrlToImage.put("gpx/10.jpg", "GPX.zip/GPX/Batterskull.full.jpg");
        copyUrlToImage.put("gpx/11.jpg", "GPX.zip/GPX/Griselbrand.full.jpg");
        copyUrlToImage.put("gpx/12.jpg", "GPX.zip/GPX/Stoneforge Mystic.full.jpg");
        copyUrlToImage.put("gpx/2.jpg", "GPX.zip/GPX/Call of the Herd.full.jpg");
        copyUrlToImage.put("gpx/3.jpg", "GPX.zip/GPX/Chrome Mox.full.jpg");
        copyUrlToImage.put("gpx/4.jpg", "GPX.zip/GPX/Umezawa's Jitte.full.jpg");
        copyUrlToImage.put("gpx/5.jpg", "GPX.zip/GPX/Maelstrom Pulse.full.jpg");
        copyUrlToImage.put("gpx/6.jpg", "GPX.zip/GPX/Goblin Guide.full.jpg");
        copyUrlToImage.put("gpx/7.jpg", "GPX.zip/GPX/Lotus Cobra.full.jpg");
        copyUrlToImage.put("gpx/8.jpg", "GPX.zip/GPX/Primeval Titan.full.jpg");
        copyUrlToImage.put("gpx/9.jpg", "GPX.zip/GPX/All Is Dust.full.jpg");
        copyUrlToImage.put("grc/1.jpg", "GRC.zip/GRC/Wood Elves.full.jpg");
        copyUrlToImage.put("grc/10.jpg", "GRC.zip/GRC/Mogg Fanatic.full.jpg");
        copyUrlToImage.put("grc/11.jpg", "GRC.zip/GRC/Mind Stone.full.jpg");
        copyUrlToImage.put("grc/12.jpg", "GRC.zip/GRC/Dauntless Dourbark.full.jpg");
        copyUrlToImage.put("grc/13.jpg", "GRC.zip/GRC/Lava Axe.full.jpg");
        copyUrlToImage.put("grc/14.jpg", "GRC.zip/GRC/Cenn's Tactician.full.jpg");
        copyUrlToImage.put("grc/15.jpg", "GRC.zip/GRC/Oona's Blackguard.full.jpg");
        copyUrlToImage.put("grc/16.jpg", "GRC.zip/GRC/Gravedigger.full.jpg");
        copyUrlToImage.put("grc/17.jpg", "GRC.zip/GRC/Boggart Ram-Gang.full.jpg");
        copyUrlToImage.put("grc/18.jpg", "GRC.zip/GRC/Wilt-Leaf Cavaliers.full.jpg");
        copyUrlToImage.put("grc/19.jpg", "GRC.zip/GRC/Duergar Hedge-Mage.full.jpg");
        copyUrlToImage.put("grc/2.jpg", "GRC.zip/GRC/Icatian Javelineers.full.jpg");
        copyUrlToImage.put("grc/20.jpg", "GRC.zip/GRC/Selkie Hedge-Mage.full.jpg");
        copyUrlToImage.put("grc/21.jpg", "GRC.zip/GRC/Sprouting Thrinax.full.jpg");
        copyUrlToImage.put("grc/22.jpg", "GRC.zip/GRC/Woolly Thoctar.full.jpg");
        copyUrlToImage.put("grc/24.jpg", "GRC.zip/GRC/Path to Exile.full.jpg");
        copyUrlToImage.put("grc/25.jpg", "GRC.zip/GRC/Hellspark Elemental.full.jpg");
        copyUrlToImage.put("grc/26.jpg", "GRC.zip/GRC/Marisi's Twinclaws.full.jpg");
        copyUrlToImage.put("grc/27.jpg", "GRC.zip/GRC/Slave of Bolas.full.jpg");
        copyUrlToImage.put("grc/28.jpg", "GRC.zip/GRC/Mycoid Shepherd.full.jpg");
        copyUrlToImage.put("grc/29.jpg", "GRC.zip/GRC/Naya Sojourners.full.jpg");
        copyUrlToImage.put("grc/3.jpg", "GRC.zip/GRC/Fiery Temper.full.jpg");
        copyUrlToImage.put("grc/30.jpg", "GRC.zip/GRC/Mind Control.full.jpg");
        copyUrlToImage.put("grc/31.jpg", "GRC.zip/GRC/Rise from the Grave.full.jpg");
        copyUrlToImage.put("grc/32.jpg", "GRC.zip/GRC/Kor Duelist.full.jpg");
        copyUrlToImage.put("grc/33.jpg", "GRC.zip/GRC/Vampire Nighthawk.full.jpg");
        copyUrlToImage.put("grc/34.jpg", "GRC.zip/GRC/Nissa's Chosen.full.jpg");
        copyUrlToImage.put("grc/35.jpg", "GRC.zip/GRC/Emeria Angel.full.jpg");
        copyUrlToImage.put("grc/36.jpg", "GRC.zip/GRC/Kor Firewalker.full.jpg");
        copyUrlToImage.put("grc/37.jpg", "GRC.zip/GRC/Leatherback Baloth.full.jpg");
        copyUrlToImage.put("grc/38.jpg", "GRC.zip/GRC/Hada Freeblade.full.jpg");
        copyUrlToImage.put("grc/39.jpg", "GRC.zip/GRC/Kalastria Highborn.full.jpg");
        copyUrlToImage.put("grc/4.jpg", "GRC.zip/GRC/Boomerang.full.jpg");
        copyUrlToImage.put("grc/40.jpg", "GRC.zip/GRC/Syphon Mind.full.jpg");
        copyUrlToImage.put("grc/46.jpg", "GRC.zip/GRC/Pathrazer of Ulamog.full.jpg");
        copyUrlToImage.put("grc/47.jpg", "GRC.zip/GRC/Curse of Wizardry.full.jpg");
        copyUrlToImage.put("grc/48.jpg", "GRC.zip/GRC/Staggershock.full.jpg");
        copyUrlToImage.put("grc/49.jpg", "GRC.zip/GRC/Deathless Angel.full.jpg");
        copyUrlToImage.put("grc/5.jpg", "GRC.zip/GRC/Calciderm.full.jpg");
        copyUrlToImage.put("grc/50.jpg", "GRC.zip/GRC/Fling 2.full.jpg");
        copyUrlToImage.put("grc/51.jpg", "GRC.zip/GRC/Sylvan Ranger 2.full.jpg");
        copyUrlToImage.put("grc/59.jpg", "GRC.zip/GRC/Plague Stinger.full.jpg");
        copyUrlToImage.put("grc/6.jpg", "GRC.zip/GRC/Reckless Wurm.full.jpg");
        copyUrlToImage.put("grc/60.jpg", "GRC.zip/GRC/Golem's Heart.full.jpg");
        copyUrlToImage.put("grc/63.jpg", "GRC.zip/GRC/Skinrender.full.jpg");
        copyUrlToImage.put("grc/64.jpg", "GRC.zip/GRC/Master's Call.full.jpg");
        copyUrlToImage.put("grc/65.jpg", "GRC.zip/GRC/Plague Myr.full.jpg");
        copyUrlToImage.put("grc/66.jpg", "GRC.zip/GRC/Signal Pest.full.jpg");
        copyUrlToImage.put("grc/69.jpg", "GRC.zip/GRC/Fling 1.full.jpg");
        copyUrlToImage.put("grc/7.jpg", "GRC.zip/GRC/Yixlid Jailer.full.jpg");
        copyUrlToImage.put("grc/70.jpg", "GRC.zip/GRC/Sylvan Ranger 1.full.jpg");
        copyUrlToImage.put("grc/71.jpg", "GRC.zip/GRC/Vault Skirge.full.jpg");
        copyUrlToImage.put("grc/72.jpg", "GRC.zip/GRC/Maul Splicer.full.jpg");
        copyUrlToImage.put("grc/73.jpg", "GRC.zip/GRC/Shrine of Burning Rage.full.jpg");
        copyUrlToImage.put("grc/76.jpg", "GRC.zip/GRC/Tormented Soul.full.jpg");
        copyUrlToImage.put("grc/77.jpg", "GRC.zip/GRC/Auramancer.full.jpg");
        copyUrlToImage.put("grc/78.jpg", "GRC.zip/GRC/Circle of Flame.full.jpg");
        copyUrlToImage.put("grc/79.jpg", "GRC.zip/GRC/Gather the Townsfolk.full.jpg");
        copyUrlToImage.put("grc/8.jpg", "GRC.zip/GRC/Zoetic Cavern.full.jpg");
        copyUrlToImage.put("grc/80.jpg", "GRC.zip/GRC/Curse of the Bloody Tome.full.jpg");
        copyUrlToImage.put("grc/81.jpg", "GRC.zip/GRC/Curse of Thirst.full.jpg");
        copyUrlToImage.put("grc/82.jpg", "GRC.zip/GRC/Nearheath Stalker.full.jpg");
        copyUrlToImage.put("grc/83.jpg", "GRC.zip/GRC/Bloodcrazed Neonate.full.jpg");
        copyUrlToImage.put("grc/84.jpg", "GRC.zip/GRC/Boneyard Wurm.full.jpg");
        copyUrlToImage.put("grc/9.jpg", "GRC.zip/GRC/Llanowar Elves.full.jpg");
        copyUrlToImage.put("jr/1.jpg", "JP.zip/JP/Lightning Bolt.full.jpg");
        copyUrlToImage.put("jr/10.jpg", "JP.zip/JP/Tradewind Rider.full.jpg");
        copyUrlToImage.put("jr/100.jpg", "JP.zip/JP/Feldon of the Third Path.full.jpg");
        copyUrlToImage.put("jr/101.jpg", "JP.zip/JP/Wasteland 2.full.jpg");
        copyUrlToImage.put("jr/103.jpg", "JP.zip/JP/Mana Drain.full.jpg");
        copyUrlToImage.put("jr/105.jpg", "JP.zip/JP/Command Beacon.full.jpg");
        copyUrlToImage.put("jr/11.jpg", "JP.zip/JP/Intuition.full.jpg");
        copyUrlToImage.put("jr/12.jpg", "JP.zip/JP/Argothian Enchantress.full.jpg");
        copyUrlToImage.put("jr/13.jpg", "JP.zip/JP/Living Death.full.jpg");
        copyUrlToImage.put("jr/14.jpg", "JP.zip/JP/Armageddon.full.jpg");
        copyUrlToImage.put("jr/15.jpg", "JP.zip/JP/Balance.full.jpg");
        copyUrlToImage.put("jr/16.jpg", "JP.zip/JP/Time Warp.full.jpg");
        copyUrlToImage.put("jr/17.jpg", "JP.zip/JP/Phyrexian Negator.full.jpg");
        copyUrlToImage.put("jr/18.jpg", "JP.zip/JP/Deranged Hermit.full.jpg");
        copyUrlToImage.put("jr/19.jpg", "JP.zip/JP/Hermit Druid.full.jpg");
        copyUrlToImage.put("jr/2.jpg", "JP.zip/JP/Stroke of Genius.full.jpg");
        copyUrlToImage.put("jr/20.jpg", "JP.zip/JP/Gemstone Mine.full.jpg");
        copyUrlToImage.put("jr/21.jpg", "JP.zip/JP/Regrowth.full.jpg");
        copyUrlToImage.put("jr/22.jpg", "JP.zip/JP/Sol Ring.full.jpg");
        copyUrlToImage.put("jr/23.jpg", "JP.zip/JP/Mishra's Factory.full.jpg");
        copyUrlToImage.put("jr/24.jpg", "JP.zip/JP/Exalted Angel.full.jpg");
        copyUrlToImage.put("jr/25.jpg", "JP.zip/JP/Grim Lavamancer.full.jpg");
        copyUrlToImage.put("jr/26.jpg", "JP.zip/JP/Meddling Mage.full.jpg");
        copyUrlToImage.put("jr/27.jpg", "JP.zip/JP/Pernicious Deed.full.jpg");
        copyUrlToImage.put("jr/28.jpg", "JP.zip/JP/Ravenous Baloth.full.jpg");
        copyUrlToImage.put("jr/29.jpg", "JP.zip/JP/Cunning Wish.full.jpg");
        copyUrlToImage.put("jr/3.jpg", "JP.zip/JP/Gaea's Cradle.full.jpg");
        copyUrlToImage.put("jr/30.jpg", "JP.zip/JP/Yawgmoth's Will.full.jpg");
        copyUrlToImage.put("jr/31.jpg", "JP.zip/JP/Vindicate 2.full.jpg");
        copyUrlToImage.put("jr/32.jpg", "JP.zip/JP/Decree of Justice.full.jpg");
        copyUrlToImage.put("jr/33.jpg", "JP.zip/JP/Orim's Chant.full.jpg");
        copyUrlToImage.put("jr/34.jpg", "JP.zip/JP/Mind's Desire.full.jpg");
        copyUrlToImage.put("jr/35.jpg", "JP.zip/JP/Demonic Tutor.full.jpg");
        copyUrlToImage.put("jr/36.jpg", "JP.zip/JP/Goblin Piledriver.full.jpg");
        copyUrlToImage.put("jr/37.jpg", "JP.zip/JP/Living Wish.full.jpg");
        copyUrlToImage.put("jr/38.jpg", "JP.zip/JP/Dark Ritual.full.jpg");
        copyUrlToImage.put("jr/39.jpg", "JP.zip/JP/Maze of Ith.full.jpg");
        copyUrlToImage.put("jr/4.jpg", "JP.zip/JP/Memory Lapse.full.jpg");
        copyUrlToImage.put("jr/40.jpg", "JP.zip/JP/Stifle.full.jpg");
        copyUrlToImage.put("jr/41.jpg", "JP.zip/JP/Survival of the Fittest.full.jpg");
        copyUrlToImage.put("jr/42.jpg", "JP.zip/JP/Burning Wish.full.jpg");
        copyUrlToImage.put("jr/43.jpg", "JP.zip/JP/Bloodstained Mire.full.jpg");
        copyUrlToImage.put("jr/44.jpg", "JP.zip/JP/Flooded Strand.full.jpg");
        copyUrlToImage.put("jr/45.jpg", "JP.zip/JP/Polluted Delta.full.jpg");
        copyUrlToImage.put("jr/46.jpg", "JP.zip/JP/Windswept Heath.full.jpg");
        copyUrlToImage.put("jr/47.jpg", "JP.zip/JP/Wooded Foothills.full.jpg");
        copyUrlToImage.put("jr/48.jpg", "JP.zip/JP/Sinkhole.full.jpg");
        copyUrlToImage.put("jr/49.jpg", "JP.zip/JP/Natural Order.full.jpg");
        copyUrlToImage.put("jr/5.jpg", "JP.zip/JP/Counterspell.full.jpg");
        copyUrlToImage.put("jr/50.jpg", "JP.zip/JP/Phyrexian Dreadnought.full.jpg");
        copyUrlToImage.put("jr/51.jpg", "JP.zip/JP/Thawing Glaciers.full.jpg");
        copyUrlToImage.put("jr/52.jpg", "JP.zip/JP/Land Tax.full.jpg");
        copyUrlToImage.put("jr/53.jpg", "JP.zip/JP/Morphling.full.jpg");
        copyUrlToImage.put("jr/54.jpg", "JP.zip/JP/Wheel of Fortune.full.jpg");
        copyUrlToImage.put("jr/55.jpg", "JP.zip/JP/Wasteland 1.full.jpg");
        copyUrlToImage.put("jr/56.jpg", "JP.zip/JP/Entomb.full.jpg");
        copyUrlToImage.put("jr/57.jpg", "JP.zip/JP/Sword of Fire and Ice.full.jpg");
        copyUrlToImage.put("jr/58.jpg", "JP.zip/JP/Vendilion Clique.full.jpg");
        copyUrlToImage.put("jr/59.jpg", "JP.zip/JP/Bitterblossom.full.jpg");
        copyUrlToImage.put("jr/6.jpg", "JP.zip/JP/Vampiric Tutor.full.jpg");
        copyUrlToImage.put("jr/60.jpg", "JP.zip/JP/Mana Crypt.full.jpg");
        copyUrlToImage.put("jr/61.jpg", "JP.zip/JP/Dark Confidant.full.jpg");
        copyUrlToImage.put("jr/62.jpg", "JP.zip/JP/Doubling Season.full.jpg");
        copyUrlToImage.put("jr/63.jpg", "JP.zip/JP/Goblin Welder.full.jpg");
        copyUrlToImage.put("jr/64.jpg", "JP.zip/JP/Xiahou Dun, the One-Eyed.full.jpg");
        copyUrlToImage.put("jr/65.jpg", "JP.zip/JP/Flusterstorm.full.jpg");
        copyUrlToImage.put("jr/66.jpg", "JP.zip/JP/Noble Hierarch.full.jpg");
        copyUrlToImage.put("jr/67.jpg", "JP.zip/JP/Karmic Guide.full.jpg");
        copyUrlToImage.put("jr/68.jpg", "JP.zip/JP/Sneak Attack.full.jpg");
        copyUrlToImage.put("jr/69.jpg", "JP.zip/JP/Karakas.full.jpg");
        copyUrlToImage.put("jr/7.jpg", "JP.zip/JP/Ball Lightning.full.jpg");
        copyUrlToImage.put("jr/70.jpg", "JP.zip/JP/Sword of Light and Shadow.full.jpg");
        copyUrlToImage.put("jr/71.jpg", "JP.zip/JP/Command Tower.full.jpg");
        copyUrlToImage.put("jr/72.jpg", "JP.zip/JP/Swords to Plowshares.full.jpg");
        copyUrlToImage.put("jr/73.jpg", "JP.zip/JP/Bribery.full.jpg");
        copyUrlToImage.put("jr/74.jpg", "JP.zip/JP/Imperial Recruiter.full.jpg");
        copyUrlToImage.put("jr/75.jpg", "JP.zip/JP/Crucible of Worlds.full.jpg");
        copyUrlToImage.put("jr/76.jpg", "JP.zip/JP/Overwhelming Forces.full.jpg");
        copyUrlToImage.put("jr/77.jpg", "JP.zip/JP/Show and Tell.full.jpg");
        copyUrlToImage.put("jr/78.jpg", "JP.zip/JP/Vindicate 1.full.jpg");
        copyUrlToImage.put("jr/79.jpg", "JP.zip/JP/Genesis.full.jpg");
        copyUrlToImage.put("jr/8.jpg", "JP.zip/JP/Oath of Druids.full.jpg");
        copyUrlToImage.put("jr/80.jpg", "JP.zip/JP/Karador, Ghost Chieftain.full.jpg");
        copyUrlToImage.put("jr/81.jpg", "JP.zip/JP/Greater Good.full.jpg");
        copyUrlToImage.put("jr/82.jpg", "JP.zip/JP/Riku of Two Reflections.full.jpg");
        copyUrlToImage.put("jr/83.jpg", "JP.zip/JP/Force of Will.full.jpg");
        copyUrlToImage.put("jr/84.jpg", "JP.zip/JP/Hanna, Ship's Navigator.full.jpg");
        copyUrlToImage.put("jr/85.jpg", "JP.zip/JP/Sword of Feast and Famine.full.jpg");
        copyUrlToImage.put("jr/86.jpg", "JP.zip/JP/Nekusar, the Mindrazer.full.jpg");
        copyUrlToImage.put("jr/87.jpg", "JP.zip/JP/Elesh Norn, Grand Cenobite.full.jpg");
        copyUrlToImage.put("jr/88.jpg", "JP.zip/JP/Oloro, Ageless Ascetic.full.jpg");
        copyUrlToImage.put("jr/89.jpg", "JP.zip/JP/Plains.full.jpg");
        copyUrlToImage.put("jr/9.jpg", "JP.zip/JP/Hammer of Bogardan.full.jpg");
        copyUrlToImage.put("jr/90.jpg", "JP.zip/JP/Island.full.jpg");
        copyUrlToImage.put("jr/91.jpg", "JP.zip/JP/Swamp.full.jpg");
        copyUrlToImage.put("jr/92.jpg", "JP.zip/JP/Mountain.full.jpg");
        copyUrlToImage.put("jr/93.jpg", "JP.zip/JP/Forest.full.jpg");
        copyUrlToImage.put("jr/97.jpg", "JP.zip/JP/Ravages of War.full.jpg");
        copyUrlToImage.put("jr/98.jpg", "JP.zip/JP/Damnation.full.jpg");
        copyUrlToImage.put("jr/99.jpg", "JP.zip/JP/Dualcaster Mage.full.jpg");
        copyUrlToImage.put("mlp/1.jpg", "MLP.zip/MLP/Earwig Squad.full.jpg");
        copyUrlToImage.put("mlp/10.jpg", "MLP.zip/MLP/Lord of Shatterskull Pass.full.jpg");
        copyUrlToImage.put("mlp/11.jpg", "MLP.zip/MLP/Ancient Hellkite.full.jpg");
        copyUrlToImage.put("mlp/12.jpg", "MLP.zip/MLP/Steel Hellkite.full.jpg");
        copyUrlToImage.put("mlp/13.jpg", "MLP.zip/MLP/Thopter Assembly.full.jpg");
        copyUrlToImage.put("mlp/14.jpg", "MLP.zip/MLP/Phyrexian Metamorph.full.jpg");
        copyUrlToImage.put("mlp/15.jpg", "MLP.zip/MLP/Garruk's Horde.full.jpg");
        copyUrlToImage.put("mlp/16a.jpg", "MLP.zip/MLP/Ludevic's Test Subject.full.jpg");
        copyUrlToImage.put("mlp/16b.jpg", "MLP.zip/MLP/Ludevic's Abomination.full.jpg");
        copyUrlToImage.put("mlp/17a.jpg", "MLP.zip/MLP/Mondronen Shaman.full.jpg");
        copyUrlToImage.put("mlp/17b.jpg", "MLP.zip/MLP/Tovolar's Magehunter.full.jpg");
        copyUrlToImage.put("mlp/18.jpg", "MLP.zip/MLP/Restoration Angel.full.jpg");
        copyUrlToImage.put("mlp/19.jpg", "MLP.zip/MLP/Staff of Nin.full.jpg");
        copyUrlToImage.put("mlp/2.jpg", "MLP.zip/MLP/Vexing Shusher.full.jpg");
        copyUrlToImage.put("mlp/20.jpg", "MLP.zip/MLP/Deadbridge Goliath.full.jpg");
        copyUrlToImage.put("mlp/21.jpg", "MLP.zip/MLP/Skarrg Goliath.full.jpg");
        copyUrlToImage.put("mlp/22a.jpg", "MLP.zip/MLP/Breaking (Breaking/Entering).full.jpg");
        copyUrlToImage.put("mlp/22b.jpg", "MLP.zip/MLP/Entering (Breaking/Entering).full.jpg");
        copyUrlToImage.put("mlp/23.jpg", "MLP.zip/MLP/Colossal Whale.full.jpg");
        copyUrlToImage.put("mlp/24.jpg", "MLP.zip/MLP/Bident of Thassa.full.jpg");
        copyUrlToImage.put("mlp/25.jpg", "MLP.zip/MLP/Tromokratis.full.jpg");
        copyUrlToImage.put("mlp/26.jpg", "MLP.zip/MLP/Dictate of the Twin Gods.full.jpg");
        copyUrlToImage.put("mlp/27.jpg", "MLP.zip/MLP/Dragone Throne of Tarkir.full.jpg");
        copyUrlToImage.put("mlp/28.jpg", "MLP.zip/MLP/In Garruk's Wake.full.jpg");
        copyUrlToImage.put("mlp/29.jpg", "MLP.zip/MLP/Sandsteppe Mastodon.full.jpg");
        copyUrlToImage.put("mlp/3.jpg", "MLP.zip/MLP/Figure of Destiny.full.jpg");
        copyUrlToImage.put("mlp/31.jpg", "MLP.zip/MLP/Deathbringer Regent.full.jpg");
        copyUrlToImage.put("mlp/32.jpg", "MLP.zip/MLP/Mizzium Meddler.full.jpg");
        copyUrlToImage.put("mlp/33.jpg", "MLP.zip/MLP/Blight Herder.full.jpg");
        copyUrlToImage.put("mlp/34.jpg", "MLP.zip/MLP/Endbringer.full.jpg");
        copyUrlToImage.put("mlp/4.jpg", "MLP.zip/MLP/Ajani Vengeant.full.jpg");
        copyUrlToImage.put("mlp/5.jpg", "MLP.zip/MLP/Obelisk of Alara.full.jpg");
        copyUrlToImage.put("mlp/6.jpg", "MLP.zip/MLP/Knight of New Alara.full.jpg");
        copyUrlToImage.put("mlp/7.jpg", "MLP.zip/MLP/Ant Queen.full.jpg");
        copyUrlToImage.put("mlp/8.jpg", "MLP.zip/MLP/Valakut, the Molten Pinnacle.full.jpg");
        copyUrlToImage.put("mlp/9.jpg", "MLP.zip/MLP/Joraga Warcaller.full.jpg");
        copyUrlToImage.put("mprp/1.jpg", "MPRP.zip/MPRP/Wasteland.full.jpg");
        copyUrlToImage.put("mprp/10.jpg", "MPRP.zip/MPRP/Hypnotic Specter.full.jpg");
        copyUrlToImage.put("mprp/11.jpg", "MPRP.zip/MPRP/Hinder.full.jpg");
        copyUrlToImage.put("mprp/12.jpg", "MPRP.zip/MPRP/Pyroclasm.full.jpg");
        copyUrlToImage.put("mprp/13.jpg", "MPRP.zip/MPRP/Giant Growth.full.jpg");
        copyUrlToImage.put("mprp/14.jpg", "MPRP.zip/MPRP/Putrefy.full.jpg");
        copyUrlToImage.put("mprp/15.jpg", "MPRP.zip/MPRP/Zombify.full.jpg");
        copyUrlToImage.put("mprp/16.jpg", "MPRP.zip/MPRP/Lightning Helix.full.jpg");
        copyUrlToImage.put("mprp/17.jpg", "MPRP.zip/MPRP/Wrath of God.full.jpg");
        copyUrlToImage.put("mprp/18.jpg", "MPRP.zip/MPRP/Condemn.full.jpg");
        copyUrlToImage.put("mprp/19.jpg", "MPRP.zip/MPRP/Mortify.full.jpg");
        copyUrlToImage.put("mprp/2.jpg", "MPRP.zip/MPRP/Voidmage Prodigy.full.jpg");
        copyUrlToImage.put("mprp/20.jpg", "MPRP.zip/MPRP/Psionic Blast.full.jpg");
        copyUrlToImage.put("mprp/21.jpg", "MPRP.zip/MPRP/Cruel Edict.full.jpg");
        copyUrlToImage.put("mprp/22.jpg", "MPRP.zip/MPRP/Disenchant.full.jpg");
        copyUrlToImage.put("mprp/23.jpg", "MPRP.zip/MPRP/Recollect.full.jpg");
        copyUrlToImage.put("mprp/24.jpg", "MPRP.zip/MPRP/Damnation.full.jpg");
        copyUrlToImage.put("mprp/25.jpg", "MPRP.zip/MPRP/Tidings.full.jpg");
        copyUrlToImage.put("mprp/26.jpg", "MPRP.zip/MPRP/Incinerate.full.jpg");
        copyUrlToImage.put("mprp/27.jpg", "MPRP.zip/MPRP/Mana Tithe.full.jpg");
        copyUrlToImage.put("mprp/28.jpg", "MPRP.zip/MPRP/Harmonize.full.jpg");
        copyUrlToImage.put("mprp/29.jpg", "MPRP.zip/MPRP/Ponder.full.jpg");
        copyUrlToImage.put("mprp/3.jpg", "MPRP.zip/MPRP/Powder Keg.full.jpg");
        copyUrlToImage.put("mprp/30.jpg", "MPRP.zip/MPRP/Corrupt.full.jpg");
        copyUrlToImage.put("mprp/31.jpg", "MPRP.zip/MPRP/Cryptic Command.full.jpg");
        copyUrlToImage.put("mprp/32.jpg", "MPRP.zip/MPRP/Flame Javelin.full.jpg");
        copyUrlToImage.put("mprp/33.jpg", "MPRP.zip/MPRP/Unmake.full.jpg");
        copyUrlToImage.put("mprp/34.jpg", "MPRP.zip/MPRP/Nameless Inversion.full.jpg");
        copyUrlToImage.put("mprp/35.jpg", "MPRP.zip/MPRP/Remove Soul.full.jpg");
        copyUrlToImage.put("mprp/36.jpg", "MPRP.zip/MPRP/Blightning.full.jpg");
        copyUrlToImage.put("mprp/37.jpg", "MPRP.zip/MPRP/Rampant Growth.full.jpg");
        copyUrlToImage.put("mprp/38.jpg", "MPRP.zip/MPRP/Negate.full.jpg");
        copyUrlToImage.put("mprp/39.jpg", "MPRP.zip/MPRP/Terminate.full.jpg");
        copyUrlToImage.put("mprp/4.jpg", "MPRP.zip/MPRP/Psychatog.full.jpg");
        copyUrlToImage.put("mprp/40.jpg", "MPRP.zip/MPRP/Lightning Bolt.full.jpg");
        copyUrlToImage.put("mprp/41.jpg", "MPRP.zip/MPRP/Cancel.full.jpg");
        copyUrlToImage.put("mprp/42.jpg", "MPRP.zip/MPRP/Sign in Blood.full.jpg");
        copyUrlToImage.put("mprp/43.jpg", "MPRP.zip/MPRP/Infest.full.jpg");
        copyUrlToImage.put("mprp/44.jpg", "MPRP.zip/MPRP/Volcanic Fallout.full.jpg");
        copyUrlToImage.put("mprp/45.jpg", "MPRP.zip/MPRP/Celestial Purge.full.jpg");
        copyUrlToImage.put("mprp/46.jpg", "MPRP.zip/MPRP/Bituminous Blast.full.jpg");
        copyUrlToImage.put("mprp/47.jpg", "MPRP.zip/MPRP/Burst Lightning.full.jpg");
        copyUrlToImage.put("mprp/48.jpg", "MPRP.zip/MPRP/Harrow.full.jpg");
        copyUrlToImage.put("mprp/49.jpg", "MPRP.zip/MPRP/Day of Judgment.full.jpg");
        copyUrlToImage.put("mprp/5.jpg", "MPRP.zip/MPRP/Terror.full.jpg");
        copyUrlToImage.put("mprp/50.jpg", "MPRP.zip/MPRP/Brave the Elements.full.jpg");
        copyUrlToImage.put("mprp/51.jpg", "MPRP.zip/MPRP/Doom Blade.full.jpg");
        copyUrlToImage.put("mprp/52.jpg", "MPRP.zip/MPRP/Treasure Hunt.full.jpg");
        copyUrlToImage.put("mprp/53.jpg", "MPRP.zip/MPRP/Searing Blaze.full.jpg");
        copyUrlToImage.put("mprp/6.jpg", "MPRP.zip/MPRP/Fireball.full.jpg");
        copyUrlToImage.put("mprp/7.jpg", "MPRP.zip/MPRP/Oxidize.full.jpg");
        copyUrlToImage.put("mprp/8.jpg", "MPRP.zip/MPRP/Mana Leak.full.jpg");
        copyUrlToImage.put("mprp/9.jpg", "MPRP.zip/MPRP/Reciprocate.full.jpg");
        copyUrlToImage.put("ptc/1.jpg", "PTC.zip/PTC/Dirtcowl Wurm.full.jpg");
        copyUrlToImage.put("ptc/10.jpg", "PTC.zip/PTC/Rathi Assassin.full.jpg");
        copyUrlToImage.put("ptc/100.jpg", "PTC.zip/PTC/Flying Crane Technique.full.jpg");
        copyUrlToImage.put("ptc/101.jpg", "PTC.zip/PTC/Grim Haruspex.full.jpg");
        copyUrlToImage.put("ptc/102.jpg", "PTC.zip/PTC/Hardened Scales.full.jpg");
        copyUrlToImage.put("ptc/103.jpg", "PTC.zip/PTC/Herald of Anafenza.full.jpg");
        copyUrlToImage.put("ptc/104.jpg", "PTC.zip/PTC/High Sentinels of Arashin.full.jpg");
        copyUrlToImage.put("ptc/105.jpg", "PTC.zip/PTC/Icy Blast.full.jpg");
        copyUrlToImage.put("ptc/106.jpg", "PTC.zip/PTC/Ivorytusk Fortress.full.jpg");
        copyUrlToImage.put("ptc/107.jpg", "PTC.zip/PTC/Jeering Instigator.full.jpg");
        copyUrlToImage.put("ptc/108.jpg", "PTC.zip/PTC/Jeskai Ascendancy.full.jpg");
        copyUrlToImage.put("ptc/109.jpg", "PTC.zip/PTC/Kheru Lich Lord.full.jpg");
        copyUrlToImage.put("ptc/11.jpg", "PTC.zip/PTC/Avatar of Hope.full.jpg");
        copyUrlToImage.put("ptc/110.jpg", "PTC.zip/PTC/Mardu Ascendancy.full.jpg");
        copyUrlToImage.put("ptc/111.jpg", "PTC.zip/PTC/Master of Pearls.full.jpg");
        copyUrlToImage.put("ptc/112.jpg", "PTC.zip/PTC/Narset, Enlightened Master.full.jpg");
        copyUrlToImage.put("ptc/113.jpg", "PTC.zip/PTC/Necropolis Fiend.full.jpg");
        copyUrlToImage.put("ptc/114.jpg", "PTC.zip/PTC/Rakshasa Vizier.full.jpg");
        copyUrlToImage.put("ptc/115.jpg", "PTC.zip/PTC/Rattleclaw Mystic.full.jpg");
        copyUrlToImage.put("ptc/116.jpg", "PTC.zip/PTC/Sage of the Inward Eye.full.jpg");
        copyUrlToImage.put("ptc/117.jpg", "PTC.zip/PTC/Sidisi, Brood Tyrant.full.jpg");
        copyUrlToImage.put("ptc/118.jpg", "PTC.zip/PTC/Siege Rhino.full.jpg");
        copyUrlToImage.put("ptc/119.jpg", "PTC.zip/PTC/Sultai Ascendacy.full.jpg");
        copyUrlToImage.put("ptc/12.jpg", "PTC.zip/PTC/Raging Kavu.full.jpg");
        copyUrlToImage.put("ptc/120.jpg", "PTC.zip/PTC/Surrak Dragonclaw.full.jpg");
        copyUrlToImage.put("ptc/121.jpg", "PTC.zip/PTC/Temur Ascendancy.full.jpg");
        copyUrlToImage.put("ptc/122.jpg", "PTC.zip/PTC/Thousand Winds.full.jpg");
        copyUrlToImage.put("ptc/123.jpg", "PTC.zip/PTC/Trail of Mystery.full.jpg");
        copyUrlToImage.put("ptc/124.jpg", "PTC.zip/PTC/Trap Essence.full.jpg");
        copyUrlToImage.put("ptc/125.jpg", "PTC.zip/PTC/Utter End.full.jpg");
        copyUrlToImage.put("ptc/126.jpg", "PTC.zip/PTC/Villainous Wealth.full.jpg");
        copyUrlToImage.put("ptc/127.jpg", "PTC.zip/PTC/Zurgo Helmsmasher.full.jpg");
        copyUrlToImage.put("ptc/128.jpg", "PTC.zip/PTC/Alesha, Who Smiles at Death.full.jpg");
        copyUrlToImage.put("ptc/129.jpg", "PTC.zip/PTC/Arcbond.full.jpg");
        copyUrlToImage.put("ptc/13.jpg", "PTC.zip/PTC/Questing Phelddagrif.full.jpg");
        copyUrlToImage.put("ptc/130.jpg", "PTC.zip/PTC/Archfiend of Depravity.full.jpg");
        copyUrlToImage.put("ptc/131.jpg", "PTC.zip/PTC/Atarka, World Render.full.jpg");
        copyUrlToImage.put("ptc/132.jpg", "PTC.zip/PTC/Brutal Hordechief.full.jpg");
        copyUrlToImage.put("ptc/133.jpg", "PTC.zip/PTC/Daghatar the Adamant.full.jpg");
        copyUrlToImage.put("ptc/134.jpg", "PTC.zip/PTC/Dragonscale General.full.jpg");
        copyUrlToImage.put("ptc/135.jpg", "PTC.zip/PTC/Dromoka, the Eternal.full.jpg");
        copyUrlToImage.put("ptc/136.jpg", "PTC.zip/PTC/Flamerush Rider.full.jpg");
        copyUrlToImage.put("ptc/137.jpg", "PTC.zip/PTC/Flamewake Phoenix.full.jpg");
        copyUrlToImage.put("ptc/138.jpg", "PTC.zip/PTC/Jeskai Infiltrator.full.jpg");
        copyUrlToImage.put("ptc/139.jpg", "PTC.zip/PTC/Kolaghan, the Storm's Fury.full.jpg");
        copyUrlToImage.put("ptc/14.jpg", "PTC.zip/PTC/Fungal Shambler.full.jpg");
        copyUrlToImage.put("ptc/140.jpg", "PTC.zip/PTC/Mardu Strike Leader.full.jpg");
        copyUrlToImage.put("ptc/141.jpg", "PTC.zip/PTC/Mastery of the Unseen.full.jpg");
        copyUrlToImage.put("ptc/142.jpg", "PTC.zip/PTC/Ojutai, Soul of Winter.full.jpg");
        copyUrlToImage.put("ptc/143.jpg", "PTC.zip/PTC/Rally the Ancestors.full.jpg");
        copyUrlToImage.put("ptc/144.jpg", "PTC.zip/PTC/Sage-Eye Avengers.full.jpg");
        copyUrlToImage.put("ptc/145.jpg", "PTC.zip/PTC/Sandsteppe Mastodon.full.jpg");
        copyUrlToImage.put("ptc/146.jpg", "PTC.zip/PTC/Shaman of the Great Hunt.full.jpg");
        copyUrlToImage.put("ptc/147.jpg", "PTC.zip/PTC/Shamanic Revelation.full.jpg");
        copyUrlToImage.put("ptc/148.jpg", "PTC.zip/PTC/Shu Yun, the Silent Tempest.full.jpg");
        copyUrlToImage.put("ptc/149.jpg", "PTC.zip/PTC/Silumgar, the Drifting Death.full.jpg");
        copyUrlToImage.put("ptc/15.jpg", "PTC.zip/PTC/Stone-Tongue Basilisk.full.jpg");
        copyUrlToImage.put("ptc/150.jpg", "PTC.zip/PTC/Soulfire Grand Master.full.jpg");
        copyUrlToImage.put("ptc/151.jpg", "PTC.zip/PTC/Soulflayer.full.jpg");
        copyUrlToImage.put("ptc/152.jpg", "PTC.zip/PTC/Supplant Form.full.jpg");
        copyUrlToImage.put("ptc/153.jpg", "PTC.zip/PTC/Tasigur, the Golden Fang.full.jpg");
        copyUrlToImage.put("ptc/154.jpg", "PTC.zip/PTC/Torrent Elemental.full.jpg");
        copyUrlToImage.put("ptc/155.jpg", "PTC.zip/PTC/Warden of the First Tree.full.jpg");
        copyUrlToImage.put("ptc/156.jpg", "PTC.zip/PTC/Wildcall.full.jpg");
        copyUrlToImage.put("ptc/157.jpg", "PTC.zip/PTC/Yasova Dragonclaw.full.jpg");
        copyUrlToImage.put("ptc/158.jpg", "PTC.zip/PTC/Anafenza, Kin-Tree Spirit.full.jpg");
        copyUrlToImage.put("ptc/159.jpg", "PTC.zip/PTC/Arashin Foremost.full.jpg");
        copyUrlToImage.put("ptc/16.jpg", "PTC.zip/PTC/Laquatus's Champion.full.jpg");
        copyUrlToImage.put("ptc/160.jpg", "PTC.zip/PTC/Arashin Sovereign.full.jpg");
        copyUrlToImage.put("ptc/161.jpg", "PTC.zip/PTC/Atarka's Command.full.jpg");
        copyUrlToImage.put("ptc/162.jpg", "PTC.zip/PTC/Avatar of the Resolute.full.jpg");
        copyUrlToImage.put("ptc/163.jpg", "PTC.zip/PTC/Blessed Reincarnation.full.jpg");
        copyUrlToImage.put("ptc/164.jpg", "PTC.zip/PTC/Blood-Chin Fanatic.full.jpg");
        copyUrlToImage.put("ptc/165.jpg", "PTC.zip/PTC/Boltwing Marauder.full.jpg");
        copyUrlToImage.put("ptc/166.jpg", "PTC.zip/PTC/Crater Elemental.full.jpg");
        copyUrlToImage.put("ptc/167.jpg", "PTC.zip/PTC/Damnable Pact.full.jpg");
        copyUrlToImage.put("ptc/168.jpg", "PTC.zip/PTC/Deathbringer Regent.full.jpg");
        copyUrlToImage.put("ptc/169.jpg", "PTC.zip/PTC/Den Protector.full.jpg");
        copyUrlToImage.put("ptc/17.jpg", "PTC.zip/PTC/Glory.full.jpg");
        copyUrlToImage.put("ptc/170.jpg", "PTC.zip/PTC/Dragonlord Atarka.full.jpg");
        copyUrlToImage.put("ptc/171.jpg", "PTC.zip/PTC/Dragonlord Dromoka.full.jpg");
        copyUrlToImage.put("ptc/172.jpg", "PTC.zip/PTC/Dragonlord Kolaghan.full.jpg");
        copyUrlToImage.put("ptc/173.jpg", "PTC.zip/PTC/Dragonlord Ojutai.full.jpg");
        copyUrlToImage.put("ptc/174.jpg", "PTC.zip/PTC/Dragonlord Silumgar.full.jpg");
        copyUrlToImage.put("ptc/175.jpg", "PTC.zip/PTC/Dromoka's Command.full.jpg");
        copyUrlToImage.put("ptc/176.jpg", "PTC.zip/PTC/Foe-Razer Regent.full.jpg");
        copyUrlToImage.put("ptc/177.jpg", "PTC.zip/PTC/Harbinger of the Hunt.full.jpg");
        copyUrlToImage.put("ptc/178.jpg", "PTC.zip/PTC/Hidden Dragonslayer.full.jpg");
        copyUrlToImage.put("ptc/179.jpg", "PTC.zip/PTC/Icefall Regent.full.jpg");
        copyUrlToImage.put("ptc/18.jpg", "PTC.zip/PTC/Silent Specter.full.jpg");
        copyUrlToImage.put("ptc/180.jpg", "PTC.zip/PTC/Ire Shaman.full.jpg");
        copyUrlToImage.put("ptc/181.jpg", "PTC.zip/PTC/Kolaghan's Command.full.jpg");
        copyUrlToImage.put("ptc/182.jpg", "PTC.zip/PTC/Living Lore.full.jpg");
        copyUrlToImage.put("ptc/183.jpg", "PTC.zip/PTC/Myth Realized.full.jpg");
        copyUrlToImage.put("ptc/184.jpg", "PTC.zip/PTC/Necromaster Dragon.full.jpg");
        copyUrlToImage.put("ptc/185.jpg", "PTC.zip/PTC/Ojutai's Command.full.jpg");
        copyUrlToImage.put("ptc/186.jpg", "PTC.zip/PTC/Pitiless Horde.full.jpg");
        copyUrlToImage.put("ptc/187.jpg", "PTC.zip/PTC/Pristine Skywise.full.jpg");
        copyUrlToImage.put("ptc/188.jpg", "PTC.zip/PTC/Profaner of the Dead.full.jpg");
        copyUrlToImage.put("ptc/189.jpg", "PTC.zip/PTC/Sidisi, Undead Vizier.full.jpg");
        copyUrlToImage.put("ptc/19.jpg", "PTC.zip/PTC/Feral Throwback.full.jpg");
        copyUrlToImage.put("ptc/190.jpg", "PTC.zip/PTC/Silumgar Assassin.full.jpg");
        copyUrlToImage.put("ptc/191.jpg", "PTC.zip/PTC/Silumgar's Command.full.jpg");
        copyUrlToImage.put("ptc/192.jpg", "PTC.zip/PTC/Stratus Dancer.full.jpg");
        copyUrlToImage.put("ptc/193.jpg", "PTC.zip/PTC/Sunscorch Regent.full.jpg");
        copyUrlToImage.put("ptc/194.jpg", "PTC.zip/PTC/Surrak, the Hunt Caller.full.jpg");
        copyUrlToImage.put("ptc/195.jpg", "PTC.zip/PTC/Thunderbreak Regent.full.jpg");
        copyUrlToImage.put("ptc/196.jpg", "PTC.zip/PTC/Volcanic Vision.full.jpg");
        copyUrlToImage.put("ptc/197.jpg", "PTC.zip/PTC/Zurgo Bellstriker.full.jpg");
        copyUrlToImage.put("ptc/198.jpg", "PTC.zip/PTC/Abbot of Keral Keep.full.jpg");
        copyUrlToImage.put("ptc/199.jpg", "PTC.zip/PTC/Alhammarret, High Arbiter.full.jpg");
        copyUrlToImage.put("ptc/2.jpg", "PTC.zip/PTC/Revenant.full.jpg");
        copyUrlToImage.put("ptc/20.jpg", "PTC.zip/PTC/Soul Collector.full.jpg");
        copyUrlToImage.put("ptc/200.jpg", "PTC.zip/PTC/Chandra's Ignition.full.jpg");
        copyUrlToImage.put("ptc/201.jpg", "PTC.zip/PTC/Chandra, Fire of Kaladesh.full.jpg");
        copyUrlToImage.put("ptc/202.jpg", "PTC.zip/PTC/Dark Petition.full.jpg");
        copyUrlToImage.put("ptc/203.jpg", "PTC.zip/PTC/Despoiler of Souls.full.jpg");
        copyUrlToImage.put("ptc/204.jpg", "PTC.zip/PTC/Dwynen, Gilt-Leaf Daen.full.jpg");
        copyUrlToImage.put("ptc/205.jpg", "PTC.zip/PTC/Embermaw Hellion.full.jpg");
        copyUrlToImage.put("ptc/206.jpg", "PTC.zip/PTC/Evolutionary Leap.full.jpg");
        copyUrlToImage.put("ptc/207.jpg", "PTC.zip/PTC/Exquisite Firecraft.full.jpg");
        copyUrlToImage.put("ptc/208.jpg", "PTC.zip/PTC/Gaea's Revenge.full.jpg");
        copyUrlToImage.put("ptc/209.jpg", "PTC.zip/PTC/Gideon's Phalanx.full.jpg");
        copyUrlToImage.put("ptc/21.jpg", "PTC.zip/PTC/Sword of Kaldra.full.jpg");
        copyUrlToImage.put("ptc/210.jpg", "PTC.zip/PTC/Gilt-Leaf Winnower.full.jpg");
        copyUrlToImage.put("ptc/211.jpg", "PTC.zip/PTC/Goblin Piledriver.full.jpg");
        copyUrlToImage.put("ptc/212.jpg", "PTC.zip/PTC/Graveblade Marauder.full.jpg");
        copyUrlToImage.put("ptc/213.jpg", "PTC.zip/PTC/Harbinger of the Tides.full.jpg");
        copyUrlToImage.put("ptc/214.jpg", "PTC.zip/PTC/Hixus, Prison Warden.full.jpg");
        copyUrlToImage.put("ptc/215.jpg", "PTC.zip/PTC/Honored Hierarch.full.jpg");
        copyUrlToImage.put("ptc/216.jpg", "PTC.zip/PTC/Jace, Vryn's Prodigy.full.jpg");
        copyUrlToImage.put("ptc/217.jpg", "PTC.zip/PTC/Knight of the White Orchid.full.jpg");
        copyUrlToImage.put("ptc/218.jpg", "PTC.zip/PTC/Kothophed, Soul Hoarder.full.jpg");
        copyUrlToImage.put("ptc/219.jpg", "PTC.zip/PTC/Kytheon's Irregulars.full.jpg");
        copyUrlToImage.put("ptc/22.jpg", "PTC.zip/PTC/Shield of Kaldra.full.jpg");
        copyUrlToImage.put("ptc/220.jpg", "PTC.zip/PTC/Kytheon, Hero of Akros.full.jpg");
        copyUrlToImage.put("ptc/221.jpg", "PTC.zip/PTC/Languish.full.jpg");
        copyUrlToImage.put("ptc/222.jpg", "PTC.zip/PTC/Liliana, Heretical Healer.full.jpg");
        copyUrlToImage.put("ptc/223.jpg", "PTC.zip/PTC/Managorger Hydra.full.jpg");
        copyUrlToImage.put("ptc/224.jpg", "PTC.zip/PTC/Mizzium Meddler.full.jpg");
        copyUrlToImage.put("ptc/225.jpg", "PTC.zip/PTC/Nissa's Revelation.full.jpg");
        copyUrlToImage.put("ptc/226.jpg", "PTC.zip/PTC/Nissa, Vastwood Seer.full.jpg");
        copyUrlToImage.put("ptc/227.jpg", "PTC.zip/PTC/Outland Colossus.full.jpg");
        copyUrlToImage.put("ptc/228.jpg", "PTC.zip/PTC/Pia and Kiran Nalaar.full.jpg");
        copyUrlToImage.put("ptc/229.jpg", "PTC.zip/PTC/Priest of the Blood Rite.full.jpg");
        copyUrlToImage.put("ptc/23.jpg", "PTC.zip/PTC/Helm of Kaldra.full.jpg");
        copyUrlToImage.put("ptc/230.jpg", "PTC.zip/PTC/Relic Seeker.full.jpg");
        copyUrlToImage.put("ptc/231.jpg", "PTC.zip/PTC/Scab-Clan Berserker.full.jpg");
        copyUrlToImage.put("ptc/232.jpg", "PTC.zip/PTC/Soulblade Djinn.full.jpg");
        copyUrlToImage.put("ptc/233.jpg", "PTC.zip/PTC/Talent of the Telepath.full.jpg");
        copyUrlToImage.put("ptc/234.jpg", "PTC.zip/PTC/Thopter Spy Network.full.jpg");
        copyUrlToImage.put("ptc/235.jpg", "PTC.zip/PTC/Tragic Arrogance.full.jpg");
        copyUrlToImage.put("ptc/236.jpg", "PTC.zip/PTC/Vryn Wingmare.full.jpg");
        copyUrlToImage.put("ptc/237.jpg", "PTC.zip/PTC/Willbreaker.full.jpg");
        copyUrlToImage.put("ptc/238.jpg", "PTC.zip/PTC/Akoum Firebird.full.jpg");
        copyUrlToImage.put("ptc/239.jpg", "PTC.zip/PTC/Akoum Hellkite.full.jpg");
        copyUrlToImage.put("ptc/24.jpg", "PTC.zip/PTC/Ryusei, the Falling Star.full.jpg");
        copyUrlToImage.put("ptc/240.jpg", "PTC.zip/PTC/Aligned Hedron Network.full.jpg");
        copyUrlToImage.put("ptc/241.jpg", "PTC.zip/PTC/Ally Encampment.full.jpg");
        copyUrlToImage.put("ptc/242.jpg", "PTC.zip/PTC/Angelic Captain.full.jpg");
        copyUrlToImage.put("ptc/243.jpg", "PTC.zip/PTC/Barrage Tyrant.full.jpg");
        copyUrlToImage.put("ptc/244.jpg", "PTC.zip/PTC/Beastcaller Savant.full.jpg");
        copyUrlToImage.put("ptc/245.jpg", "PTC.zip/PTC/Blight Herder.full.jpg");
        copyUrlToImage.put("ptc/246.jpg", "PTC.zip/PTC/Bring to Light.full.jpg");
        copyUrlToImage.put("ptc/247.jpg", "PTC.zip/PTC/Brood Butcher.full.jpg");
        copyUrlToImage.put("ptc/248.jpg", "PTC.zip/PTC/Brutal Expulsion.full.jpg");
        copyUrlToImage.put("ptc/249.jpg", "PTC.zip/PTC/Canopy Vista.full.jpg");
        copyUrlToImage.put("ptc/25.jpg", "PTC.zip/PTC/Ink-Eyes, Servant of Oni.full.jpg");
        copyUrlToImage.put("ptc/250.jpg", "PTC.zip/PTC/Cinder Glade.full.jpg");
        copyUrlToImage.put("ptc/251.jpg", "PTC.zip/PTC/Conduit of Ruin.full.jpg");
        copyUrlToImage.put("ptc/252.jpg", "PTC.zip/PTC/Defiant Bloodlord.full.jpg");
        copyUrlToImage.put("ptc/253.jpg", "PTC.zip/PTC/Desolation Twin.full.jpg");
        copyUrlToImage.put("ptc/254.jpg", "PTC.zip/PTC/Dragonmaster Outcast.full.jpg");
        copyUrlToImage.put("ptc/255.jpg", "PTC.zip/PTC/Drana, Liberator of Malakir.full.jpg");
        copyUrlToImage.put("ptc/256.jpg", "PTC.zip/PTC/Drowner of Hope.full.jpg");
        copyUrlToImage.put("ptc/257.jpg", "PTC.zip/PTC/Dust Stalker.full.jpg");
        copyUrlToImage.put("ptc/258.jpg", "PTC.zip/PTC/Emeria Shepherd.full.jpg");
        copyUrlToImage.put("ptc/259.jpg", "PTC.zip/PTC/Endless One.full.jpg");
        copyUrlToImage.put("ptc/26.jpg", "PTC.zip/PTC/Kiyomaro, First to Stand.full.jpg");
        copyUrlToImage.put("ptc/260.jpg", "PTC.zip/PTC/Exert Influence.full.jpg");
        copyUrlToImage.put("ptc/261.jpg", "PTC.zip/PTC/Fathom Feeder.full.jpg");
        copyUrlToImage.put("ptc/262.jpg", "PTC.zip/PTC/Felidar Sovereign.full.jpg");
        copyUrlToImage.put("ptc/263.jpg", "PTC.zip/PTC/From Beyond.full.jpg");
        copyUrlToImage.put("ptc/264.jpg", "PTC.zip/PTC/Gideon, Ally of Zendikar.full.jpg");
        copyUrlToImage.put("ptc/265.jpg", "PTC.zip/PTC/Greenwarden of Murasa.full.jpg");
        copyUrlToImage.put("ptc/266.jpg", "PTC.zip/PTC/Gruesome Slaughter.full.jpg");
        copyUrlToImage.put("ptc/267.jpg", "PTC.zip/PTC/Guardian of Tazeem.full.jpg");
        copyUrlToImage.put("ptc/268.jpg", "PTC.zip/PTC/Guul Draz Overseer.full.jpg");
        copyUrlToImage.put("ptc/269.jpg", "PTC.zip/PTC/Hero of Goma Fada.full.jpg");
        copyUrlToImage.put("ptc/27.jpg", "PTC.zip/PTC/Gleancrawler.full.jpg");
        copyUrlToImage.put("ptc/270.jpg", "PTC.zip/PTC/Kiora, Master of the Depths.full.jpg");
        copyUrlToImage.put("ptc/271.jpg", "PTC.zip/PTC/Lantern Scout.full.jpg");
        copyUrlToImage.put("ptc/272.jpg", "PTC.zip/PTC/Lumbering Falls.full.jpg");
        copyUrlToImage.put("ptc/273.jpg", "PTC.zip/PTC/March from the Tomb.full.jpg");
        copyUrlToImage.put("ptc/274.jpg", "PTC.zip/PTC/Munda, Ambush Leader.full.jpg");
        copyUrlToImage.put("ptc/275.jpg", "PTC.zip/PTC/Nissa's Renewal.full.jpg");
        copyUrlToImage.put("ptc/276.jpg", "PTC.zip/PTC/Noyan Dar, Roil Shaper.full.jpg");
        copyUrlToImage.put("ptc/277.jpg", "PTC.zip/PTC/Ob Nixilis Reignited.full.jpg");
        copyUrlToImage.put("ptc/278.jpg", "PTC.zip/PTC/Oblivion Sower.full.jpg");
        copyUrlToImage.put("ptc/279.jpg", "PTC.zip/PTC/Omnath, Locus of Rage.full.jpg");
        copyUrlToImage.put("ptc/28.jpg", "PTC.zip/PTC/Djinn Illuminatus.full.jpg");
        copyUrlToImage.put("ptc/280.jpg", "PTC.zip/PTC/Oran-Rief Hydra.full.jpg");
        copyUrlToImage.put("ptc/281.jpg", "PTC.zip/PTC/Painful Truths.full.jpg");
        copyUrlToImage.put("ptc/282.jpg", "PTC.zip/PTC/Part the Waterveil.full.jpg");
        copyUrlToImage.put("ptc/283.jpg", "PTC.zip/PTC/Planar Outburst.full.jpg");
        copyUrlToImage.put("ptc/284.jpg", "PTC.zip/PTC/Prairie Stream.full.jpg");
        copyUrlToImage.put("ptc/285.jpg", "PTC.zip/PTC/Prism Array.full.jpg");
        copyUrlToImage.put("ptc/286.jpg", "PTC.zip/PTC/Quarantine Field.full.jpg");
        copyUrlToImage.put("ptc/287.jpg", "PTC.zip/PTC/Radiant Flames.full.jpg");
        copyUrlToImage.put("ptc/288.jpg", "PTC.zip/PTC/Ruinous Path.full.jpg");
        copyUrlToImage.put("ptc/289.jpg", "PTC.zip/PTC/Sanctum of Ugin.full.jpg");
        copyUrlToImage.put("ptc/29.jpg", "PTC.zip/PTC/Avatar of Discord.full.jpg");
        copyUrlToImage.put("ptc/290.jpg", "PTC.zip/PTC/Scatter to the Winds.full.jpg");
        copyUrlToImage.put("ptc/291.jpg", "PTC.zip/PTC/Serpentine Spike.full.jpg");
        copyUrlToImage.put("ptc/292.jpg", "PTC.zip/PTC/Shambling Vent.full.jpg");
        copyUrlToImage.put("ptc/293.jpg", "PTC.zip/PTC/Shrine of the Forsaken Gods.full.jpg");
        copyUrlToImage.put("ptc/294.jpg", "PTC.zip/PTC/Sire of Stagnation.full.jpg");
        copyUrlToImage.put("ptc/295.jpg", "PTC.zip/PTC/Smoldering Marsh.full.jpg");
        copyUrlToImage.put("ptc/296.jpg", "PTC.zip/PTC/Smothering Abomination.full.jpg");
        copyUrlToImage.put("ptc/297.jpg", "PTC.zip/PTC/Sunken Hollow.full.jpg");
        copyUrlToImage.put("ptc/298.jpg", "PTC.zip/PTC/Ugin's Insight.full.jpg");
        copyUrlToImage.put("ptc/299.jpg", "PTC.zip/PTC/Ulamog, the Ceaseless Hunger.full.jpg");
        copyUrlToImage.put("ptc/3.jpg", "PTC.zip/PTC/Monstrous Hound.full.jpg");
        copyUrlToImage.put("ptc/30.jpg", "PTC.zip/PTC/Allosaurus Rider.full.jpg");
        copyUrlToImage.put("ptc/300.jpg", "PTC.zip/PTC/Undergrowth Champion.full.jpg");
        copyUrlToImage.put("ptc/301.jpg", "PTC.zip/PTC/Veteran Warleader.full.jpg");
        copyUrlToImage.put("ptc/302.jpg", "PTC.zip/PTC/Void Winnower.full.jpg");
        copyUrlToImage.put("ptc/303.jpg", "PTC.zip/PTC/Wasteland Strangler.full.jpg");
        copyUrlToImage.put("ptc/304.jpg", "PTC.zip/PTC/Woodland Wanderer.full.jpg");
        copyUrlToImage.put("ptc/305.jpg", "PTC.zip/PTC/Zada, Hedron Grinder.full.jpg");
        copyUrlToImage.put("ptc/31.jpg", "PTC.zip/PTC/Lotus Bloom.full.jpg");
        copyUrlToImage.put("ptc/32.jpg", "PTC.zip/PTC/Oros, the Avenger.full.jpg");
        copyUrlToImage.put("ptc/33.jpg", "PTC.zip/PTC/Korlash, Heir to Blackblade.full.jpg");
        copyUrlToImage.put("ptc/34.jpg", "PTC.zip/PTC/Wren's Run Packmaster.full.jpg");
        copyUrlToImage.put("ptc/35.jpg", "PTC.zip/PTC/Door of Destinies.full.jpg");
        copyUrlToImage.put("ptc/36.jpg", "PTC.zip/PTC/Demigod of Revenge.full.jpg");
        copyUrlToImage.put("ptc/37.jpg", "PTC.zip/PTC/Overbeing of Myth.full.jpg");
        copyUrlToImage.put("ptc/38.jpg", "PTC.zip/PTC/Ajani Vengeant.full.jpg");
        copyUrlToImage.put("ptc/39.jpg", "PTC.zip/PTC/Malfegor.full.jpg");
        copyUrlToImage.put("ptc/4.jpg", "PTC.zip/PTC/Lightning Dragon.full.jpg");
        copyUrlToImage.put("ptc/40.jpg", "PTC.zip/PTC/Dragon Broodmother.full.jpg");
        copyUrlToImage.put("ptc/41.jpg", "PTC.zip/PTC/Vampire Nocturnus.full.jpg");
        copyUrlToImage.put("ptc/42.jpg", "PTC.zip/PTC/Rampaging Baloths.full.jpg");
        copyUrlToImage.put("ptc/43.jpg", "PTC.zip/PTC/Comet Storm.full.jpg");
        copyUrlToImage.put("ptc/44.jpg", "PTC.zip/PTC/Emrakul, the Aeons Torn.full.jpg");
        copyUrlToImage.put("ptc/45.jpg", "PTC.zip/PTC/Sun Titan.full.jpg");
        copyUrlToImage.put("ptc/46.jpg", "PTC.zip/PTC/Wurmcoil Engine.full.jpg");
        copyUrlToImage.put("ptc/47.jpg", "PTC.zip/PTC/Hero of Bladehold.full.jpg");
        copyUrlToImage.put("ptc/48.jpg", "PTC.zip/PTC/Glissa, the Traitor.full.jpg");
        copyUrlToImage.put("ptc/49.jpg", "PTC.zip/PTC/Sheoldred, Whispering One.full.jpg");
        copyUrlToImage.put("ptc/5.jpg", "PTC.zip/PTC/Beast of Burden.full.jpg");
        copyUrlToImage.put("ptc/50.jpg", "PTC.zip/PTC/Bloodlord of Vaasgoth.full.jpg");
        copyUrlToImage.put("ptc/51a.jpg", "PTC.zip/PTC/Mayor of Avabruck.full.jpg");
        copyUrlToImage.put("ptc/51b.jpg", "PTC.zip/PTC/Howlpack Alpha.full.jpg");
        copyUrlToImage.put("ptc/52a.jpg", "PTC.zip/PTC/Ravenous Demon.full.jpg");
        copyUrlToImage.put("ptc/52b.jpg", "PTC.zip/PTC/Archdemon of Greed.full.jpg");
        copyUrlToImage.put("ptc/53.jpg", "PTC.zip/PTC/Moonsilver Spear.full.jpg");
        copyUrlToImage.put("ptc/54.jpg", "PTC.zip/PTC/Xathrid Gorgon.full.jpg");
        copyUrlToImage.put("ptc/55.jpg", "PTC.zip/PTC/Archon of the Triumvirate.full.jpg");
        copyUrlToImage.put("ptc/56.jpg", "PTC.zip/PTC/Hypersonic Dragon.full.jpg");
        copyUrlToImage.put("ptc/57.jpg", "PTC.zip/PTC/Carnival Hellsteed.full.jpg");
        copyUrlToImage.put("ptc/58.jpg", "PTC.zip/PTC/Corpsejack Menace.full.jpg");
        copyUrlToImage.put("ptc/59.jpg", "PTC.zip/PTC/Grove of the Guardian.full.jpg");
        copyUrlToImage.put("ptc/6.jpg", "PTC.zip/PTC/Lu Bu, Master-at-Arms 2.full.jpg");
        copyUrlToImage.put("ptc/60.jpg", "PTC.zip/PTC/Consuming Aberration.full.jpg");
        copyUrlToImage.put("ptc/61.jpg", "PTC.zip/PTC/Fathom Mage.full.jpg");
        copyUrlToImage.put("ptc/62.jpg", "PTC.zip/PTC/Foundry Champion.full.jpg");
        copyUrlToImage.put("ptc/63.jpg", "PTC.zip/PTC/Rubblehulk.full.jpg");
        copyUrlToImage.put("ptc/64.jpg", "PTC.zip/PTC/Treasury Thrull.full.jpg");
        copyUrlToImage.put("ptc/65.jpg", "PTC.zip/PTC/Maze's End.full.jpg");
        copyUrlToImage.put("ptc/66.jpg", "PTC.zip/PTC/Plains.66.full.jpg");
        copyUrlToImage.put("ptc/67.jpg", "PTC.zip/PTC/Megantic Sliver.full.jpg");
        copyUrlToImage.put("ptc/68.jpg", "PTC.zip/PTC/Celestial Archon.full.jpg");
        copyUrlToImage.put("ptc/69.jpg", "PTC.zip/PTC/Shipbreaker Kraken.full.jpg");
        copyUrlToImage.put("ptc/7.jpg", "PTC.zip/PTC/False Prophet.full.jpg");
        copyUrlToImage.put("ptc/70.jpg", "PTC.zip/PTC/Abhorrent Overlord.full.jpg");
        copyUrlToImage.put("ptc/71.jpg", "PTC.zip/PTC/Ember Swallower.full.jpg");
        copyUrlToImage.put("ptc/72.jpg", "PTC.zip/PTC/Anthousa, Setessan Hero.full.jpg");
        copyUrlToImage.put("ptc/73.jpg", "PTC.zip/PTC/Silent Sentinel.full.jpg");
        copyUrlToImage.put("ptc/74.jpg", "PTC.zip/PTC/Arbiter of the Ideal.full.jpg");
        copyUrlToImage.put("ptc/75.jpg", "PTC.zip/PTC/Eater of Hope.full.jpg");
        copyUrlToImage.put("ptc/76.jpg", "PTC.zip/PTC/Forgestoker Dragon.full.jpg");
        copyUrlToImage.put("ptc/77.jpg", "PTC.zip/PTC/Nessian Wilds Ravager.full.jpg");
        copyUrlToImage.put("ptc/78.jpg", "PTC.zip/PTC/Dawnbringer Charioteers.full.jpg");
        copyUrlToImage.put("ptc/79.jpg", "PTC.zip/PTC/Scourge of Fleets.full.jpg");
        copyUrlToImage.put("ptc/8.jpg", "PTC.zip/PTC/Lu Bu, Master-at-Arms 1.full.jpg");
        copyUrlToImage.put("ptc/80.jpg", "PTC.zip/PTC/Doomwake Giant.full.jpg");
        copyUrlToImage.put("ptc/81.jpg", "PTC.zip/PTC/Spawn of Thraxes.full.jpg");
        copyUrlToImage.put("ptc/82.jpg", "PTC.zip/PTC/Heroes' Bane.full.jpg");
        copyUrlToImage.put("ptc/83.jpg", "PTC.zip/PTC/Resolute Archangel.full.jpg");
        copyUrlToImage.put("ptc/84.jpg", "PTC.zip/PTC/Mercurial Pretender.full.jpg");
        copyUrlToImage.put("ptc/85.jpg", "PTC.zip/PTC/Indulgent Tormentor.full.jpg");
        copyUrlToImage.put("ptc/86.jpg", "PTC.zip/PTC/Siege Dragon.full.jpg");
        copyUrlToImage.put("ptc/87.jpg", "PTC.zip/PTC/Phytotitan.full.jpg");
        copyUrlToImage.put("ptc/88.jpg", "PTC.zip/PTC/Abzan Ascendancy.full.jpg");
        copyUrlToImage.put("ptc/89.jpg", "PTC.zip/PTC/Anafenza, the Foremost.full.jpg");
        copyUrlToImage.put("ptc/9.jpg", "PTC.zip/PTC/Overtaker.full.jpg");
        copyUrlToImage.put("ptc/90.jpg", "PTC.zip/PTC/Ankle Shanker.full.jpg");
        copyUrlToImage.put("ptc/91.jpg", "PTC.zip/PTC/Avalanche Tusker.full.jpg");
        copyUrlToImage.put("ptc/92.jpg", "PTC.zip/PTC/Bloodsoaked Champion.full.jpg");
        copyUrlToImage.put("ptc/93.jpg", "PTC.zip/PTC/Butcher of the Horde.full.jpg");
        copyUrlToImage.put("ptc/94.jpg", "PTC.zip/PTC/Crackling Doom.full.jpg");
        copyUrlToImage.put("ptc/95.jpg", "PTC.zip/PTC/Crater's Claws.full.jpg");
        copyUrlToImage.put("ptc/96.jpg", "PTC.zip/PTC/Deflecting Palm.full.jpg");
        copyUrlToImage.put("ptc/97.jpg", "PTC.zip/PTC/Dig Through Time.full.jpg");
        copyUrlToImage.put("ptc/98.jpg", "PTC.zip/PTC/Dragon-Style Twins.full.jpg");
        copyUrlToImage.put("ptc/99.jpg", "PTC.zip/PTC/Duneblast.full.jpg");
        copyUrlToImage.put("ugin/1.jpg", "UGIN.zip/UGIN/Ugin, the Spirit Dragon.full.jpg");
        copyUrlToImage.put("ugin/113.jpg", "UGIN.zip/UGIN/Jeering Instigator.full.jpg");
        copyUrlToImage.put("ugin/123.jpg", "UGIN.zip/UGIN/Arashin War Beast.full.jpg");
        copyUrlToImage.put("ugin/129.jpg", "UGIN.zip/UGIN/Formless Nurturing.full.jpg");
        copyUrlToImage.put("ugin/131.jpg", "UGIN.zip/UGIN/Dragonscale Boon.full.jpg");
        copyUrlToImage.put("ugin/146.jpg", "UGIN.zip/UGIN/Wildcall.full.jpg");
        copyUrlToImage.put("ugin/161.jpg", "UGIN.zip/UGIN/Hewed Stone Retainers.full.jpg");
        copyUrlToImage.put("ugin/164.jpg", "UGIN.zip/UGIN/Ugin's Construct.full.jpg");
        copyUrlToImage.put("ugin/19.jpg", "UGIN.zip/UGIN/Mastery of the Unseen.full.jpg");
        copyUrlToImage.put("ugin/216.jpg", "UGIN.zip/UGIN/Altar of the Brood.full.jpg");
        copyUrlToImage.put("ugin/217.jpg", "UGIN.zip/UGIN/Briber's Purse.full.jpg");
        copyUrlToImage.put("ugin/220.jpg", "UGIN.zip/UGIN/Ghostfire Blade.full.jpg");
        copyUrlToImage.put("ugin/24.jpg", "UGIN.zip/UGIN/Smite the Monstrous.full.jpg");
        copyUrlToImage.put("ugin/26.jpg", "UGIN.zip/UGIN/Soul Summons.full.jpg");
        copyUrlToImage.put("ugin/30.jpg", "UGIN.zip/UGIN/Watcher of the Roost.full.jpg");
        copyUrlToImage.put("ugin/36.jpg", "UGIN.zip/UGIN/Jeskai Infiltrator.full.jpg");
        copyUrlToImage.put("ugin/46.jpg", "UGIN.zip/UGIN/Reality Shift.full.jpg");
        copyUrlToImage.put("ugin/48.jpg", "UGIN.zip/UGIN/Mystic of the Hidden Way.full.jpg");
        copyUrlToImage.put("ugin/59.jpg", "UGIN.zip/UGIN/Write into Being.full.jpg");
        copyUrlToImage.put("ugin/68.jpg", "UGIN.zip/UGIN/Debilitating Injury.full.jpg");
        copyUrlToImage.put("ugin/73.jpg", "UGIN.zip/UGIN/Grim Haruspex.full.jpg");
        copyUrlToImage.put("ugin/85.jpg", "UGIN.zip/UGIN/Sultai Emissary.full.jpg");
        copyUrlToImage.put("ugin/88.jpg", "UGIN.zip/UGIN/Ruthless Ripper.full.jpg");
        copyUrlToImage.put("ugin/96.jpg", "UGIN.zip/UGIN/Ainok Tracker.full.jpg");
        copyUrlToImage.put("ugin/97.jpg", "UGIN.zip/UGIN/Arc Lightning.full.jpg");
        copyUrlToImage.put("ugin/98.jpg", "UGIN.zip/UGIN/Fierce Invocation.full.jpg");
        copyUrlToImage.put("wmcq/1.jpg", "WMCQ.zip/WMCQ/Vengevine.full.jpg");
        copyUrlToImage.put("wmcq/2.jpg", "WMCQ.zip/WMCQ/Geist of Saint Traft.full.jpg");
        copyUrlToImage.put("wmcq/3.jpg", "WMCQ.zip/WMCQ/Thalia, Guardian of Thraben.full.jpg");
        copyUrlToImage.put("wmcq/4.jpg", "WMCQ.zip/WMCQ/Liliana of the Veil.full.jpg");
        copyUrlToImage.put("wmcq/5.jpg", "WMCQ.zip/WMCQ/Snapcaster Mage.full.jpg");


        for (String key : copyUrlToImage.keySet()) {
            copyUrlToImageDone.put(key, maxTimes);
            copyImageToUrl.put(copyUrlToImage.get(key), key);
        }
    }

    @Override
    public String generateTokenUrl(CardDownloadData card) throws IOException {
        return null;
    }

    @Override
    public Integer getTotalImages() {
        if (copyUrlToImage == null) {
            setupLinks();
        }
        if (copyUrlToImage != null) {
            return copyImageToUrl.size();
        }
        return -1;
    }
    
    @Override
    public Boolean isTokenSource() {
        return false;
    }
}
