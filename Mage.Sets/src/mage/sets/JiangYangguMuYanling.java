/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author TheElk801
 */
public class JiangYangguMuYanling extends ExpansionSet {

    private static final JiangYangguMuYanling instance = new JiangYangguMuYanling();

    public static JiangYangguMuYanling getInstance() {
        return instance;
    }

    private JiangYangguMuYanling() {
        super("Global Series: Jiang Yanggu & Mu Yanling", "GS1", ExpansionSet.buildDate(2018, 6, 22), SetType.SUPPLEMENTAL);
        this.blockName = "Global Series";
        this.hasBasicLands = false;
        cards.add(new SetCardInfo("Jiang Yanggu", 22, Rarity.MYTHIC, mage.cards.j.JiangYanggu.class));
        cards.add(new SetCardInfo("Mu Yanling", 1, Rarity.MYTHIC, mage.cards.m.MuYanling.class));
    }
}
