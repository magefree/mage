package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pogw
 */
public class OathOfTheGatewatchPromos extends ExpansionSet {

    private static final OathOfTheGatewatchPromos instance = new OathOfTheGatewatchPromos();

    public static OathOfTheGatewatchPromos getInstance() {
        return instance;
    }

    private OathOfTheGatewatchPromos() {
        super("Oath of the Gatewatch Promos", "POGW", ExpansionSet.buildDate(2016, 1, 23), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Ayli, Eternal Pilgrim", "151s", Rarity.RARE, mage.cards.a.AyliEternalPilgrim.class));
        cards.add(new SetCardInfo("Bearer of Silence", "67s", Rarity.RARE, mage.cards.b.BearerOfSilence.class));
        cards.add(new SetCardInfo("Call the Gatewatch", "16s", Rarity.RARE, mage.cards.c.CallTheGatewatch.class));
        cards.add(new SetCardInfo("Captain's Claws", "162s", Rarity.RARE, mage.cards.c.CaptainsClaws.class));
        cards.add(new SetCardInfo("Chandra, Flamecaller", "104s", Rarity.MYTHIC, mage.cards.c.ChandraFlamecaller.class));
        cards.add(new SetCardInfo("Corrupted Crossroads", "169s", Rarity.RARE, mage.cards.c.CorruptedCrossroads.class));
        cards.add(new SetCardInfo("Crush of Tentacles", "53s", Rarity.MYTHIC, mage.cards.c.CrushOfTentacles.class));
        cards.add(new SetCardInfo("Deceiver of Form", "1s", Rarity.RARE, mage.cards.d.DeceiverOfForm.class));
        cards.add(new SetCardInfo("Deepfathom Skulker", 43, Rarity.RARE, mage.cards.d.DeepfathomSkulker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Deepfathom Skulker", "43s", Rarity.RARE, mage.cards.d.DeepfathomSkulker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dimensional Infiltrator", "44s", Rarity.RARE, mage.cards.d.DimensionalInfiltrator.class));
        cards.add(new SetCardInfo("Drana's Chosen", "84s", Rarity.RARE, mage.cards.d.DranasChosen.class));
        cards.add(new SetCardInfo("Dread Defiler", 68, Rarity.RARE, mage.cards.d.DreadDefiler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dread Defiler", "68s", Rarity.RARE, mage.cards.d.DreadDefiler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Eldrazi Displacer", "13s", Rarity.RARE, mage.cards.e.EldraziDisplacer.class));
        cards.add(new SetCardInfo("Eldrazi Mimic", "2s", Rarity.RARE, mage.cards.e.EldraziMimic.class));
        cards.add(new SetCardInfo("Eldrazi Obligator", "96s", Rarity.RARE, mage.cards.e.EldraziObligator.class));
        cards.add(new SetCardInfo("Endbringer", 3, Rarity.RARE, mage.cards.e.Endbringer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Endbringer", "3s", Rarity.RARE, mage.cards.e.Endbringer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fall of the Titans", "109s", Rarity.RARE, mage.cards.f.FallOfTheTitans.class));
        cards.add(new SetCardInfo("General Tazri", "19s", Rarity.MYTHIC, mage.cards.g.GeneralTazri.class));
        cards.add(new SetCardInfo("Gladehart Cavalry", 132, Rarity.RARE, mage.cards.g.GladehartCavalry.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gladehart Cavalry", "132s", Rarity.RARE, mage.cards.g.GladehartCavalry.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Goblin Dark-Dwellers", 110, Rarity.RARE, mage.cards.g.GoblinDarkDwellers.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Goblin Dark-Dwellers", "110s", Rarity.RARE, mage.cards.g.GoblinDarkDwellers.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hedron Alignment", "57s", Rarity.RARE, mage.cards.h.HedronAlignment.class));
        cards.add(new SetCardInfo("Hissing Quagmire", "171s", Rarity.RARE, mage.cards.h.HissingQuagmire.class));
        cards.add(new SetCardInfo("Immolating Glare", 20, Rarity.UNCOMMON, mage.cards.i.ImmolatingGlare.class));
        cards.add(new SetCardInfo("Inverter of Truth", "72s", Rarity.MYTHIC, mage.cards.i.InverterOfTruth.class));
        cards.add(new SetCardInfo("Jori En, Ruin Diver", 155, Rarity.RARE, mage.cards.j.JoriEnRuinDiver.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jori En, Ruin Diver", "155s", Rarity.RARE, mage.cards.j.JoriEnRuinDiver.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kalitas, Traitor of Ghet", "86s", Rarity.MYTHIC, mage.cards.k.KalitasTraitorOfGhet.class));
        cards.add(new SetCardInfo("Kozilek's Return", "98s", Rarity.MYTHIC, mage.cards.k.KozileksReturn.class));
        cards.add(new SetCardInfo("Kozilek, the Great Distortion", "4s", Rarity.MYTHIC, mage.cards.k.KozilekTheGreatDistortion.class));
        cards.add(new SetCardInfo("Linvala, the Preserver", "25s", Rarity.MYTHIC, mage.cards.l.LinvalaThePreserver.class));
        cards.add(new SetCardInfo("Matter Reshaper", "6s", Rarity.RARE, mage.cards.m.MatterReshaper.class));
        cards.add(new SetCardInfo("Mina and Denn, Wildborn", "156s", Rarity.RARE, mage.cards.m.MinaAndDennWildborn.class));
        cards.add(new SetCardInfo("Mirrorpool", "174s", Rarity.MYTHIC, mage.cards.m.Mirrorpool.class));
        cards.add(new SetCardInfo("Munda's Vanguard", 29, Rarity.RARE, mage.cards.m.MundasVanguard.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Munda's Vanguard", "29s", Rarity.RARE, mage.cards.m.MundasVanguard.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Needle Spires", "175s", Rarity.RARE, mage.cards.n.NeedleSpires.class));
        cards.add(new SetCardInfo("Nissa, Voice of Zendikar", "138s", Rarity.MYTHIC, mage.cards.n.NissaVoiceOfZendikar.class));
        cards.add(new SetCardInfo("Oath of Chandra", "113s", Rarity.RARE, mage.cards.o.OathOfChandra.class));
        cards.add(new SetCardInfo("Oath of Gideon", "30s", Rarity.RARE, mage.cards.o.OathOfGideon.class));
        cards.add(new SetCardInfo("Oath of Jace", "60s", Rarity.RARE, mage.cards.o.OathOfJace.class));
        cards.add(new SetCardInfo("Oath of Nissa", "140s", Rarity.RARE, mage.cards.o.OathOfNissa.class));
        cards.add(new SetCardInfo("Overwhelming Denial", "61s", Rarity.RARE, mage.cards.o.OverwhelmingDenial.class));
        cards.add(new SetCardInfo("Reality Smasher", "7s", Rarity.RARE, mage.cards.r.RealitySmasher.class));
        cards.add(new SetCardInfo("Remorseless Punishment", "89s", Rarity.RARE, mage.cards.r.RemorselessPunishment.class));
        cards.add(new SetCardInfo("Ruins of Oran-Rief", "176s", Rarity.RARE, mage.cards.r.RuinsOfOranRief.class));
        cards.add(new SetCardInfo("Sea Gate Wreckage", "177s", Rarity.RARE, mage.cards.s.SeaGateWreckage.class));
        cards.add(new SetCardInfo("Sifter of Skulls", "77s", Rarity.RARE, mage.cards.s.SifterOfSkulls.class));
        cards.add(new SetCardInfo("Sphinx of the Final Word", "63s", Rarity.MYTHIC, mage.cards.s.SphinxOfTheFinalWord.class));
        cards.add(new SetCardInfo("Stone Haven Outfitter", "37s", Rarity.RARE, mage.cards.s.StoneHavenOutfitter.class));
        cards.add(new SetCardInfo("Stoneforge Masterwork", "166s", Rarity.RARE, mage.cards.s.StoneforgeMasterwork.class));
        cards.add(new SetCardInfo("Sylvan Advocate", "144s", Rarity.RARE, mage.cards.s.SylvanAdvocate.class));
        cards.add(new SetCardInfo("Thought-Knot Seer", "9s", Rarity.RARE, mage.cards.t.ThoughtKnotSeer.class));
        cards.add(new SetCardInfo("Tyrant of Valakut", 119, Rarity.RARE, mage.cards.t.TyrantOfValakut.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tyrant of Valakut", "119s", Rarity.RARE, mage.cards.t.TyrantOfValakut.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vile Redeemer", "125s", Rarity.RARE, mage.cards.v.VileRedeemer.class));
        cards.add(new SetCardInfo("Wandering Fumarole", "182s", Rarity.RARE, mage.cards.w.WanderingFumarole.class));
        cards.add(new SetCardInfo("World Breaker", "126s", Rarity.MYTHIC, mage.cards.w.WorldBreaker.class));
        cards.add(new SetCardInfo("Zendikar Resurgent", "147s", Rarity.RARE, mage.cards.z.ZendikarResurgent.class));
     }
}
