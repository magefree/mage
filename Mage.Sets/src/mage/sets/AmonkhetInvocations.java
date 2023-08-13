
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/mp2
 *
 * @author fireshoes
 */
public final class AmonkhetInvocations extends ExpansionSet {

    private static final AmonkhetInvocations instance = new AmonkhetInvocations();

    public static AmonkhetInvocations getInstance() {
        return instance;
    }

    private AmonkhetInvocations() {
        super("Amonkhet Invocations", "MP2", ExpansionSet.buildDate(2017, 4, 28), SetType.PROMOTIONAL);
        this.blockName = "Masterpiece Series";
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Aggravated Assault", 25, Rarity.MYTHIC, mage.cards.a.AggravatedAssault.class));
        cards.add(new SetCardInfo("Armageddon", 31, Rarity.MYTHIC, mage.cards.a.Armageddon.class));
        cards.add(new SetCardInfo("Attrition", 19, Rarity.MYTHIC, mage.cards.a.Attrition.class));
        cards.add(new SetCardInfo("Austere Command", 1, Rarity.MYTHIC, mage.cards.a.AustereCommand.class));
        cards.add(new SetCardInfo("Avatar of Woe", 38, Rarity.MYTHIC, mage.cards.a.AvatarOfWoe.class));
        cards.add(new SetCardInfo("Aven Mindcensor", 2, Rarity.MYTHIC, mage.cards.a.AvenMindcensor.class));
        cards.add(new SetCardInfo("Blood Moon", 46, Rarity.MYTHIC, mage.cards.b.BloodMoon.class));
        cards.add(new SetCardInfo("Boil", 47, Rarity.MYTHIC, mage.cards.b.Boil.class));
        cards.add(new SetCardInfo("Bontu the Glorified", 20, Rarity.MYTHIC, mage.cards.b.BontuTheGlorified.class));
        cards.add(new SetCardInfo("Capsize", 32, Rarity.MYTHIC, mage.cards.c.Capsize.class));
        cards.add(new SetCardInfo("Chain Lightning", 26, Rarity.MYTHIC, mage.cards.c.ChainLightning.class));
        cards.add(new SetCardInfo("Choke", 50, Rarity.MYTHIC, mage.cards.c.Choke.class));
        cards.add(new SetCardInfo("Consecrated Sphinx", 8, Rarity.MYTHIC, mage.cards.c.ConsecratedSphinx.class));
        cards.add(new SetCardInfo("Containment Priest", 3, Rarity.MYTHIC, mage.cards.c.ContainmentPriest.class));
        cards.add(new SetCardInfo("Counterbalance", 9, Rarity.MYTHIC, mage.cards.c.Counterbalance.class));
        cards.add(new SetCardInfo("Counterspell", 10, Rarity.MYTHIC, mage.cards.c.Counterspell.class));
        cards.add(new SetCardInfo("Cryptic Command", 11, Rarity.MYTHIC, mage.cards.c.CrypticCommand.class));
        cards.add(new SetCardInfo("Damnation", 39, Rarity.MYTHIC, mage.cards.d.Damnation.class));
        cards.add(new SetCardInfo("Dark Ritual", 21, Rarity.MYTHIC, mage.cards.d.DarkRitual.class));
        cards.add(new SetCardInfo("Daze", 12, Rarity.MYTHIC, mage.cards.d.Daze.class));
        cards.add(new SetCardInfo("Desolation Angel", 40, Rarity.MYTHIC, mage.cards.d.DesolationAngel.class));
        cards.add(new SetCardInfo("Diabolic Edict", 41, Rarity.MYTHIC, mage.cards.d.DiabolicEdict.class));
        cards.add(new SetCardInfo("Diabolic Intent", 22, Rarity.MYTHIC, mage.cards.d.DiabolicIntent.class));
        cards.add(new SetCardInfo("Divert", 13, Rarity.MYTHIC, mage.cards.d.Divert.class));
        cards.add(new SetCardInfo("Doomsday", 42, Rarity.MYTHIC, mage.cards.d.Doomsday.class));
        cards.add(new SetCardInfo("Entomb", 23, Rarity.MYTHIC, mage.cards.e.Entomb.class));
        cards.add(new SetCardInfo("Forbid", 33, Rarity.MYTHIC, mage.cards.f.Forbid.class));
        cards.add(new SetCardInfo("Force of Will", 14, Rarity.MYTHIC, mage.cards.f.ForceOfWill.class));
        cards.add(new SetCardInfo("Hazoret the Fervent", 27, Rarity.MYTHIC, mage.cards.h.HazoretTheFervent.class));
        cards.add(new SetCardInfo("Kefnet the Mindful", 15, Rarity.MYTHIC, mage.cards.k.KefnetTheMindful.class));
        cards.add(new SetCardInfo("Lord of Extinction", 52, Rarity.MYTHIC, mage.cards.l.LordOfExtinction.class));
        cards.add(new SetCardInfo("Loyal Retainers", 4, Rarity.MYTHIC, mage.cards.l.LoyalRetainers.class));
        cards.add(new SetCardInfo("Maelstrom Pulse", 29, Rarity.MYTHIC, mage.cards.m.MaelstromPulse.class));
        cards.add(new SetCardInfo("Mind Twist", 24, Rarity.MYTHIC, mage.cards.m.MindTwist.class));
        cards.add(new SetCardInfo("No Mercy", 43, Rarity.MYTHIC, mage.cards.n.NoMercy.class));
        cards.add(new SetCardInfo("Oketra the True", 5, Rarity.MYTHIC, mage.cards.o.OketraTheTrue.class));
        cards.add(new SetCardInfo("Omniscience", 34, Rarity.MYTHIC, mage.cards.o.Omniscience.class));
        cards.add(new SetCardInfo("Opposition", 35, Rarity.MYTHIC, mage.cards.o.Opposition.class));
        cards.add(new SetCardInfo("Pact of Negation", 16, Rarity.MYTHIC, mage.cards.p.PactOfNegation.class));
        cards.add(new SetCardInfo("Rhonas the Indomitable", 28, Rarity.MYTHIC, mage.cards.r.RhonasTheIndomitable.class));
        cards.add(new SetCardInfo("Shatterstorm", 48, Rarity.MYTHIC, mage.cards.s.Shatterstorm.class));
        cards.add(new SetCardInfo("Slaughter Pact", 44, Rarity.MYTHIC, mage.cards.s.SlaughterPact.class));
        cards.add(new SetCardInfo("Spell Pierce", 17, Rarity.MYTHIC, mage.cards.s.SpellPierce.class));
        cards.add(new SetCardInfo("Stifle", 18, Rarity.MYTHIC, mage.cards.s.Stifle.class));
        cards.add(new SetCardInfo("Sunder", 36, Rarity.MYTHIC, mage.cards.s.Sunder.class));
        cards.add(new SetCardInfo("The Locust God", 51, Rarity.MYTHIC, mage.cards.t.TheLocustGod.class));
        cards.add(new SetCardInfo("The Scarab God", 53, Rarity.MYTHIC, mage.cards.t.TheScarabGod.class));
        cards.add(new SetCardInfo("The Scorpion God", 54, Rarity.MYTHIC, mage.cards.t.TheScorpionGod.class));
        cards.add(new SetCardInfo("Thoughtseize", 45, Rarity.MYTHIC, mage.cards.t.Thoughtseize.class));
        cards.add(new SetCardInfo("Threads of Disloyalty", 37, Rarity.MYTHIC, mage.cards.t.ThreadsOfDisloyalty.class));
        cards.add(new SetCardInfo("Through the Breach", 49, Rarity.MYTHIC, mage.cards.t.ThroughTheBreach.class));
        cards.add(new SetCardInfo("Vindicate", 30, Rarity.MYTHIC, mage.cards.v.Vindicate.class));
        cards.add(new SetCardInfo("Worship", 6, Rarity.MYTHIC, mage.cards.w.Worship.class));
        cards.add(new SetCardInfo("Wrath of God", 7, Rarity.MYTHIC, mage.cards.w.WrathOfGod.class));
    }
}
