/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.sets;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import mage.cards.ExpansionSet;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.constants.SetType;

/**
 *
 * @author fireshoes
 */
public class OathOfTheGatewatch extends ExpansionSet {

    private static final OathOfTheGatewatch fINSTANCE = new OathOfTheGatewatch();

    public static OathOfTheGatewatch getInstance() {
        return fINSTANCE;
    }

    protected final List<CardInfo> savedSpecialLand = new ArrayList<>();

    private OathOfTheGatewatch() {
        super("Oath of the Gatewatch", "OGW", "mage.sets.oathofthegatewatch", new GregorianCalendar(2016, 1, 22).getTime(), SetType.EXPANSION);
        this.blockName = "Battle for Zendikar";
        this.parentSet = BattleForZendikar.getInstance();
        this.hasBoosters = true;
        this.hasBasicLands = false;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;

        // Zendikar Expeditions 26-45
        this.ratioBoosterSpecialLand = 20;
    }

    @Override
    public List<CardInfo> getSpecialLand() {
        if (savedSpecialLand.isEmpty()) {
            CardCriteria criteria = new CardCriteria();
            criteria.setCodes("EXP");
            criteria.minCardNumber(26);
            criteria.maxCardNumber(45);
            savedSpecialLand.addAll(CardRepository.instance.findCards(criteria));
        }

        return new ArrayList<>(savedSpecialLand);
    }
}
