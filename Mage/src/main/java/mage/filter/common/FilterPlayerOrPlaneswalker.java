/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.filter.common;

import mage.filter.FilterPlayer;

/**
 *
 * @author LevelX2
 */
public class FilterPlayerOrPlaneswalker extends FilterPermanentOrPlayer {

    public FilterPlayerOrPlaneswalker() {
        this("player or planeswalker");
    }

    public FilterPlayerOrPlaneswalker(String name) {
        super(name, new FilterPlaneswalkerPermanent(), new FilterPlayer());
    }

    public FilterPlayerOrPlaneswalker(final FilterPlayerOrPlaneswalker filter) {
        super(filter);
    }

    @Override
    public FilterPlayerOrPlaneswalker copy() {
        return new FilterPlayerOrPlaneswalker(this);
    }
}
