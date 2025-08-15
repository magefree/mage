package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * Italian version of Renaissance
 * <p>
 * https://scryfall.com/sets/rin
 * https://mtg.wiki/page/Renaissance
 *
 * @author JayDi85
 */
public final class Rinascimento extends ExpansionSet {

    private static final Rinascimento instance = new Rinascimento();

    public static Rinascimento getInstance() {
        return instance;
    }

    private Rinascimento() {
        super("Rinascimento", "RIN", ExpansionSet.buildDate(1995, 8, 1), SetType.SUPPLEMENTAL);
        this.hasBasicLands = true;

        this.enableCollectorBooster(Integer.MAX_VALUE, 0, 6, 2, 0);

        cards.add(new SetCardInfo("Abu Ja'far", 1, Rarity.UNCOMMON, mage.cards.a.AbuJafar.class, RETRO_ART));
        cards.add(new SetCardInfo("Aladdin", 70, Rarity.UNCOMMON, mage.cards.a.Aladdin.class, RETRO_ART));
        cards.add(new SetCardInfo("Ali Baba", 71, Rarity.UNCOMMON, mage.cards.a.AliBaba.class, RETRO_ART));
        cards.add(new SetCardInfo("Amulet of Kroog", 99, Rarity.COMMON, mage.cards.a.AmuletOfKroog.class, RETRO_ART));
        cards.add(new SetCardInfo("Argothian Pixies", 100, Rarity.COMMON, mage.cards.a.ArgothianPixies.class, RETRO_ART));
        cards.add(new SetCardInfo("Ashnod's Altar", 101, Rarity.UNCOMMON, mage.cards.a.AshnodsAltar.class, RETRO_ART));
        cards.add(new SetCardInfo("Ashnod's Battle Gear", 102, Rarity.UNCOMMON, mage.cards.a.AshnodsBattleGear.class, RETRO_ART));
        cards.add(new SetCardInfo("Ashnod's Transmogrant", 103, Rarity.UNCOMMON, mage.cards.a.AshnodsTransmogrant.class, RETRO_ART));
        cards.add(new SetCardInfo("Battering Ram", 105, Rarity.COMMON, mage.cards.b.BatteringRam.class, RETRO_ART));
        cards.add(new SetCardInfo("Bird Maiden", 72, Rarity.COMMON, mage.cards.b.BirdMaiden.class, RETRO_ART));
        //cards.add(new SetCardInfo("Bronze Tablet", 106, Rarity.UNCOMMON, mage.cards.b.BronzeTablet.class, RETRO_ART));
        cards.add(new SetCardInfo("Circle of Protection: Artifacts", 5, Rarity.UNCOMMON, mage.cards.c.CircleOfProtectionArtifacts.class, RETRO_ART));
        cards.add(new SetCardInfo("City of Brass", 171, Rarity.UNCOMMON, mage.cards.c.CityOfBrass.class, RETRO_ART));
        cards.add(new SetCardInfo("Clay Statue", 109, Rarity.COMMON, mage.cards.c.ClayStatue.class, RETRO_ART));
        cards.add(new SetCardInfo("Clockwork Avian", 110, Rarity.UNCOMMON, mage.cards.c.ClockworkAvian.class, RETRO_ART));
        cards.add(new SetCardInfo("Colossus of Sardia", 111, Rarity.UNCOMMON, mage.cards.c.ColossusOfSardia.class, RETRO_ART));
        cards.add(new SetCardInfo("Coral Helm", 113, Rarity.UNCOMMON, mage.cards.c.CoralHelm.class, RETRO_ART));
        cards.add(new SetCardInfo("Cuombajj Witches", 50, Rarity.COMMON, mage.cards.c.CuombajjWitches.class, RETRO_ART));
        cards.add(new SetCardInfo("Cursed Rack", 114, Rarity.COMMON, mage.cards.c.CursedRack.class, RETRO_ART));
        cards.add(new SetCardInfo("Cyclone", 116, Rarity.UNCOMMON, mage.cards.c.Cyclone.class, RETRO_ART));
        cards.add(new SetCardInfo("Dandan", 25, Rarity.COMMON, mage.cards.d.Dandan.class, RETRO_ART));
        cards.add(new SetCardInfo("Detonate", 77, Rarity.UNCOMMON, mage.cards.d.Detonate.class, RETRO_ART));
        cards.add(new SetCardInfo("Erhnam Djinn", 118, Rarity.UNCOMMON, mage.cards.e.ErhnamDjinn.class, RETRO_ART));
        cards.add(new SetCardInfo("Feldon's Cane", 119, Rarity.COMMON, mage.cards.f.FeldonsCane.class, RETRO_ART));
        cards.add(new SetCardInfo("Fishliver Oil", 26, Rarity.COMMON, mage.cards.f.FishliverOil.class, RETRO_ART));
        cards.add(new SetCardInfo("Ghazban Ogre", 121, Rarity.COMMON, mage.cards.g.GhazbanOgre.class, RETRO_ART));
        cards.add(new SetCardInfo("Giant Tortoise", 27, Rarity.COMMON, mage.cards.g.GiantTortoise.class, RETRO_ART));
        cards.add(new SetCardInfo("Goblin Artisans", 79, Rarity.UNCOMMON, mage.cards.g.GoblinArtisans.class, RETRO_ART));
        cards.add(new SetCardInfo("Grapeshot Catapult", 125, Rarity.COMMON, mage.cards.g.GrapeshotCatapult.class, RETRO_ART));
        cards.add(new SetCardInfo("Hasran Ogress", 53, Rarity.COMMON, mage.cards.h.HasranOgress.class, RETRO_ART));
        cards.add(new SetCardInfo("Hurr Jackal", 81, Rarity.COMMON, mage.cards.h.HurrJackal.class, RETRO_ART));
        cards.add(new SetCardInfo("Ironclaw Orcs", 82, Rarity.COMMON, mage.cards.i.IronclawOrcs.class, RETRO_ART));
        cards.add(new SetCardInfo("Jalum Tome", 127, Rarity.UNCOMMON, mage.cards.j.JalumTome.class, RETRO_ART));
        //cards.add(new SetCardInfo("Jeweled Bird", 128, Rarity.UNCOMMON, mage.cards.j.JeweledBird.class, RETRO_ART));
        cards.add(new SetCardInfo("Junun Efreet", 55, Rarity.UNCOMMON, mage.cards.j.JununEfreet.class, RETRO_ART));
        cards.add(new SetCardInfo("Metamorphosis", 130, Rarity.COMMON, mage.cards.m.Metamorphosis.class, RETRO_ART));
        cards.add(new SetCardInfo("Mishra's Factory", 172, Rarity.UNCOMMON, mage.cards.m.MishrasFactory.class, RETRO_ART));
        cards.add(new SetCardInfo("Nafs Asp", 132, Rarity.COMMON, mage.cards.n.NafsAsp.class, RETRO_ART));
        cards.add(new SetCardInfo("Oasis", 173, Rarity.UNCOMMON, mage.cards.o.Oasis.class, RETRO_ART));
        cards.add(new SetCardInfo("Obelisk of Undoing", 134, Rarity.UNCOMMON, mage.cards.o.ObeliskOfUndoing.class, RETRO_ART));
        cards.add(new SetCardInfo("Rakalite", 135, Rarity.UNCOMMON, mage.cards.r.Rakalite.class, RETRO_ART));
        cards.add(new SetCardInfo("Repentant Blacksmith", 10, Rarity.UNCOMMON, mage.cards.r.RepentantBlacksmith.class, RETRO_ART));
        cards.add(new SetCardInfo("Sandstorm", 137, Rarity.COMMON, mage.cards.s.Sandstorm.class, RETRO_ART));
        cards.add(new SetCardInfo("Shapeshifter", 138, Rarity.UNCOMMON, mage.cards.s.Shapeshifter.class, RETRO_ART));
        cards.add(new SetCardInfo("Sindbad", 31, Rarity.UNCOMMON, mage.cards.s.Sindbad.class, RETRO_ART));
        cards.add(new SetCardInfo("Strip Mine", 174, Rarity.UNCOMMON, mage.cards.s.StripMine.class, RETRO_ART));
        cards.add(new SetCardInfo("Tawnos's Wand", 139, Rarity.UNCOMMON, mage.cards.t.TawnossWand.class, RETRO_ART));
        cards.add(new SetCardInfo("Tawnos's Weaponry", 140, Rarity.UNCOMMON, mage.cards.t.TawnossWeaponry.class, RETRO_ART));
        cards.add(new SetCardInfo("Tetravus", 141, Rarity.UNCOMMON, mage.cards.t.Tetravus.class, RETRO_ART));
        cards.add(new SetCardInfo("Triskelion", 142, Rarity.UNCOMMON, mage.cards.t.Triskelion.class, RETRO_ART));
        cards.add(new SetCardInfo("Twiddle", 35, Rarity.COMMON, mage.cards.t.Twiddle.class, RETRO_ART));
        cards.add(new SetCardInfo("Urza's Avenger", 144, Rarity.UNCOMMON, mage.cards.u.UrzasAvenger.class, RETRO_ART));
        cards.add(new SetCardInfo("Urza's Mine", 175, Rarity.COMMON, mage.cards.u.UrzasMine.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza's Mine", 176, Rarity.COMMON, mage.cards.u.UrzasMine.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza's Mine", 177, Rarity.COMMON, mage.cards.u.UrzasMine.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza's Mine", 178, Rarity.COMMON, mage.cards.u.UrzasMine.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza's Power Plant", 179, Rarity.COMMON, mage.cards.u.UrzasPowerPlant.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza's Power Plant", 180, Rarity.COMMON, mage.cards.u.UrzasPowerPlant.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza's Power Plant", 181, Rarity.COMMON, mage.cards.u.UrzasPowerPlant.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza's Power Plant", 182, Rarity.COMMON, mage.cards.u.UrzasPowerPlant.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza's Tower", 183, Rarity.COMMON, mage.cards.u.UrzasTower.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza's Tower", 184, Rarity.COMMON, mage.cards.u.UrzasTower.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza's Tower", 185, Rarity.COMMON, mage.cards.u.UrzasTower.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza's Tower", 186, Rarity.COMMON, mage.cards.u.UrzasTower.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Wall of Spears", 148, Rarity.UNCOMMON, mage.cards.w.WallOfSpears.class, RETRO_ART));
        cards.add(new SetCardInfo("War Elephant", 14, Rarity.COMMON, mage.cards.w.WarElephant.class, RETRO_ART));
        cards.add(new SetCardInfo("Xenic Poltergeist", 60, Rarity.UNCOMMON, mage.cards.x.XenicPoltergeist.class, RETRO_ART));
        cards.add(new SetCardInfo("Yawgmoth Demon", 61, Rarity.UNCOMMON, mage.cards.y.YawgmothDemon.class, RETRO_ART));
        cards.add(new SetCardInfo("Yotian Soldier", 150, Rarity.COMMON, mage.cards.y.YotianSoldier.class, RETRO_ART));
    }
}
