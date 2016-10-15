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
import mage.constants.SetType;
import mage.constants.Rarity;
import mage.cards.CardGraphicInfo;

/**
 *
 * @author North
 */
public class ArabianNights extends ExpansionSet {

    private static final ArabianNights fINSTANCE = new ArabianNights();

    public static ArabianNights getInstance() {
        return fINSTANCE;
    }

    private ArabianNights() {
        super("Arabian Nights", "ARN", ExpansionSet.buildDate(1993, 11, 1), SetType.EXPANSION);
        this.hasBasicLands = false;
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 0;
        cards.add(new SetCardInfo("Abu Ja'far", 55, Rarity.UNCOMMON, mage.cards.a.AbuJafar.class));
        cards.add(new SetCardInfo("Aladdin", 42, Rarity.RARE, mage.cards.a.Aladdin.class));
        cards.add(new SetCardInfo("Aladdin's Lamp", 70, Rarity.RARE, mage.cards.a.AladdinsLamp.class));
        cards.add(new SetCardInfo("Aladdin's Ring", 71, Rarity.RARE, mage.cards.a.AladdinsRing.class));
        cards.add(new SetCardInfo("Ali Baba", 43, Rarity.UNCOMMON, mage.cards.a.AliBaba.class));
        cards.add(new SetCardInfo("Ali from Cairo", 44, Rarity.RARE, mage.cards.a.AliFromCairo.class));
        cards.add(new SetCardInfo("Army of Allah", 56, Rarity.COMMON, mage.cards.a.ArmyOfAllah1.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Army of Allah", 57, Rarity.COMMON, mage.cards.a.ArmyOfAllah1.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Bazaar of Baghdad", 84, Rarity.UNCOMMON, mage.cards.b.BazaarOfBaghdad.class));
        cards.add(new SetCardInfo("Bird Maiden", 45, Rarity.COMMON, mage.cards.b.BirdMaiden.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Bird Maiden", 46, Rarity.COMMON, mage.cards.b.BirdMaiden.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Bottle of Suleiman", 72, Rarity.RARE, mage.cards.b.BottleOfSuleiman.class));
        cards.add(new SetCardInfo("Brass Man", 73, Rarity.UNCOMMON, mage.cards.b.BrassMan.class));
        cards.add(new SetCardInfo("City in a Bottle", 74, Rarity.RARE, mage.cards.c.CityInABottle.class));
        cards.add(new SetCardInfo("City of Brass", 85, Rarity.UNCOMMON, mage.cards.c.CityOfBrass.class));
        cards.add(new SetCardInfo("Cuombajj Witches", 1, Rarity.COMMON, mage.cards.c.CuombajjWitches.class));
        cards.add(new SetCardInfo("Cyclone", 29, Rarity.UNCOMMON, mage.cards.c.Cyclone.class));
        cards.add(new SetCardInfo("Dancing Scimitar", 75, Rarity.RARE, mage.cards.d.DancingScimitar.class));
        cards.add(new SetCardInfo("Dandan", 16, Rarity.COMMON, mage.cards.d.Dandan.class));
        cards.add(new SetCardInfo("Desert", 86, Rarity.COMMON, mage.cards.d.Desert.class));
        cards.add(new SetCardInfo("Desert Nomads", 47, Rarity.COMMON, mage.cards.d.DesertNomads.class));
        cards.add(new SetCardInfo("Desert Twister", 30, Rarity.UNCOMMON, mage.cards.d.DesertTwister.class));
        cards.add(new SetCardInfo("Diamond Valley", 87, Rarity.UNCOMMON, mage.cards.d.DiamondValley.class));
        cards.add(new SetCardInfo("Drop of Honey", 31, Rarity.RARE, mage.cards.d.DropOfHoney.class));
        cards.add(new SetCardInfo("Ebony Horse", 76, Rarity.RARE, mage.cards.e.EbonyHorse.class));
        cards.add(new SetCardInfo("Elephant Graveyard", 88, Rarity.RARE, mage.cards.e.ElephantGraveyard.class));
        cards.add(new SetCardInfo("El-Hajjaj", 2, Rarity.RARE, mage.cards.e.ElHajjaj.class));
        cards.add(new SetCardInfo("Erg Raiders", 3, Rarity.COMMON, mage.cards.e.ErgRaiders.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Erg Raiders", 4, Rarity.COMMON, mage.cards.e.ErgRaiders.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Erhnam Djinn", 32, Rarity.RARE, mage.cards.e.ErhnamDjinn.class));
        cards.add(new SetCardInfo("Eye for an Eye", 59, Rarity.UNCOMMON, mage.cards.e.EyeForAnEye.class));
        cards.add(new SetCardInfo("Fishliver Oil", 17, Rarity.COMMON, mage.cards.f.FishliverOil1.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Fishliver Oil", 18, Rarity.COMMON, mage.cards.f.FishliverOil1.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Flying Carpet", 77, Rarity.UNCOMMON, mage.cards.f.FlyingCarpet.class));
        cards.add(new SetCardInfo("Flying Men", 19, Rarity.COMMON, mage.cards.f.FlyingMen.class));
        cards.add(new SetCardInfo("Ghazban Ogre", 33, Rarity.COMMON, mage.cards.g.GhazbanOgre.class));
        cards.add(new SetCardInfo("Giant Tortoise", 20, Rarity.COMMON, mage.cards.g.GiantTortoise.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Giant Tortoise", 21, Rarity.COMMON, mage.cards.g.GiantTortoise.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Guardian Beast", 5, Rarity.RARE, mage.cards.g.GuardianBeast.class));
        cards.add(new SetCardInfo("Hasran Ogress", 6, Rarity.COMMON, mage.cards.h.HasranOgress.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Hasran Ogress", 7, Rarity.COMMON, mage.cards.h.HasranOgress.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Hurr Jackal", 48, Rarity.COMMON, mage.cards.h.HurrJackal.class));
        cards.add(new SetCardInfo("Ifh-Biff Efreet", 34, Rarity.RARE, mage.cards.i.IfhBiffEfreet.class));
        cards.add(new SetCardInfo("Island Fish Jasconius", 22, Rarity.RARE, mage.cards.i.IslandFishJasconius.class));
        cards.add(new SetCardInfo("Island of Wak-Wak", 89, Rarity.RARE, mage.cards.i.IslandOfWakWak.class));
        cards.add(new SetCardInfo("Jandor's Ring", 78, Rarity.RARE, mage.cards.j.JandorsRing.class));
        cards.add(new SetCardInfo("Jandor's Saddlebags", 79, Rarity.RARE, mage.cards.j.JandorsSaddlebags.class));
        cards.add(new SetCardInfo("Jihad", 60, Rarity.RARE, mage.cards.j.Jihad.class));
        cards.add(new SetCardInfo("Junún Efreet", 8, Rarity.RARE, mage.cards.j.JununEfreet.class));
        cards.add(new SetCardInfo("Juzam Djinn", 9, Rarity.RARE, mage.cards.j.JuzamDjinn.class));
        cards.add(new SetCardInfo("Khabal Ghoul", 10, Rarity.UNCOMMON, mage.cards.k.KhabalGhoul.class));
        cards.add(new SetCardInfo("King Suleiman", 61, Rarity.RARE, mage.cards.k.KingSuleiman.class));
        cards.add(new SetCardInfo("Kird Ape", 49, Rarity.COMMON, mage.cards.k.KirdApe.class));
        cards.add(new SetCardInfo("Library of Alexandria", 90, Rarity.UNCOMMON, mage.cards.l.LibraryOfAlexandria.class));
        cards.add(new SetCardInfo("Magnetic Mountain", 50, Rarity.UNCOMMON, mage.cards.m.MagneticMountain.class));
        cards.add(new SetCardInfo("Merchant Ship", 23, Rarity.UNCOMMON, mage.cards.m.MerchantShip.class));
        cards.add(new SetCardInfo("Metamorphosis", 35, Rarity.COMMON, mage.cards.m.Metamorphosis.class));
        cards.add(new SetCardInfo("Mijae Djinn", 51, Rarity.RARE, mage.cards.m.MijaeDjinn.class));
        cards.add(new SetCardInfo("Moorish Cavalry", 62, Rarity.COMMON, mage.cards.m.MoorishCavalry.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Moorish Cavalry", 63, Rarity.COMMON, mage.cards.m.MoorishCavalry.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Mountain", 91, Rarity.COMMON, mage.cards.basiclands.Mountain.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Nafs Asp", 36, Rarity.COMMON, mage.cards.n.NafsAsp1.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Nafs Asp", 37, Rarity.COMMON, mage.cards.n.NafsAsp1.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Oasis", 92, Rarity.UNCOMMON, mage.cards.o.Oasis.class));
        cards.add(new SetCardInfo("Old Man of the Sea", 24, Rarity.RARE, mage.cards.o.OldManOfTheSea.class));
        cards.add(new SetCardInfo("Oubliette", 11, Rarity.COMMON, mage.cards.o.Oubliette1.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Oubliette", 12, Rarity.COMMON, mage.cards.o.Oubliette1.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Piety", 64, Rarity.COMMON, mage.cards.p.Piety1.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Piety", 65, Rarity.COMMON, mage.cards.p.Piety1.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Pyramids", 81, Rarity.RARE, mage.cards.p.Pyramids.class));
        cards.add(new SetCardInfo("Repentant Blacksmith", 66, Rarity.RARE, mage.cards.r.RepentantBlacksmith.class));
        cards.add(new SetCardInfo("Rukh Egg", 52, Rarity.COMMON, mage.cards.r.RukhEgg1.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Rukh Egg", 53, Rarity.COMMON, mage.cards.r.RukhEgg1.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Sandals of Abdallah", 83, Rarity.UNCOMMON, mage.cards.s.SandalsOfAbdallah.class));
        cards.add(new SetCardInfo("Sandstorm", 38, Rarity.COMMON, mage.cards.s.Sandstorm.class));
        cards.add(new SetCardInfo("Serendib Djinn", 25, Rarity.RARE, mage.cards.s.SerendibDjinn.class));
        cards.add(new SetCardInfo("Serendib Efreet", 26, Rarity.RARE, mage.cards.s.SerendibEfreet.class));
        cards.add(new SetCardInfo("Sindbad", 27, Rarity.UNCOMMON, mage.cards.s.Sindbad.class));
        cards.add(new SetCardInfo("Singing Tree", 39, Rarity.RARE, mage.cards.s.SingingTree.class));
        cards.add(new SetCardInfo("Sorceress Queen", 13, Rarity.UNCOMMON, mage.cards.s.SorceressQueen.class));
        cards.add(new SetCardInfo("Stone-Throwing Devils", 14, Rarity.COMMON, mage.cards.s.StoneThrowingDevils1.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Stone-Throwing Devils", 15, Rarity.COMMON, mage.cards.s.StoneThrowingDevils1.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Unstable Mutation", 28, Rarity.COMMON, mage.cards.u.UnstableMutation.class));
        cards.add(new SetCardInfo("Wyluli Wolf", 40, Rarity.COMMON, mage.cards.w.WyluliWolf.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Wyluli Wolf", 41, Rarity.COMMON, mage.cards.w.WyluliWolf.class, new CardGraphicInfo(null, true)));
        cards.add(new SetCardInfo("Ydwen Efreet", 54, Rarity.RARE, mage.cards.y.YdwenEfreet.class));
    }
}
