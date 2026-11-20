package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author muz
 */
public class Stardates extends ExpansionSet {

    private static final Stardates instance = new Stardates();

    public static Stardates getInstance() {
        return instance;
    }

    private Stardates() {
        super("Stardates", "SDS", ExpansionSet.buildDate(2026, 11, 13), SetType.SUPPLEMENTAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Arcum Dagsson", 7, Rarity.MYTHIC, mage.cards.a.ArcumDagsson.class));
        cards.add(new SetCardInfo("Bristly Bill, Spine Sower", 15, Rarity.MYTHIC, mage.cards.b.BristlyBillSpineSower.class));
        cards.add(new SetCardInfo("Bruvac the Grandiloquent", 8, Rarity.MYTHIC, mage.cards.b.BruvacTheGrandiloquent.class));
        cards.add(new SetCardInfo("Captain Sisay", 22, Rarity.MYTHIC, mage.cards.c.CaptainSisay.class));
        cards.add(new SetCardInfo("Decree of Pain", 9, Rarity.MYTHIC, mage.cards.d.DecreeOfPain.class));
        cards.add(new SetCardInfo("Drivnod, Carnage Dominus", 10, Rarity.MYTHIC, mage.cards.d.DrivnodCarnageDominus.class));
        cards.add(new SetCardInfo("Empress Galina", 6, Rarity.MYTHIC, mage.cards.e.EmpressGalina.class));
        cards.add(new SetCardInfo("Ertai Resurrected", 20, Rarity.MYTHIC, mage.cards.e.ErtaiResurrected.class));
        cards.add(new SetCardInfo("Flare of Fortitude", 2, Rarity.MYTHIC, mage.cards.f.FlareOfFortitude.class));
        cards.add(new SetCardInfo("Hull Breach", 18, Rarity.MYTHIC, mage.cards.h.HullBreach.class));
        cards.add(new SetCardInfo("Isshin, Two Heavens as One", 19, Rarity.MYTHIC, mage.cards.i.IsshinTwoHeavensAsOne.class));
        cards.add(new SetCardInfo("Jhoira, Weatherlight Captain", 21, Rarity.MYTHIC, mage.cards.j.JhoiraWeatherlightCaptain.class));
        cards.add(new SetCardInfo("Kenrith, the Returned King", 3, Rarity.MYTHIC, mage.cards.k.KenrithTheReturnedKing.class));
        cards.add(new SetCardInfo("Loot, Exuberant Explorer", 13, Rarity.MYTHIC, mage.cards.l.LootExuberantExplorer.class));
        cards.add(new SetCardInfo("Mikaeus, the Lunarch", 1, Rarity.MYTHIC, mage.cards.m.MikaeusTheLunarch.class));
        cards.add(new SetCardInfo("Minamo, School at Water's Edge", 27, Rarity.MYTHIC, mage.cards.m.MinamoSchoolAtWatersEdge.class));
        cards.add(new SetCardInfo("Mogis, God of Slaughter", 26, Rarity.MYTHIC, mage.cards.m.MogisGodOfSlaughter.class));
        cards.add(new SetCardInfo("Phyrexian Tower", 29, Rarity.MYTHIC, mage.cards.p.PhyrexianTower.class));
        cards.add(new SetCardInfo("Prosper, Tome-Bound", 24, Rarity.MYTHIC, mage.cards.p.ProsperTomeBound.class));
        cards.add(new SetCardInfo("Riku of Many Paths", 23, Rarity.MYTHIC, mage.cards.r.RikuOfManyPaths.class));
        cards.add(new SetCardInfo("Rishkar, Peema Renegade", 14, Rarity.MYTHIC, mage.cards.r.RishkarPeemaRenegade.class));
        cards.add(new SetCardInfo("Sen Triplets", 16, Rarity.MYTHIC, mage.cards.s.SenTriplets.class));
        cards.add(new SetCardInfo("Sheoldred, the Apocalypse", 11, Rarity.MYTHIC, mage.cards.s.SheoldredTheApocalypse.class));
        cards.add(new SetCardInfo("Shizo, Death's Storehouse", 30, Rarity.MYTHIC, mage.cards.s.ShizoDeathsStorehouse.class));
        cards.add(new SetCardInfo("Sisay, Weatherlight Captain", 4, Rarity.MYTHIC, mage.cards.s.SisayWeatherlightCaptain.class));
        cards.add(new SetCardInfo("Stella Lee, Wild Card", 25, Rarity.MYTHIC, mage.cards.s.StellaLeeWildCard.class));
        cards.add(new SetCardInfo("Talon Gates of Madara", 28, Rarity.MYTHIC, mage.cards.t.TalonGatesOfMadara.class));
        cards.add(new SetCardInfo("Tatyova, Benthic Druid", 17, Rarity.MYTHIC, mage.cards.t.TatyovaBenthicDruid.class));
        cards.add(new SetCardInfo("Thada Adel, Acquisitor", 5, Rarity.MYTHIC, mage.cards.t.ThadaAdelAcquisitor.class));
        cards.add(new SetCardInfo("Torbran, Thane of Red Fell", 12, Rarity.MYTHIC, mage.cards.t.TorbranThaneOfRedFell.class));
    }
}
