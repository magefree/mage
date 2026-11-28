package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pwcs
 *
 * @author DarioBarrabinoDev
 */
public final class PlaneswalkerChampionshipPromos extends ExpansionSet {

    private static final PlaneswalkerChampionshipPromos instance = new PlaneswalkerChampionshipPromos();

    public static PlaneswalkerChampionshipPromos getInstance() {
        return instance;
    }

    private PlaneswalkerChampionshipPromos() {
        super("Planeswalker Championship Promos", "PWCS", ExpansionSet.buildDate(2019, 10, 1), SetType.PROMOTIONAL);
        hasBasicLands = false;

        cards.add(new SetCardInfo("Adult Gold Dragon", "2022-2", Rarity.RARE, mage.cards.a.AdultGoldDragon.class));
        cards.add(new SetCardInfo("Aven Interrupter", "2024-08", Rarity.RARE, mage.cards.a.AvenInterrupter.class));
        cards.add(new SetCardInfo("Banishing Slash", "2022-6", Rarity.UNCOMMON, mage.cards.b.BanishingSlash.class));
        cards.add(new SetCardInfo("Basri Ket", "2021-1", Rarity.MYTHIC, mage.cards.b.BasriKet.class));
        cards.add(new SetCardInfo("Beza, the Bounding Spring", "2026-1", Rarity.MYTHIC, mage.cards.b.BezaTheBoundingSpring.class));
        cards.add(new SetCardInfo("Braids, Arisen Nightmare", "2023-4", Rarity.RARE, mage.cards.b.BraidsArisenNightmare.class));
        cards.add(new SetCardInfo("Delney, Streetwise Lookout", "2025-3", Rarity.MYTHIC, mage.cards.d.DelneyStreetwiseLookout.class));
        cards.add(new SetCardInfo("Elspeth Resplendent", "2023-1", Rarity.MYTHIC, mage.cards.e.ElspethResplendent.class));
        cards.add(new SetCardInfo("Elspeth, Sun's Nemesis", "2020-6", Rarity.MYTHIC, mage.cards.e.ElspethSunsNemesis.class));
        cards.add(new SetCardInfo("Experimental Frenzy", "2019-2", Rarity.RARE, mage.cards.e.ExperimentalFrenzy.class));
        cards.add(new SetCardInfo("Faceless Haven", "2021-6", Rarity.RARE, mage.cards.f.FacelessHaven.class));
        cards.add(new SetCardInfo("Fae of Wishes", "2020-5", Rarity.RARE, mage.cards.f.FaeOfWishes.class));
        cards.add(new SetCardInfo("Farewell", "2022-5", Rarity.RARE, mage.cards.f.Farewell.class));
        cards.add(new SetCardInfo("Get Lost", "2025-1", Rarity.RARE, mage.cards.g.GetLost.class));
        cards.add(new SetCardInfo("Giada, Font of Hope", "2023-2", Rarity.RARE, mage.cards.g.GiadaFontOfHope.class));
        cards.add(new SetCardInfo("Glimpse the Core", "2024-03", Rarity.RARE, mage.cards.g.GlimpseTheCore.class));
        cards.add(new SetCardInfo("Grand Master of Flowers", "2022-1", Rarity.MYTHIC, mage.cards.g.GrandMasterOfFlowers.class));
        cards.add(new SetCardInfo("Inti, Seneschal of the Sun", "2025-4", Rarity.RARE, mage.cards.i.IntiSeneschalOfTheSun.class));
        cards.add(new SetCardInfo("Into the Flood Maw", "2026-2", Rarity.RARE, mage.cards.i.IntoTheFloodMaw.class));
        cards.add(new SetCardInfo("Jace Reawakened", "2024-09", Rarity.MYTHIC, mage.cards.j.JaceReawakened.class));
        cards.add(new SetCardInfo("Kaya the Inexorable", "2021-5", Rarity.MYTHIC, mage.cards.k.KayaTheInexorable.class));
        cards.add(new SetCardInfo("Kaya, Spirits' Justice", "2024-06", Rarity.MYTHIC, mage.cards.k.KayaSpiritsJustice.class));
        cards.add(new SetCardInfo("Krydle of Baldur's Gate", "2022-3", Rarity.UNCOMMON, mage.cards.k.KrydleOfBaldursGate.class));
        cards.add(new SetCardInfo("Lightning Helix", "2024-07", Rarity.RARE, mage.cards.l.LightningHelix.class));
        cards.add(new SetCardInfo("Liliana of the Veil", "2023-3", Rarity.MYTHIC, mage.cards.l.LilianaOfTheVeil.class));
        cards.add(new SetCardInfo("Liliana, Dreadhorde General", "2020-3", Rarity.MYTHIC, mage.cards.l.LilianaDreadhordeGeneral.class));
        cards.add(new SetCardInfo("Monstrous Rage", "2025-5", Rarity.RARE, mage.cards.m.MonstrousRage.class));
        cards.add(new SetCardInfo("Mystical Dispute", "2020-4", Rarity.UNCOMMON, mage.cards.m.MysticalDispute.class));
        cards.add(new SetCardInfo("Narset, Parter of Veils", "2020-1", Rarity.UNCOMMON, mage.cards.n.NarsetParterOfVeils.class));
        cards.add(new SetCardInfo("Nissa, Resurgent Animist", "2025-2", Rarity.MYTHIC, mage.cards.n.NissaResurgentAnimist.class));
        cards.add(new SetCardInfo("Nissa, Who Shakes the World", "2020-2", Rarity.RARE, mage.cards.n.NissaWhoShakesTheWorld.class));
        cards.add(new SetCardInfo("Phyrexian Rager", "2023-5", Rarity.UNCOMMON, mage.cards.p.PhyrexianRager.class));
        cards.add(new SetCardInfo("Pteramander", "2019-1", Rarity.UNCOMMON, mage.cards.p.Pteramander.class));
        cards.add(new SetCardInfo("Realm-Scorcher Hellkite", "2024-01", Rarity.MYTHIC, mage.cards.r.RealmScorcherHellkite.class));
        cards.add(new SetCardInfo("Ruby, Daring Tracker", "2024-02", Rarity.RARE, mage.cards.r.RubyDaringTracker.class));
        cards.add(new SetCardInfo("Saheeli, the Sun's Brilliance", "2024-05", Rarity.MYTHIC, mage.cards.s.SaheeliTheSunsBrilliance.class));
        cards.add(new SetCardInfo("Saw It Coming", "2021-4", Rarity.UNCOMMON, mage.cards.s.SawItComing.class));
        cards.add(new SetCardInfo("Seasoned Hallowblade", "2021-2", Rarity.UNCOMMON, mage.cards.s.SeasonedHallowblade.class));
        cards.add(new SetCardInfo("Slickshot Show-Off", "2025-6", Rarity.RARE, mage.cards.s.SlickshotShowOff.class));
        cards.add(new SetCardInfo("Strategic Betrayal", "2026-6", Rarity.RARE, mage.cards.s.StrategicBetrayal.class));
        cards.add(new SetCardInfo("The Wandering Emperor", "2022-4", Rarity.MYTHIC, mage.cards.t.TheWanderingEmperor.class));
        cards.add(new SetCardInfo("The Wandering Rescuer", "2026-3", Rarity.MYTHIC, mage.cards.t.TheWanderingRescuer.class));
        cards.add(new SetCardInfo("Twitching Doll", "2026-4", Rarity.RARE, mage.cards.t.TwitchingDoll.class));
        cards.add(new SetCardInfo("Vito, Thorn of the Dusk Rose", "2021-3", Rarity.RARE, mage.cards.v.VitoThornOfTheDuskRose.class));
        cards.add(new SetCardInfo("Voice of Victory", "2026-5", Rarity.RARE, mage.cards.v.VoiceOfVictory.class));
        cards.add(new SetCardInfo("Vraska, Golgari Queen", "2019-3", Rarity.MYTHIC, mage.cards.v.VraskaGolgariQueen.class));
    }
}
