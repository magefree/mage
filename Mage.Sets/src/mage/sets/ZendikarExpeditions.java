/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author fireshoes
 */
public class ZendikarExpeditions extends ExpansionSet {

    private static final ZendikarExpeditions instance = new ZendikarExpeditions();

    public static ZendikarExpeditions getInstance() {
        return instance;
    }

    private ZendikarExpeditions() {
        super("Zendikar Expeditions", "EXP", ExpansionSet.buildDate(2015, 10, 2), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;
        cards.add(new SetCardInfo("Ancient Tomb", 36, Rarity.MYTHIC, mage.cards.a.AncientTomb.class));
        cards.add(new SetCardInfo("Arid Mesa", 24, Rarity.MYTHIC, mage.cards.a.AridMesa.class));
        cards.add(new SetCardInfo("Blood Crypt", 8, Rarity.MYTHIC, mage.cards.b.BloodCrypt.class));
        cards.add(new SetCardInfo("Bloodstained Mire", 18, Rarity.MYTHIC, mage.cards.b.BloodstainedMire.class));
        cards.add(new SetCardInfo("Breeding Pool", 15, Rarity.MYTHIC, mage.cards.b.BreedingPool.class));
        cards.add(new SetCardInfo("Canopy Vista", 5, Rarity.MYTHIC, mage.cards.c.CanopyVista.class));
        cards.add(new SetCardInfo("Cascade Bluffs", 32, Rarity.MYTHIC, mage.cards.c.CascadeBluffs.class));
        cards.add(new SetCardInfo("Cinder Glade", 4, Rarity.MYTHIC, mage.cards.c.CinderGlade.class));
        cards.add(new SetCardInfo("Dust Bowl", 37, Rarity.MYTHIC, mage.cards.d.DustBowl.class));
        cards.add(new SetCardInfo("Eye of Ugin", 38, Rarity.MYTHIC, mage.cards.e.EyeOfUgin.class));
        cards.add(new SetCardInfo("Fetid Heath", 31, Rarity.MYTHIC, mage.cards.f.FetidHeath.class));
        cards.add(new SetCardInfo("Fire-Lit Thicket", 29, Rarity.MYTHIC, mage.cards.f.FireLitThicket.class));
        cards.add(new SetCardInfo("Flooded Grove", 35, Rarity.MYTHIC, mage.cards.f.FloodedGrove.class));
        cards.add(new SetCardInfo("Flooded Strand", 16, Rarity.MYTHIC, mage.cards.f.FloodedStrand.class));
        cards.add(new SetCardInfo("Forbidden Orchard", 39, Rarity.MYTHIC, mage.cards.f.ForbiddenOrchard.class));
        cards.add(new SetCardInfo("Godless Shrine", 11, Rarity.MYTHIC, mage.cards.g.GodlessShrine.class));
        cards.add(new SetCardInfo("Graven Cairns", 28, Rarity.MYTHIC, mage.cards.g.GravenCairns.class));
        cards.add(new SetCardInfo("Hallowed Fountain", 6, Rarity.MYTHIC, mage.cards.h.HallowedFountain.class));
        cards.add(new SetCardInfo("Horizon Canopy", 40, Rarity.MYTHIC, mage.cards.h.HorizonCanopy.class));
        cards.add(new SetCardInfo("Kor Haven", 41, Rarity.MYTHIC, mage.cards.k.KorHaven.class));
        cards.add(new SetCardInfo("Mana Confluence", 42, Rarity.MYTHIC, mage.cards.m.ManaConfluence.class));
        cards.add(new SetCardInfo("Marsh Flats", 21, Rarity.MYTHIC, mage.cards.m.MarshFlats.class));
        cards.add(new SetCardInfo("Misty Rainforest", 25, Rarity.MYTHIC, mage.cards.m.MistyRainforest.class));
        cards.add(new SetCardInfo("Mystic Gate", 26, Rarity.MYTHIC, mage.cards.m.MysticGate.class));
        cards.add(new SetCardInfo("Overgrown Tomb", 13, Rarity.MYTHIC, mage.cards.o.OvergrownTomb.class));
        cards.add(new SetCardInfo("Polluted Delta", 17, Rarity.MYTHIC, mage.cards.p.PollutedDelta.class));
        cards.add(new SetCardInfo("Prairie Stream", 1, Rarity.MYTHIC, mage.cards.p.PrairieStream.class));
        cards.add(new SetCardInfo("Rugged Prairie", 34, Rarity.MYTHIC, mage.cards.r.RuggedPrairie.class));
        cards.add(new SetCardInfo("Sacred Foundry", 14, Rarity.MYTHIC, mage.cards.s.SacredFoundry.class));
        cards.add(new SetCardInfo("Scalding Tarn", 22, Rarity.MYTHIC, mage.cards.s.ScaldingTarn.class));
        cards.add(new SetCardInfo("Smoldering Marsh", 3, Rarity.MYTHIC, mage.cards.s.SmolderingMarsh.class));
        cards.add(new SetCardInfo("Steam Vents", 12, Rarity.MYTHIC, mage.cards.s.SteamVents.class));
        cards.add(new SetCardInfo("Stomping Ground", 9, Rarity.MYTHIC, mage.cards.s.StompingGround.class));
        cards.add(new SetCardInfo("Strip Mine", 43, Rarity.MYTHIC, mage.cards.s.StripMine.class));
        cards.add(new SetCardInfo("Sunken Hollow", 2, Rarity.MYTHIC, mage.cards.s.SunkenHollow.class));
        cards.add(new SetCardInfo("Sunken Ruins", 27, Rarity.MYTHIC, mage.cards.s.SunkenRuins.class));
        cards.add(new SetCardInfo("Tectonic Edge", 44, Rarity.MYTHIC, mage.cards.t.TectonicEdge.class));
        cards.add(new SetCardInfo("Temple Garden", 10, Rarity.MYTHIC, mage.cards.t.TempleGarden.class));
        cards.add(new SetCardInfo("Twilight Mire", 33, Rarity.MYTHIC, mage.cards.t.TwilightMire.class));
        cards.add(new SetCardInfo("Verdant Catacombs", 23, Rarity.MYTHIC, mage.cards.v.VerdantCatacombs.class));
        cards.add(new SetCardInfo("Wasteland", 45, Rarity.MYTHIC, mage.cards.w.Wasteland.class));
        cards.add(new SetCardInfo("Watery Grave", 7, Rarity.MYTHIC, mage.cards.w.WateryGrave.class));
        cards.add(new SetCardInfo("Windswept Heath", 20, Rarity.MYTHIC, mage.cards.w.WindsweptHeath.class));
        cards.add(new SetCardInfo("Wooded Bastion", 30, Rarity.MYTHIC, mage.cards.w.WoodedBastion.class));
        cards.add(new SetCardInfo("Wooded Foothills", 19, Rarity.MYTHIC, mage.cards.w.WoodedFoothills.class));
    }
}
