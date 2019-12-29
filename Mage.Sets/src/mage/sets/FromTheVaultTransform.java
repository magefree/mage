
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author fireshoes
 */
public final class FromTheVaultTransform extends ExpansionSet {

    private static final FromTheVaultTransform instance = new FromTheVaultTransform();

    public static FromTheVaultTransform getInstance() {
        return instance;
    }

    private FromTheVaultTransform() {
        super("From the Vault: Transform", "V17", ExpansionSet.buildDate(2017, 11, 24), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Archangel Avacyn", "1a", Rarity.MYTHIC, mage.cards.a.ArchangelAvacyn.class));
        cards.add(new SetCardInfo("Avacyn, the Purifier", "1b", Rarity.MYTHIC, mage.cards.a.AvacynThePurifier.class));
        cards.add(new SetCardInfo("Arguel's Blood Fast", "2a", Rarity.MYTHIC, mage.cards.a.ArguelsBloodFast.class));
        cards.add(new SetCardInfo("Temple of Aclazotz", "2b", Rarity.MYTHIC, mage.cards.t.TempleOfAclazotz.class));
        cards.add(new SetCardInfo("Arlinn Kord", "3a", Rarity.MYTHIC, mage.cards.a.ArlinnKord.class));
        cards.add(new SetCardInfo("Arlinn, Embraced by the Moon", "3b", Rarity.MYTHIC, mage.cards.a.ArlinnEmbracedByTheMoon.class));
        cards.add(new SetCardInfo("Bloodline Keeper", "4a", Rarity.MYTHIC, mage.cards.b.BloodlineKeeper.class));
        cards.add(new SetCardInfo("Lord of Lineage", "4b", Rarity.MYTHIC, mage.cards.l.LordOfLineage.class));
        cards.add(new SetCardInfo("Bruna, the Fading Light", "5a", Rarity.MYTHIC, mage.cards.b.BrunaTheFadingLight.class));
        cards.add(new SetCardInfo("Brisela, Voice of Nightmares", "5b", Rarity.MYTHIC, mage.cards.b.BriselaVoiceOfNightmares.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chandra, Fire of Kaladesh", "6a", Rarity.MYTHIC, mage.cards.c.ChandraFireOfKaladesh.class));
        cards.add(new SetCardInfo("Chandra, Roaring Flame", "6b", Rarity.MYTHIC, mage.cards.c.ChandraRoaringFlame.class));
        cards.add(new SetCardInfo("Delver of Secrets", "7a", Rarity.MYTHIC, mage.cards.d.DelverOfSecrets.class));
        cards.add(new SetCardInfo("Insectile Aberration", "7b", Rarity.MYTHIC, mage.cards.i.InsectileAberration.class));
        cards.add(new SetCardInfo("Elbrus, the Binding Blade", "8a", Rarity.MYTHIC, mage.cards.e.ElbrusTheBindingBlade.class));
        cards.add(new SetCardInfo("Withengar Unbound", "8b", Rarity.MYTHIC, mage.cards.w.WithengarUnbound.class));
        cards.add(new SetCardInfo("Garruk Relentless", "9a", Rarity.MYTHIC, mage.cards.g.GarrukRelentless.class));
        cards.add(new SetCardInfo("Garruk, the Veil-Cursed", "9b", Rarity.MYTHIC, mage.cards.g.GarrukTheVeilCursed.class));
        cards.add(new SetCardInfo("Gisela, the Broken Blade", "10a", Rarity.MYTHIC, mage.cards.g.GiselaTheBrokenBlade.class));
        cards.add(new SetCardInfo("Huntmaster of the Fells", "11a", Rarity.MYTHIC, mage.cards.h.HuntmasterOfTheFells.class));
        cards.add(new SetCardInfo("Ravager of the Fells", "11b", Rarity.MYTHIC, mage.cards.r.RavagerOfTheFells.class));
        cards.add(new SetCardInfo("Jace, Vryn's Prodigy", "12a", Rarity.MYTHIC, mage.cards.j.JaceVrynsProdigy.class));
        cards.add(new SetCardInfo("Jace, Telepath Unbound", "12b", Rarity.MYTHIC, mage.cards.j.JaceTelepathUnbound.class));
        cards.add(new SetCardInfo("Kytheon, Hero of Akros", "13a", Rarity.MYTHIC, mage.cards.k.KytheonHeroOfAkros.class));
        cards.add(new SetCardInfo("Gideon, Battle-Forged", "13b", Rarity.MYTHIC, mage.cards.g.GideonBattleForged.class));
        cards.add(new SetCardInfo("Liliana, Heretical Healer", "14a", Rarity.MYTHIC, mage.cards.l.LilianaHereticalHealer.class));
        cards.add(new SetCardInfo("Liliana, Defiant Necromancer", "14b", Rarity.MYTHIC, mage.cards.l.LilianaDefiantNecromancer.class));
        cards.add(new SetCardInfo("Nissa, Vastwood Seer", "15a", Rarity.MYTHIC, mage.cards.n.NissaVastwoodSeer.class));
        cards.add(new SetCardInfo("Nissa, Sage Animist", "15b", Rarity.MYTHIC, mage.cards.n.NissaSageAnimist.class));
    }
}
