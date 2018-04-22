/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.filter.common;

import mage.filter.FilterOpponent;

/**
 *
 * @author LevelX2
 */
public class FilterOpponentOrPlaneswalker extends FilterPermanentOrPlayer {

    public FilterOpponentOrPlaneswalker() {
        this("opponent or planeswalker");
    }

    public FilterOpponentOrPlaneswalker(String name) {
        super(name, new FilterPlaneswalkerPermanent(), new FilterOpponent());
    }

    public FilterOpponentOrPlaneswalker(final FilterOpponentOrPlaneswalker filter) {
        super(filter);
    }

    @Override
    public FilterOpponentOrPlaneswalker copy() {
        return new FilterOpponentOrPlaneswalker(this);
    }
}
