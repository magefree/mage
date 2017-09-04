/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.game.permanent.token;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.keyword.HexproofAbility;
import mage.constants.CardType;

/**
 *
 * @author TacomenX
 */

public class MerfolkHexproofToken extends Token {

   

    public MerfolkHexproofToken() {
        super("Merfolk", "1/1 blue Merfolk creature token with hexproof");
        this.cardType.add(CardType.CREATURE);
        this.subtype.add("Merfolk");
        this.color = ObjectColor.BLUE;
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(HexproofAbility.getInstance());
    }

}