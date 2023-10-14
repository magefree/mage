package mage.sets;

import mage.cards.CardGraphicInfo;
import mage.cards.ExpansionSet;
import mage.cards.FrameStyle;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/mps
 *
 * @author fireshoes
 */
public final class KaladeshInventions extends ExpansionSet {

    private static final KaladeshInventions instance = new KaladeshInventions();

    public static KaladeshInventions getInstance() {
        return instance;
    }

    private KaladeshInventions() {
        super("Kaladesh Inventions", "MPS", ExpansionSet.buildDate(2016, 9, 30), SetType.PROMOTIONAL);
        this.blockName = "Masterpiece Series";
        this.hasBoosters = false;
        this.hasBasicLands = false;

        CardGraphicInfo cardGraphicInfo = new CardGraphicInfo(FrameStyle.KLD_INVENTION, false);

        cards.add(new SetCardInfo("Aether Vial", 6, Rarity.SPECIAL, mage.cards.a.AetherVial.class, cardGraphicInfo));
        cards.add(new SetCardInfo("Arcbound Ravager", 31, Rarity.SPECIAL, mage.cards.a.ArcboundRavager.class, cardGraphicInfo));
        cards.add(new SetCardInfo("Black Vise", 32, Rarity.SPECIAL, mage.cards.b.BlackVise.class, cardGraphicInfo));
        cards.add(new SetCardInfo("Cataclysmic Gearhulk", 1, Rarity.SPECIAL, mage.cards.c.CataclysmicGearhulk.class, cardGraphicInfo));
        cards.add(new SetCardInfo("Chalice of the Void", 33, Rarity.SPECIAL, mage.cards.c.ChaliceOfTheVoid.class, cardGraphicInfo));
        cards.add(new SetCardInfo("Champion's Helm", 7, Rarity.SPECIAL, mage.cards.c.ChampionsHelm.class, cardGraphicInfo));
        cards.add(new SetCardInfo("Chromatic Lantern", 8, Rarity.SPECIAL, mage.cards.c.ChromaticLantern.class, cardGraphicInfo));
        cards.add(new SetCardInfo("Chrome Mox", 9, Rarity.SPECIAL, mage.cards.c.ChromeMox.class, cardGraphicInfo));
        cards.add(new SetCardInfo("Cloudstone Curio", 10, Rarity.SPECIAL, mage.cards.c.CloudstoneCurio.class, cardGraphicInfo));
        cards.add(new SetCardInfo("Combustible Gearhulk", 4, Rarity.SPECIAL, mage.cards.c.CombustibleGearhulk.class, cardGraphicInfo));
        cards.add(new SetCardInfo("Crucible of Worlds", 11, Rarity.SPECIAL, mage.cards.c.CrucibleOfWorlds.class, cardGraphicInfo));
        cards.add(new SetCardInfo("Defense Grid", 34, Rarity.SPECIAL, mage.cards.d.DefenseGrid.class, cardGraphicInfo));
        cards.add(new SetCardInfo("Duplicant", 35, Rarity.SPECIAL, mage.cards.d.Duplicant.class, cardGraphicInfo));
        cards.add(new SetCardInfo("Engineered Explosives", 36, Rarity.SPECIAL, mage.cards.e.EngineeredExplosives.class, cardGraphicInfo));
        cards.add(new SetCardInfo("Ensnaring Bridge", 37, Rarity.SPECIAL, mage.cards.e.EnsnaringBridge.class, cardGraphicInfo));
        cards.add(new SetCardInfo("Extraplanar Lens", 38, Rarity.SPECIAL, mage.cards.e.ExtraplanarLens.class, cardGraphicInfo));
        cards.add(new SetCardInfo("Gauntlet of Power", 12, Rarity.SPECIAL, mage.cards.g.GauntletOfPower.class, cardGraphicInfo));
        cards.add(new SetCardInfo("Grindstone", 39, Rarity.SPECIAL, mage.cards.g.Grindstone.class, cardGraphicInfo));
        cards.add(new SetCardInfo("Hangarback Walker", 13, Rarity.SPECIAL, mage.cards.h.HangarbackWalker.class, cardGraphicInfo));
        cards.add(new SetCardInfo("Lightning Greaves", 14, Rarity.SPECIAL, mage.cards.l.LightningGreaves.class, cardGraphicInfo));
        cards.add(new SetCardInfo("Lotus Petal", 15, Rarity.SPECIAL, mage.cards.l.LotusPetal.class, cardGraphicInfo));
        cards.add(new SetCardInfo("Mana Crypt", 16, Rarity.SPECIAL, mage.cards.m.ManaCrypt.class, cardGraphicInfo));
        cards.add(new SetCardInfo("Mana Vault", 17, Rarity.SPECIAL, mage.cards.m.ManaVault.class, cardGraphicInfo));
        cards.add(new SetCardInfo("Meekstone", 40, Rarity.SPECIAL, mage.cards.m.Meekstone.class, cardGraphicInfo));
        cards.add(new SetCardInfo("Mind's Eye", 18, Rarity.SPECIAL, mage.cards.m.MindsEye.class, cardGraphicInfo));
        cards.add(new SetCardInfo("Mox Opal", 19, Rarity.SPECIAL, mage.cards.m.MoxOpal.class, cardGraphicInfo));
        cards.add(new SetCardInfo("Noxious Gearhulk", 3, Rarity.SPECIAL, mage.cards.n.NoxiousGearhulk.class, cardGraphicInfo));
        cards.add(new SetCardInfo("Oblivion Stone", 41, Rarity.SPECIAL, mage.cards.o.OblivionStone.class, cardGraphicInfo));
        cards.add(new SetCardInfo("Ornithopter", 42, Rarity.SPECIAL, mage.cards.o.Ornithopter.class, cardGraphicInfo));
        cards.add(new SetCardInfo("Painter's Servant", 20, Rarity.SPECIAL, mage.cards.p.PaintersServant.class, cardGraphicInfo));
        cards.add(new SetCardInfo("Paradox Engine", 43, Rarity.SPECIAL, mage.cards.p.ParadoxEngine.class));
        cards.add(new SetCardInfo("Pithing Needle", 44, Rarity.SPECIAL, mage.cards.p.PithingNeedle.class, cardGraphicInfo));
        cards.add(new SetCardInfo("Planar Bridge", 45, Rarity.SPECIAL, mage.cards.p.PlanarBridge.class));
        cards.add(new SetCardInfo("Platinum Angel", 46, Rarity.SPECIAL, mage.cards.p.PlatinumAngel.class, cardGraphicInfo));
        cards.add(new SetCardInfo("Rings of Brighthearth", 21, Rarity.SPECIAL, mage.cards.r.RingsOfBrighthearth.class, cardGraphicInfo));
        cards.add(new SetCardInfo("Scroll Rack", 22, Rarity.SPECIAL, mage.cards.s.ScrollRack.class, cardGraphicInfo));
        cards.add(new SetCardInfo("Sculpting Steel", 23, Rarity.SPECIAL, mage.cards.s.SculptingSteel.class, cardGraphicInfo));
        cards.add(new SetCardInfo("Sol Ring", 24, Rarity.SPECIAL, mage.cards.s.SolRing.class, cardGraphicInfo));
        cards.add(new SetCardInfo("Solemn Simulacrum", 25, Rarity.SPECIAL, mage.cards.s.SolemnSimulacrum.class, cardGraphicInfo));
        cards.add(new SetCardInfo("Sphere of Resistance", 47, Rarity.SPECIAL, mage.cards.s.SphereOfResistance.class, cardGraphicInfo));
        cards.add(new SetCardInfo("Staff of Domination", 48, Rarity.SPECIAL, mage.cards.s.StaffOfDomination.class, cardGraphicInfo));
        cards.add(new SetCardInfo("Static Orb", 26, Rarity.SPECIAL, mage.cards.s.StaticOrb.class, cardGraphicInfo));
        cards.add(new SetCardInfo("Steel Overseer", 27, Rarity.SPECIAL, mage.cards.s.SteelOverseer.class, cardGraphicInfo));
        cards.add(new SetCardInfo("Sundering Titan", 49, Rarity.SPECIAL, mage.cards.s.SunderingTitan.class, cardGraphicInfo));
        cards.add(new SetCardInfo("Sword of Body and Mind", 50, Rarity.SPECIAL, mage.cards.s.SwordOfBodyAndMind.class, cardGraphicInfo));
        cards.add(new SetCardInfo("Sword of Feast and Famine", 28, Rarity.SPECIAL, mage.cards.s.SwordOfFeastAndFamine.class, cardGraphicInfo));
        cards.add(new SetCardInfo("Sword of Fire and Ice", 29, Rarity.SPECIAL, mage.cards.s.SwordOfFireAndIce.class, cardGraphicInfo));
        cards.add(new SetCardInfo("Sword of Light and Shadow", 30, Rarity.SPECIAL, mage.cards.s.SwordOfLightAndShadow.class, cardGraphicInfo));
        cards.add(new SetCardInfo("Sword of War and Peace", 51, Rarity.SPECIAL, mage.cards.s.SwordOfWarAndPeace.class, cardGraphicInfo));
        cards.add(new SetCardInfo("Torrential Gearhulk", 2, Rarity.SPECIAL, mage.cards.t.TorrentialGearhulk.class, cardGraphicInfo));
        cards.add(new SetCardInfo("Trinisphere", 52, Rarity.SPECIAL, mage.cards.t.Trinisphere.class, cardGraphicInfo));
        cards.add(new SetCardInfo("Vedalken Shackles", 53, Rarity.SPECIAL, mage.cards.v.VedalkenShackles.class, cardGraphicInfo));
        cards.add(new SetCardInfo("Verdurous Gearhulk", 5, Rarity.SPECIAL, mage.cards.v.VerdurousGearhulk.class, cardGraphicInfo));
        cards.add(new SetCardInfo("Wurmcoil Engine", 54, Rarity.SPECIAL, mage.cards.w.WurmcoilEngine.class, cardGraphicInfo));
    }
}
