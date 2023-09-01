
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

        cards.add(new SetCardInfo("Archangel Avacyn", 1, Rarity.MYTHIC, mage.cards.a.ArchangelAvacyn.class));
        cards.add(new SetCardInfo("Arguel's Blood Fast", 2, Rarity.MYTHIC, mage.cards.a.ArguelsBloodFast.class));
        cards.add(new SetCardInfo("Arlinn Kord", 3, Rarity.MYTHIC, mage.cards.a.ArlinnKord.class));
        cards.add(new SetCardInfo("Bloodline Keeper", 4, Rarity.MYTHIC, mage.cards.b.BloodlineKeeper.class));
        cards.add(new SetCardInfo("Bruna, the Fading Light", "5a", Rarity.MYTHIC, mage.cards.b.BrunaTheFadingLight.class));
        cards.add(new SetCardInfo("Brisela, Voice of Nightmares", "5b", Rarity.MYTHIC, mage.cards.b.BriselaVoiceOfNightmares.class));
        cards.add(new SetCardInfo("Chandra, Fire of Kaladesh", 6, Rarity.MYTHIC, mage.cards.c.ChandraFireOfKaladesh.class));
        cards.add(new SetCardInfo("Delver of Secrets", 7, Rarity.MYTHIC, mage.cards.d.DelverOfSecrets.class));
        cards.add(new SetCardInfo("Elbrus, the Binding Blade", 8, Rarity.MYTHIC, mage.cards.e.ElbrusTheBindingBlade.class));
        cards.add(new SetCardInfo("Garruk Relentless", 9, Rarity.MYTHIC, mage.cards.g.GarrukRelentless.class));
        cards.add(new SetCardInfo("Gisela, the Broken Blade", 10, Rarity.MYTHIC, mage.cards.g.GiselaTheBrokenBlade.class));
        cards.add(new SetCardInfo("Huntmaster of the Fells", 11, Rarity.MYTHIC, mage.cards.h.HuntmasterOfTheFells.class));
        cards.add(new SetCardInfo("Jace, Vryn's Prodigy", 12, Rarity.MYTHIC, mage.cards.j.JaceVrynsProdigy.class));
        cards.add(new SetCardInfo("Kytheon, Hero of Akros", 13, Rarity.MYTHIC, mage.cards.k.KytheonHeroOfAkros.class));
        cards.add(new SetCardInfo("Liliana, Heretical Healer", 14, Rarity.MYTHIC, mage.cards.l.LilianaHereticalHealer.class));
        cards.add(new SetCardInfo("Nissa, Vastwood Seer", 15, Rarity.MYTHIC, mage.cards.n.NissaVastwoodSeer.class));
    }
}
