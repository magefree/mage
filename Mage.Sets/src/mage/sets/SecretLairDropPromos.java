package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/psld
 */
public class SecretLairDropPromos extends ExpansionSet {

    private static final SecretLairDropPromos instance = new SecretLairDropPromos();

    public static SecretLairDropPromos getInstance() {
        return instance;
    }

    private SecretLairDropPromos() {
        super("Secret Lair Drop Promos", "PSLD", ExpansionSet.buildDate(2020, 2, 18), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Ajani, the Greathearted", 520, Rarity.RARE, mage.cards.a.AjaniTheGreathearted.class));
        cards.add(new SetCardInfo("Angrath, Captain of Chaos", 527, Rarity.UNCOMMON, mage.cards.a.AngrathCaptainOfChaos.class));
        cards.add(new SetCardInfo("Arlinn, Voice of the Pack", 516, Rarity.UNCOMMON, mage.cards.a.ArlinnVoiceOfThePack.class));
        cards.add(new SetCardInfo("Ashiok, Dream Render", 528, Rarity.UNCOMMON, mage.cards.a.AshiokDreamRender.class));
        cards.add(new SetCardInfo("Chandra, Fire Artisan", 512, Rarity.RARE, mage.cards.c.ChandraFireArtisan.class));
        cards.add(new SetCardInfo("Davriel, Rogue Shadowmage", 509, Rarity.UNCOMMON, mage.cards.d.DavrielRogueShadowmage.class));
        cards.add(new SetCardInfo("Domri, Anarch of Bolas", 521, Rarity.RARE, mage.cards.d.DomriAnarchOfBolas.class));
        cards.add(new SetCardInfo("Dovin, Hand of Control", 529, Rarity.UNCOMMON, mage.cards.d.DovinHandOfControl.class));
        cards.add(new SetCardInfo("Gideon Blackblade", 503, Rarity.MYTHIC, mage.cards.g.GideonBlackblade.class));
        cards.add(new SetCardInfo("Huatli, the Sun's Heart", 530, Rarity.UNCOMMON, mage.cards.h.HuatliTheSunsHeart.class));
        cards.add(new SetCardInfo("Jace, Wielder of Mysteries", 506, Rarity.RARE, mage.cards.j.JaceWielderOfMysteries.class));
        cards.add(new SetCardInfo("Jaya, Venerated Firemage", 513, Rarity.UNCOMMON, mage.cards.j.JayaVeneratedFiremage.class));
        cards.add(new SetCardInfo("Jiang Yanggu, Wildcrafter", 517, Rarity.UNCOMMON, mage.cards.j.JiangYangguWildcrafter.class));
        cards.add(new SetCardInfo("Kasmina, Enigmatic Mentor", 507, Rarity.UNCOMMON, mage.cards.k.KasminaEnigmaticMentor.class));
        cards.add(new SetCardInfo("Kaya, Bane of the Dead", 531, Rarity.UNCOMMON, mage.cards.k.KayaBaneOfTheDead.class));
        cards.add(new SetCardInfo("Kiora, Behemoth Beckoner", 532, Rarity.UNCOMMON, mage.cards.k.KioraBehemothBeckoner.class));
        cards.add(new SetCardInfo("Liliana, Dreadhorde General", 510, Rarity.MYTHIC, mage.cards.l.LilianaDreadhordeGeneral.class));
        cards.add(new SetCardInfo("Nahiri, Storm of Stone", 533, Rarity.UNCOMMON, mage.cards.n.NahiriStormOfStone.class));
        cards.add(new SetCardInfo("Narset, Parter of Veils", 508, Rarity.UNCOMMON, mage.cards.n.NarsetParterOfVeils.class));
        cards.add(new SetCardInfo("Nicol Bolas, Dragon-God", 522, Rarity.MYTHIC, mage.cards.n.NicolBolasDragonGod.class));
        cards.add(new SetCardInfo("Nissa, Who Shakes the World", 518, Rarity.RARE, mage.cards.n.NissaWhoShakesTheWorld.class));
        cards.add(new SetCardInfo("Ob Nixilis, the Hate-Twisted", 511, Rarity.UNCOMMON, mage.cards.o.ObNixilisTheHateTwisted.class));
        cards.add(new SetCardInfo("Ral, Storm Conduit", 523, Rarity.RARE, mage.cards.r.RalStormConduit.class));
        cards.add(new SetCardInfo("Saheeli, Sublime Artificer", 534, Rarity.UNCOMMON, mage.cards.s.SaheeliSublimeArtificer.class));
        cards.add(new SetCardInfo("Samut, Tyrant Smasher", 535, Rarity.UNCOMMON, mage.cards.s.SamutTyrantSmasher.class));
        cards.add(new SetCardInfo("Sarkhan the Masterless", 514, Rarity.RARE, mage.cards.s.SarkhanTheMasterless.class));
        cards.add(new SetCardInfo("Sorin, Vengeful Bloodlord", 524, Rarity.RARE, mage.cards.s.SorinVengefulBloodlord.class));
        cards.add(new SetCardInfo("Tamiyo, Collector of Tales", 525, Rarity.RARE, mage.cards.t.TamiyoCollectorOfTales.class));
        cards.add(new SetCardInfo("Teferi, Time Raveler", 526, Rarity.RARE, mage.cards.t.TeferiTimeRaveler.class));
        cards.add(new SetCardInfo("Teyo, the Shieldmage", 504, Rarity.UNCOMMON, mage.cards.t.TeyoTheShieldmage.class));
        cards.add(new SetCardInfo("The Wanderer", 505, Rarity.UNCOMMON, mage.cards.t.TheWanderer.class));
        cards.add(new SetCardInfo("Tibalt, Rakish Instigator", 515, Rarity.UNCOMMON, mage.cards.t.TibaltRakishInstigator.class));
        cards.add(new SetCardInfo("Vivien, Champion of the Wilds", 519, Rarity.RARE, mage.cards.v.VivienChampionOfTheWilds.class));
        cards.add(new SetCardInfo("Vraska, Swarm's Eminence", 536, Rarity.UNCOMMON, mage.cards.v.VraskaSwarmsEminence.class));
     }
}
