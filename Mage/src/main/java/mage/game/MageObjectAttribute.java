/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.game;

import java.io.Serializable;
import mage.MageObject;
import mage.ObjectColor;
import mage.util.SubTypeList;

/**
 * This class saves changed attributes of mage objects (e.g. in command zone, graveyard, exile or
 * player hands or libraries).
 *
 * @author LevelX2
 */
public class MageObjectAttribute implements Serializable {

    protected ObjectColor color;
    protected SubTypeList subtype;

    public MageObjectAttribute(MageObject mageObject, Game game) {
        color = mageObject.getColor(null).copy();
        subtype = new SubTypeList();
        subtype.addAll(mageObject.getSubtype(game));
    }

    public MageObjectAttribute(MageObjectAttribute mageObjectAttribute) {
        this.color = mageObjectAttribute.color;
        this.subtype = mageObjectAttribute.subtype;
    }

    public MageObjectAttribute copy() {
        return new MageObjectAttribute(this);
    }

    public ObjectColor getColor() {
        return color;
    }

    public SubTypeList getSubtype() {
        return subtype;
    }

}
