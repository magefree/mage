/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.cards;

/**
 *
 * @author LevelX2
 */
public interface SplitCardHalf extends Card {

    @Override
    SplitCardHalf copy();

    void setParentCard(SplitCard card);
}
