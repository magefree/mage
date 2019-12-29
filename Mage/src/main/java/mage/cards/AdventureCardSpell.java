/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.cards;

/**
 *
 * @author phulin
 */
public interface AdventureCardSpell extends Card {

    @Override
    AdventureCardSpell copy();

    void setParentCard(AdventureCard card);

    AdventureCard getParentCard();
}
