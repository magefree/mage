/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.client.util;

import java.util.Comparator;
import mage.view.CardView;

/**
 *
 * @author LevelX2
 */
public class CardViewCardTypeComparator implements Comparator<CardView> {

    @Override
    public int compare(CardView o1, CardView o2) {
        return o1.getCardTypes().toString().compareTo(o2.getCardTypes().toString());
    }

}
